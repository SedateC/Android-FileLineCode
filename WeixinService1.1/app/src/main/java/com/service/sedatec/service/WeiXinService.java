package com.service.sedatec.service;

import android.accessibilityservice.AccessibilityService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.service.sedatec.base.ShellUtils;
import com.service.sedatec.dbentity.AccountEntity;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class WeiXinService extends AccessibilityService {
    public static final String TAG = "Message";
    private AccessibilityNodeInfo rootNodeInfo;
    public String description ="";
    private SQLiteDatabase db = LitePal.getDatabase();
    List<AccountEntity>  accounts = DataSupport.findAll(AccountEntity.class);
    AccountEntity entity; //切换账户得到的实例
    boolean chuangeflag =true; //标志可以切换账户的FLAG
    int nowAccountsId = 0;//当前账户LIST ID
    private AccountEntity nowAccountEntity; //当前账户ID

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        rootNodeInfo = getRootInActiveWindow();
         if (rootNodeInfo!=null){
            String flag =  findWhereView(rootNodeInfo);
             if (flag!=null){
                     if (flag.equals("ONSET")){
                         ShellUtils.execCommand("input tap 967 1859",true);//点击我按钮
                         List<AccessibilityNodeInfo> kabao =   rootNodeInfo.findAccessibilityNodeInfosByText("卡包");
                         if (kabao!=null&&!kabao.isEmpty()){
                           ShellUtils.execCommand("input tap 233 1500",true);
                         }else {
                             ShellUtils.execCommand("input tap 220 1350",true);
                         }
                         //点击设置按钮
                         try {
                             Thread.currentThread().sleep(1500);
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                    }else if(flag.equals("ONSETTABLE")){
                     ShellUtils.execCommand("input tap 127 1829",true);//点击退出按钮
                         try {
                             Thread.currentThread().sleep(1500);
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                    }else if (flag.equals("ONSETTABLEEXIT")){
                         try {
                             Thread.currentThread().sleep(1500);
                         }catch (Exception e){
                             e.printStackTrace();
                         }
                     ShellUtils.execCommand("input tap 450 900",true);//点击退出当前账号
                     }else if(flag.equals("ONSETTABLEEXITCLICK")){
                         ShellUtils.execCommand("input tap 806 1128",true);//点击退出微信
                     }else if (flag.equals("LOGINUI")){
                         ShellUtils.execCommand("input tap 1000 150",true);//点击右上角功能
                     }else if (flag.equals("CHUANGEACCOUNT")){
                         ShellUtils.execCommand("input tap 165 1421",true);//点击切换账号
                     }else if (flag.equals("PHONELOGIN")){
                         ShellUtils.execCommand("input tap 291 913",true);//点击微信号登陆
                         chuangeflag = true;//确认可以切换账户
                         Log.d(TAG, "do:chuangeflag=true  nowAccountsId  "+chuangeflag);
                       //   nowAccountsId -= 1;//减一操作
                     }else if (flag.equals("CLICKLOGIN")){
                        for (int i=0;i<accounts.size();i++){
                             AccountEntity account =  accounts.get(i);
                             Log.d(TAG, "nowAccountsId account: i:"+i+"---"+account);
                         }
                             if (chuangeflag=true){
                                entity = autoChuangeAccount(accounts,chuangeflag);
                             }
                         if (entity!=null){
                             nowAccountEntity = entity;
                         }
                         List<AccessibilityNodeInfo> accInputs = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/h2");
                         Log.d(TAG, "onAccessibilityEvent: "+accInputs);
                         AccessibilityNodeInfo accinputtext = accInputs.get(0);
                         pause(accinputtext,nowAccountEntity.getAccount());
                         AccessibilityNodeInfo accinputtextpassword = accInputs.get(1);
                         pause(accinputtextpassword,nowAccountEntity.getPassword());
                         ShellUtils.execCommand("input tap 500 1100",true);

                     }
             }
              }else {
             try {
                     Thread.currentThread().sleep(500);
                 }catch (Exception e){
                     e.printStackTrace();
                 }
                 return;
             }


    }

    private AccountEntity autoChuangeAccount(List<AccountEntity> accounts,boolean flag) {
        Log.d(TAG, "autoChuangeAccount: accounts.size()"+accounts.size()+"-----flag"+flag);
        if (nowAccountsId<accounts.size()&& flag){//循环添加
            nowAccountEntity = accounts.get(nowAccountsId);
            nowAccountsId ++;
            chuangeflag=false;
            Log.d(TAG, "autoChuangeAccount: nowAccountsId++"+nowAccountsId+"___"+nowAccountEntity);
            return nowAccountEntity;
       }else if (nowAccountsId<accounts.size()) {// 这里是其他次进入此次方法的情况
            if (nowAccountsId==0){
                accounts.get(0);
            }
            Log.d(TAG, "autoChuangeAccount:不++ nowAccountsId-1 "+nowAccountsId+"___"+nowAccountEntity);
            return accounts.get(nowAccountsId-1);
        } else {
            Log.d(TAG, "autoChuangeAccount   nowAccountsId=0; "+nowAccountsId);
                nowAccountsId=0;
             return accounts.get(nowAccountsId);
        }
    }


    @Override
    public void onInterrupt() {

    }
    public void pause(AccessibilityNodeInfo info,String text){
        ClipboardManager clipboard = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("text", text);
        clipboard.setPrimaryClip(clip);
        //焦点（n是AccessibilityNodeInfo对象）
        info.performAction(AccessibilityNodeInfo.ACTION_FOCUS);
        ////粘贴进入内容
        info.performAction(AccessibilityNodeInfo.ACTION_PASTE);
    }
    private String findWhereView(AccessibilityNodeInfo rootNodeInfo ){
        List<AccessibilityNodeInfo> indexFlag = rootNodeInfo.findAccessibilityNodeInfosByText("当前所在页面");
        List<AccessibilityNodeInfo> nodeMEs =   rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/apb");//第四个节点才是
        CharSequence ContentDescription = rootNodeInfo.getContentDescription();
        rootNodeInfo.getClassName();
        List<AccessibilityNodeInfo> exitNodes =   rootNodeInfo.findAccessibilityNodeInfosByText("退出当前账号");
        List<AccessibilityNodeInfo> closeNodes =   rootNodeInfo.findAccessibilityNodeInfosByText("关闭微信");
        List<AccessibilityNodeInfo> closeIDNodes = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bi2");
        List<AccessibilityNodeInfo> closeID2Nodes = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bi3");
        List<AccessibilityNodeInfo> exit2Nodes =   rootNodeInfo.findAccessibilityNodeInfosByText("退出后不会删除");
        List<AccessibilityNodeInfo> exit21Nodes = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aes");
        List<AccessibilityNodeInfo> loginImage = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bhl");
        List<AccessibilityNodeInfo> loginAdmin = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bhg");
        List<AccessibilityNodeInfo> chuangeAccount = rootNodeInfo.findAccessibilityNodeInfosByText("com.tencent.mm:id/fl");
        List<AccessibilityNodeInfo> chuangepal = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/hb");
        List<AccessibilityNodeInfo> phoneLogin = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/nu");
        List<AccessibilityNodeInfo> phoneLogin1 = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/adj");
        List<AccessibilityNodeInfo> loginclick = rootNodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/bhj");
        if (ContentDescription!=null){
            Log.d(TAG, "rootNodeInfoDescription: "+ContentDescription.toString());
        }
        Log.d(TAG, "indexFlag: "+indexFlag);
        Log.d(TAG, "nodeMEs: "+nodeMEs);
        Log.d(TAG, ".getClassName: "+  rootNodeInfo.getClassName());
        Log.d(TAG, "exitNodes: "+exitNodes);
        Log.d(TAG, "closeNodes: "+closeNodes);
        Log.d(TAG, "closeIDNodes: "+closeIDNodes+"----"+closeID2Nodes);
        Log.d(TAG, "exit2Nodes: "+exit2Nodes+"---"+exit21Nodes);
        Log.d(TAG, "chuangeAccount: "+chuangeAccount+"----chuangepal--"+chuangepal);
        Log.d(TAG, "phoneLogin: "+phoneLogin+"phoneLogin1----"+phoneLogin1);
        Log.d(TAG, "loginclick: "+loginclick+"loginclick----"+loginclick);
        if (nodeMEs!=null&&!nodeMEs.isEmpty()&&ContentDescription!=null&&!ContentDescription.equals("当前所在页面,设置")){
            return "ONSET";
        }else if(ContentDescription!=null&&ContentDescription.equals("当前所在页面,设置")&&nodeMEs.isEmpty()){
            return "ONSETTABLE";
        }else if(closeNodes!=null&&!closeNodes.isEmpty()&&closeIDNodes!=null&&!closeIDNodes.isEmpty()){
            return "ONSETTABLEEXIT";
        }else if (exit2Nodes!=null&&!exit2Nodes.isEmpty()){
            return "ONSETTABLEEXITCLICK";
        }else if (loginImage!=null&&!loginImage.isEmpty()&&loginAdmin!=null&&!loginAdmin.isEmpty()){
            return "LOGINUI";
        }
        else if (chuangepal!=null&&!chuangepal.isEmpty()){
            return "CHUANGEACCOUNT";
        }
        else if (phoneLogin!=null&&!phoneLogin.isEmpty()&&phoneLogin1!=null&&!phoneLogin1.isEmpty()
                &&loginclick!=null&&loginclick.isEmpty()){
            return "PHONELOGIN";
        }
        else if (phoneLogin!=null&&!phoneLogin.isEmpty()&&loginclick!=null&&!loginclick.isEmpty()){
            return "CLICKLOGIN";
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
