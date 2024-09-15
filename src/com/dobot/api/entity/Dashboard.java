package com.dobot.api;



import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dobot.api.ErrorInfoHelper;


public class Dashboard {
    private Socket socketClient = new Socket();

    private static String SEND_ERROR = ":send error";

    private String ip = "";


    public boolean connect(String ip,int port,Socket socketClient){
        boolean ok = false;
        try {
            this.socketClient = socketClient;
            this.ip = ip;
            Logger.instance.log("Dashboard connect success");
            ok = true;
        } catch (Exception e) {
            Logger.instance.log("Connect failed:" + e.getMessage());
        }
        return ok;
    }

    public void disconnect(){
        if(!socketClient.isClosed()){
            try {
                socketClient.shutdownOutput();
                socketClient.shutdownInput();
                socketClient.close();
                Logger.instance.log("Dashboard closed");
            } catch (Exception e) {
                Logger.instance.log("Dashboard Close Socket Exception:" + e.getMessage());
            }
        }else {
            Logger.instance.log("this ip is not connected");
        }
    }

    /// <summary>
    /// 设置数字输出端口状态（队列指令）
    /// </summary>
    /// <param name="index">数字输出索引，取值范围：1~16或100~1000</param>
    /// <param name="status">数字输出端口状态，true：高电平；false：低电平</param>
    /// <param name="mstime">持续时间，毫秒</param>
    /// <returns>返回执行结果的描述信息</returns>
    public String DigitalOutputs(int index, boolean status, int mstime)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        int statusInt = status ? 1 : 0;
        String str = "DOInstant("+index+","+statusInt+")";
        if (!sendData(str,false))
        {
            return str + ":send error";
        }

        return waitReply(5000,false);
    }

    public String getErrorID()
    {

        String str = "GetErrorID()";

        if (!sendData(str,true))
        {
            return str + ":send error";
        }

        return waitReply(5000,true);

    }


    /// <summary>
    /// 设置末端数字输出端口状态（立即指令）
    /// </summary>
    /// <param name="index">数字输出索引</param>
    /// <param name="status">数字输出端口状态，true：高电平；false：低电平</param>
    /// <returns>返回执行结果的描述信息</returns>
    public String toolDO(int index, boolean status)
    {
        if (!socketClient.isConnected())
        {
            return "device does not connected!!!";
        }
        int intStatus = status ? 1 : 0;
        String str = "ToolDOInstant("+index+","+intStatus+")";
        if (!sendData(str,false))
        {
            return str + ":send error";
        }

        return waitReply(5000,false);
    }


    public String clearError(){
        if(socketClient.isClosed()){
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ClearError()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }

        return waitReply(5000,false);
    }

    public String PowerOn(){
        if(socketClient.isClosed()){
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "PowerOn()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }

        return waitReply(15000,false);
    }

    public String PowerOff() {
        return emergencyStop();
    }

    public String emergencyStop()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "EmergencyStop()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(15000,false);
    }

    public String enableRobot()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }

        String str = "EnableRobot()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }

        return waitReply(20000,false);
    }

    public String disableRobot()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }

        String str = "DisableRobot()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }

        return waitReply(20000,false);
    }

    public String stopRobot(){
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }

        String str = "Stop()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }

        return waitReply(20000,false);
    }

    public String speedFactor(int ratio)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="SpeedFactor(" + ratio +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///选择已标定的用户坐标系
    public String User(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="User(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///选择已标定的工具坐标系
    public String Tool(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="Tool(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///机器人状态
    public String RobotMode()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="RobotMode()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置当前的负载
    public String SetPayload(double load)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="SetPayload(" + load +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置控制柜模拟输出端口的模拟值
    public String AO(int index,double value)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="AO(" + index +","+value+")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }

    ///设置控制柜模拟输出端口的模拟值
    public String AOInstant(int index,double value)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="AOInstant(" + index +","+value+")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置关节加速度比例
    public String AccJ(int R)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="AccJ(" + R +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置笛卡尔加速度比例。
    public String AccL(int R)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="AccL(" + R +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置关节速度比例。
    public String VelJ(int R)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="VelJ(" + R +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置笛卡尔速度比例。
    public String VelL(int R)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="VelL(" + R +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置CP比例。CP即平滑过渡，机械臂从起始点经过中间点到达终点时，经过中间点是以直角方式过渡还是以曲线方式过渡。
    public String CP(int R)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="CP(" + R +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///运行脚本
    public String RunScript(String projectName)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="RunScript(" + projectName +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///停止运动(或脚本)
    public String Stop()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="Stop()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///暂停运动(或脚本)
    public String Pause()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="Pause()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///继续运动(或脚本)
    public String Continue()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="Continue()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置碰撞等级
    public String SetCollisionLevel(int level)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="SetCollisionLevel(" + level +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取关节坐标系下机械臂的实时位姿
    public String GetAngle()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="GetAngle()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取笛卡尔坐标系下机械臂的实时位姿
    public String GetPose()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="GetPose()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取数字量输入端口状态
    public String DI(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="DI(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取末端数字量输入端口状态
    public String ToolDI(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="ToolDI(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取模拟量输入端口模拟值（立即指令）
    public String AI(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="AI(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取末端模拟量输入端口模拟值
    public String ToolAI(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="ToolAI(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///开关抱闸
    ///axisID 关节轴号
    ///value 设置抱闸状态； 0:关闭抱闸 1：打开抱闸
    public String BrakeControl(int axisID,int value)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="BrakeControl(" + axisID +","+value+")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///进入拖拽(在报错状态下，不可进入拖拽)
    public String StartDrag()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="StartDrag()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///进入拖拽(在报错状态下，不可进入拖拽
    public String StopDrag()
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="StopDrag()";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///设置拖拽灵敏度
    ///index 轴号代表1到6轴均设置为此灵敏度
    ///value 轴拖拽灵敏度 1-90
    public String DragSensivity(int index,int value)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="DragSensivity(" + index +","+value+")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取数字输出端口状态
    public String GetDO(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="GetDO(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取模拟量输出端口状态
    public String GetAO(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="GetAO(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    ///获取末端数字输出端口状态
    public String GetToolDO(int index)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="GetToolDO(" + index +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    /// 设置末端工具供电状态
    /// 0:关闭，1：开启
    public String SetToolPower(int status)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="SetToolPower(" + status +")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    /// 设置用户坐标系
    /// index  坐标系索引 0-50
    /// value 要设置的坐标值{x,y,z,rx,ry,rz}
    public String SetUser(int index,String value)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="SetUser(" + index +","+value+")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }
    /// 设置工具坐标系
    /// index  坐标系索引 0-50
    /// value 要设置的坐标值{x,y,z,rx,ry,rz}
    public String SetTool(int index,String value)
    {
        if (socketClient.isClosed())
        {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str ="SetTool(" + index +","+value+")";
        if(!sendData(str,false)){
            return str + SEND_ERROR;
        }
        return waitReply(5000,false);
    }


    public boolean sendData(String str,boolean notLog){
        try {
            if(!notLog)
            Logger.instance.log("Send to:" +this.ip+":"+socketClient.getPort()+":"+str);
            socketClient.getOutputStream().write((str).getBytes());
        } catch (IOException e) {
            Logger.instance.log("Exception:" + e.getMessage());
            return false;
        }
        return true;
    }

    public String waitReply(int timeout,boolean notLog){
        String reply = "";
        try {
            if(socketClient.getSoTimeout() != timeout){
                socketClient.setSoTimeout(timeout);
            }
            byte[] buffer = new byte[1024];				//缓冲
            int len = socketClient.getInputStream().read(buffer);//每次读取的长度（正常情况下是1024，最后一次可能不是1024，如果传输结束，返回-1）
            reply = new String(buffer,0,len,"UTF-8");
            ErrorInfoHelper.getInstance().parseResult(reply);
            if(!notLog)
            Logger.instance.log("Receive from:"+this.ip+":"+socketClient.getPort()+":"+reply);

        } catch (IOException e) {
            Logger.instance.log("Exception:"+e.getMessage());
            return reply;
        }
        return reply;
    }


}
