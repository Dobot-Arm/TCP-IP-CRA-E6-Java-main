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


    ///设置机械臂检测到碰撞后原路回退的距离
    public String SetBackDistance(double distance) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetBackDistance(" + distance + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    ///设置机械臂检测到碰撞后进入的状态
    public String SetPostCollisionMode(int mode) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetPostCollisionMode(" + mode + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    ///开启或关闭安全皮肤功能
    public String EnableSafeSkin(int status) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "EnableSafeSkin(" + status + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    ///设置安全皮肤各个部位的灵敏度
    public String SetSafeSkin(int part, int status) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetSafeSkin(" + part + "," + status + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    ///开启或关闭指定的安全墙
    public String SetSafeWallEnable(int index, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetSafeWallEnable(" + index + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    ///开启或关闭指定的干涉区
    public String SetWorkZoneEnable(int index, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetWorkZoneEnable(" + index + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCCollisionSwitch(int enable) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "FCCollisionSwitch(" + enable + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetFCCollision(double force, double torque) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetFCCollision(" + force + "," + torque + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String PositiveKin(double J1, double J2, double J3, double J4, double J5, double J6, int user, int tool) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("PositiveKin(%f,%f,%f,%f,%f,%f", J1, J2, J3, J4, J5, J6);
        if (user != -1) {
            str += ",user=" + user;
        }
        if (tool != -1) {
            str += ",tool=" + tool;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String InverseKin(double X, double Y, double Z, double Rx, double Ry, double Rz, int user, int tool, int useJointNear, String JointNear) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("InverseKin(%f,%f,%f,%f,%f,%f", X, Y, Z, Rx, Ry, Rz);
        if (user != -1) {
            str += ",user=" + user;
        }
        if (tool != -1) {
            str += ",tool=" + tool;
        }
        if (useJointNear != -1) {
            str += ",useJointNear=" + useJointNear;
        }
        if (JointNear != null && !JointNear.isEmpty()) {
            str += ",JointNear=" + JointNear;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String InverseSolution(double a1, double b1, double c1, double d1, double e1, double f1, int user, int tool, int isJoint) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("InverseSolution(pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        if (user != -1) {
            str += ",user=" + user;
        }
        if (tool != -1) {
            str += ",tool=" + tool;
        }
        if (isJoint != 0) {
            str += ",isJoint=" + isJoint;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String DOGroup(int... args) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        StringBuilder str = new StringBuilder("DOGroup(");
        for (int i = 0; i < args.length; i++) {
            str.append(args[i]);
            if (i < args.length - 1) {
                str.append(",");
            }
        }
        str.append(")");
        if (!sendData(str.toString(), false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetDOGroup(int... args) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        StringBuilder str = new StringBuilder("GetDOGroup(");
        for (int i = 0; i < args.length; i++) {
            str.append(args[i]);
            if (i < args.length - 1) {
                str.append(",");
            }
        }
        str.append(")");
        if (!sendData(str.toString(), false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String DIGroup(int... args) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        StringBuilder str = new StringBuilder("DIGroup(");
        for (int i = 0; i < args.length; i++) {
            str.append(args[i]);
            if (i < args.length - 1) {
                str.append(",");
            }
        }
        str.append(")");
        if (!sendData(str.toString(), false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }
    
    public String ToolDOInstant(int index, int status) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ToolDOInstant(" + index + "," + status + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetTool485(int baud, String parity, int stopbit, int identify) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetTool485(" + baud;
        if (parity != null && !parity.isEmpty()) {
            str += "," + parity;
        }
        if (stopbit != -1) {
            str += "," + stopbit;
        }
        if (identify != -1) {
            str += "," + identify;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetToolPower(int status, int identify) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetToolPower(" + status;
        if (identify != -1) {
            str += "," + identify;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetToolMode(int mode, int type, int identify) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetToolMode(" + mode + "," + type;
        if (identify != -1) {
            str += "," + identify;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ModbusCreate(String ip, int port, int slave_id, int isRTU) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ModbusCreate(" + ip + "," + port + "," + slave_id;
        if (isRTU != -1) {
            str += "," + isRTU;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ModbusRTUCreate(int slave_id, int baud, String parity, int data_bit, int stop_bit) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ModbusRTUCreate(" + slave_id + "," + baud;
        if (parity != null && !parity.isEmpty()) {
            str += "," + parity;
        }
        if (data_bit != -1) {
            str += "," + data_bit;
        }
        if (stop_bit != -1) {
            str += "," + stop_bit;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ModbusClose(int index) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ModbusClose(" + index + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetInBits(int index, int addr, int count) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetInBits(" + index + "," + addr + "," + count + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetInRegs(int index, int addr, int count, String valType) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetInRegs(" + index + "," + addr + "," + count;
        if (valType != null && !valType.isEmpty()) {
            str += "," + valType;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetCoils(int index, int addr, int count) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetCoils(" + index + "," + addr + "," + count + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetCoils(int index, int addr, int count, String valTab) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetCoils(" + index + "," + addr + "," + count + "," + valTab + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetHoldRegs(int index, int addr, int count, String valType) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetHoldRegs(" + index + "," + addr + "," + count;
        if (valType != null && !valType.isEmpty()) {
            str += "," + valType;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetHoldRegs(int index, int addr, int count, String valTab, String valType) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetHoldRegs(" + index + "," + addr + "," + count + "," + valTab;
        if (valType != null && !valType.isEmpty()) {
            str += "," + valType;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String DOGroupDEC(int group, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "DOGroupDEC(" + group + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetDOGroupDEC(int group, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetDOGroupDEC(" + group + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String DIGroupDEC(int group, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "DIGroupDEC(" + group + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }
    
    public String GetInputBool(int address) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetInputBool(" + address + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetInputInt(int address) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetInputInt(" + address + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetInputFloat(int address) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetInputFloat(" + address + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetOutputBool(int address) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetOutputBool(" + address + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetOutputInt(int address) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetOutputInt(" + address + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetOutputFloat(int address) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetOutputFloat(" + address + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetOutputBool(int address, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetOutputBool(" + address + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetOutputInt(int address, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetOutputInt(" + address + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetOutputFloat(int address, int value) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetOutputFloat(" + address + "," + value + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String MovJ(double a1, double b1, double c1, double d1, double e1, double f1, int coordinateMode, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("MovJ(pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        } else if (coordinateMode == 1) {
            str = String.format("MovJ(joint={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        } else {
             return "coordinateMode param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String MovL(double a1, double b1, double c1, double d1, double e1, double f1, int coordinateMode, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("MovL(pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        } else if (coordinateMode == 1) {
            str = String.format("MovL(joint={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        } else {
             return "coordinateMode param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ServoJ(double J1, double J2, double J3, double J4, double J5, double J6, double t, double aheadtime, double gain) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("ServoJ(%f,%f,%f,%f,%f,%f", J1, J2, J3, J4, J5, J6);
        if (t != -1) str += ",t=" + t;
        if (aheadtime != -1) str += ",aheadtime=" + aheadtime;
        if (gain != -1) str += ",gain=" + gain;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ServoP(double X, double Y, double Z, double Rx, double Ry, double Rz, double t, double aheadtime, double gain) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("ServoP(%f,%f,%f,%f,%f,%f", X, Y, Z, Rx, Ry, Rz);
        if (t != -1) str += ",t=" + t;
        if (aheadtime != -1) str += ",aheadtime=" + aheadtime;
        if (gain != -1) str += ",gain=" + gain;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String MovLIO(double a1, double b1, double c1, double d1, double e1, double f1, int coordinateMode, int Mode, int Distance, int Index, int Status, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("MovLIO(pose={%f,%f,%f,%f,%f,%f},{%d,%d,%d,%d}", a1, b1, c1, d1, e1, f1, Mode, Distance, Index, Status);
        } else if (coordinateMode == 1) {
            str = String.format("MovLIO(joint={%f,%f,%f,%f,%f,%f},{%d,%d,%d,%d}", a1, b1, c1, d1, e1, f1, Mode, Distance, Index, Status);
        } else {
             return "coordinateMode param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String MovJIO(double a1, double b1, double c1, double d1, double e1, double f1, int coordinateMode, int Mode, int Distance, int Index, int Status, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("MovJIO(pose={%f,%f,%f,%f,%f,%f},{%d,%d,%d,%d}", a1, b1, c1, d1, e1, f1, Mode, Distance, Index, Status);
        } else if (coordinateMode == 1) {
            str = String.format("MovJIO(joint={%f,%f,%f,%f,%f,%f},{%d,%d,%d,%d}", a1, b1, c1, d1, e1, f1, Mode, Distance, Index, Status);
        } else {
             return "coordinateMode param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String Arc(double a1, double b1, double c1, double d1, double e1, double f1, double a2, double b2, double c2, double d2, double e2, double f2, int coordinateMode, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("Arc(pose={%f,%f,%f,%f,%f,%f},pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1, a2, b2, c2, d2, e2, f2);
        } else if (coordinateMode == 1) {
            str = String.format("Arc(joint={%f,%f,%f,%f,%f,%f},joint={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1, a2, b2, c2, d2, e2, f2);
        } else {
             return "coordinateMode param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String Circle(double a1, double b1, double c1, double d1, double e1, double f1, double a2, double b2, double c2, double d2, double e2, double f2, int coordinateMode, int count, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("Circle(pose={%f,%f,%f,%f,%f,%f},pose={%f,%f,%f,%f,%f,%f},%d", a1, b1, c1, d1, e1, f1, a2, b2, c2, d2, e2, f2, count);
        } else if (coordinateMode == 1) {
            str = String.format("Circle(joint={%f,%f,%f,%f,%f,%f},joint={%f,%f,%f,%f,%f,%f},%d", a1, b1, c1, d1, e1, f1, a2, b2, c2, d2, e2, f2, count);
        } else {
             return "coordinateMode param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String MoveJog(String axis_id, int coordtype, int user, int tool) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "MoveJog(" + axis_id;
        if (coordtype != -1) str += ",coordtype=" + coordtype;
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }
    
    public String RunTo(double a1, double b1, double c1, double d1, double e1, double f1, int moveType, int user, int tool, int a, int v) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (moveType == 0) {
            str = String.format("RunTo(pose={%f,%f,%f,%f,%f,%f},moveType=0", a1, b1, c1, d1, e1, f1);
        } else if (moveType == 1) {
            str = String.format("RunTo(joint={%f,%f,%f,%f,%f,%f},moveType=1", a1, b1, c1, d1, e1, f1);
        } else {
             return "moveType param is wrong";
        }
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String MoveL(double a1, double b1, double c1, double d1, double e1, double f1, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("MoveL(pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String CheckMovJ(double j1a, double j2a, double j3a, double j4a, double j5a, double j6a, double j1b, double j2b, double j3b, double j4b, double j5b, double j6b, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("CheckMovJ(joint={%f,%f,%f,%f,%f,%f},joint={%f,%f,%f,%f,%f,%f}", j1a, j2a, j3a, j4a, j5a, j6a, j1b, j2b, j3b, j4b, j5b, j6b);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }
    
    public String CheckMovC(double j1a, double j2a, double j3a, double j4a, double j5a, double j6a, double j1b, double j2b, double j3b, double j4b, double j5b, double j6b, double j1c, double j2c, double j3c, double j4c, double j5c, double j6c, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("CheckMovC(joint={%f,%f,%f,%f,%f,%f},joint={%f,%f,%f,%f,%f,%f},joint={%f,%f,%f,%f,%f,%f}", j1a, j2a, j3a, j4a, j5a, j6a, j1b, j2b, j3b, j4b, j5b, j6b, j1c, j2c, j3c, j4c, j5c, j6c);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelMovJTool(double x, double y, double z, double rx, double ry, double rz, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelMovJTool(%f,%f,%f,%f,%f,%f", x, y, z, rx, ry, rz);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelMovLTool(double x, double y, double z, double rx, double ry, double rz, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelMovLTool(%f,%f,%f,%f,%f,%f", x, y, z, rx, ry, rz);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelMovJUser(double x, double y, double z, double rx, double ry, double rz, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelMovJUser(%f,%f,%f,%f,%f,%f", x, y, z, rx, ry, rz);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelMovLUser(double x, double y, double z, double rx, double ry, double rz, int user, int tool, int a, int v, int speed, int cp, int r) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelMovLUser(%f,%f,%f,%f,%f,%f", x, y, z, rx, ry, rz);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (speed != -1) str += ",speed=" + speed;
        else if (v != -1) str += ",v=" + v;
        if (r != -1) str += ",r=" + r;
        else if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelJointMovJ(double offset1, double offset2, double offset3, double offset4, double offset5, double offset6, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelJointMovJ(%f,%f,%f,%f,%f,%f", offset1, offset2, offset3, offset4, offset5, offset6);
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelPointTool(int coordinateMode, double a1, double b1, double c1, double d1, double e1, double f1, double x, double y, double z, double rx, double ry, double rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("RelPointTool(pose={%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f})", a1, b1, c1, d1, e1, f1, x, y, z, rx, ry, rz);
        } else if (coordinateMode == 1) {
            str = String.format("RelPointTool(joint={%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f})", a1, b1, c1, d1, e1, f1, x, y, z, rx, ry, rz);
        } else {
             return "coordinateMode param is wrong";
        }
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelPointUser(int coordinateMode, double a1, double b1, double c1, double d1, double e1, double f1, double x, double y, double z, double rx, double ry, double rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "";
        if (coordinateMode == 0) {
            str = String.format("RelPointUser(pose={%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f})", a1, b1, c1, d1, e1, f1, x, y, z, rx, ry, rz);
        } else if (coordinateMode == 1) {
            str = String.format("RelPointUser(joint={%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f})", a1, b1, c1, d1, e1, f1, x, y, z, rx, ry, rz);
        } else {
             return "coordinateMode param is wrong";
        }
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelJoint(double j1, double j2, double j3, double j4, double j5, double j6, double offset1, double offset2, double offset3, double offset4, double offset5, double offset6) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelJoint(%f,%f,%f,%f,%f,%f,{%f,%f,%f,%f,%f,%f})", j1, j2, j3, j4, j5, j6, offset1, offset2, offset3, offset4, offset5, offset6);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelPointWeldLine(double StartX, double EndX, double Y, double Z, double WorkAngle, double TravelAngle, double p1_1, double p1_2, double p1_3, double p1_4, double p1_5, double p1_6, double p2_1, double p2_2, double p2_3, double p2_4, double p2_5, double p2_6) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelPointWeldLine(%f,%f,%f,%f,%f,%f,{%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f})", StartX, EndX, Y, Z, WorkAngle, TravelAngle, p1_1, p1_2, p1_3, p1_4, p1_5, p1_6, p2_1, p2_2, p2_3, p2_4, p2_5, p2_6);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RelPointWeldArc(double StartX, double EndX, double Y, double Z, double WorkAngle, double TravelAngle, double p1_1, double p1_2, double p1_3, double p1_4, double p1_5, double p1_6, double p2_1, double p2_2, double p2_3, double p2_4, double p2_5, double p2_6, double p3_1, double p3_2, double p3_3, double p3_4, double p3_5, double p3_6) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("RelPointWeldArc(%f,%f,%f,%f,%f,%f,{%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f},{%f,%f,%f,%f,%f,%f})", StartX, EndX, Y, Z, WorkAngle, TravelAngle, p1_1, p1_2, p1_3, p1_4, p1_5, p1_6, p2_1, p2_2, p2_3, p2_4, p2_5, p2_6, p3_1, p3_2, p3_3, p3_4, p3_5, p3_6);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetStartPose(String traceName) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetStartPose(" + traceName + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String StartPath(String traceName, int isConst, double multi, int user, int tool) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "StartPath(" + traceName;
        if (isConst != -1) str += ",isConst=" + isConst;
        if (multi != -1) str += ",multi=" + multi;
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetResumeOffset(double distance) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetResumeOffset(" + distance + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String PathRecovery() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "PathRecovery()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String PathRecoveryStop() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "PathRecoveryStop()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String PathRecoveryStatus() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "PathRecoveryStatus()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ArcTrackStart() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ArcTrackStart()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ArcTrackParams(int sampleTime, int coordinateType, double upDownCompensationMin, double upDownCompensationMax, double upDownCompensationOffset, double leftRightCompensationMin, double leftRightCompensationMax, double leftRightCompensationOffset) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("ArcTrackParams(%d,%d,%f,%f,%f,%f,%f,%f)", sampleTime, coordinateType, upDownCompensationMin, upDownCompensationMax, upDownCompensationOffset, leftRightCompensationMin, leftRightCompensationMax, leftRightCompensationOffset);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ArcTrackEnd() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ArcTrackEnd()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetArcTrackOffset(double offsetX, double offsetY, double offsetZ, double offsetRx, double offsetRy, double offsetRz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("SetArcTrackOffset({%f,%f,%f,%f,%f,%f})", offsetX, offsetY, offsetZ, offsetRx, offsetRy, offsetRz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String OffsetPara(double x, double y, double z, double rx, double ry, double rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("OffsetPara(%f,%f,%f,%f,%f,%f)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetTrayPoint(String trayName) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetTrayPoint(" + trayName + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String EnableFTSensor(int status) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "EnableFTSensor(" + status + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SixForceHome() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SixForceHome()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetForce(int tool) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetForce(";
        if (tool != -1) {
            str += tool;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ForceDriveMode(int x, int y, int z, int rx, int ry, int rz, int user) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("ForceDriveMode({%d,%d,%d,%d,%d,%d}", x, y, z, rx, ry, rz);
        if (user != -1) {
            str += "," + user;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ForceDriveSpeed(int speed) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ForceDriveSpeed(" + speed + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCForceMode(int x, int y, int z, int rx, int ry, int rz, int fx, int fy, int fz, int frx, int fry, int frz, int reference, int user, int tool) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCForceMode({%d,%d,%d,%d,%d,%d},{%d,%d,%d,%d,%d,%d}", x, y, z, rx, ry, rz, fx, fy, fz, frx, fry, frz);
        if (reference != -1) str += ",reference=" + reference;
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetDeviation(int x, int y, int z, int rx, int ry, int rz, int controltype) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetDeviation({%d,%d,%d,%d,%d,%d}", x, y, z, rx, ry, rz);
        if (controltype != -1) {
            str += "," + controltype;
        }
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetForceLimit(int x, int y, int z, int rx, int ry, int rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetForceLimit(%d,%d,%d,%d,%d,%d)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetMass(int x, int y, int z, int rx, int ry, int rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetMass(%d,%d,%d,%d,%d,%d)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetStiffness(int x, int y, int z, int rx, int ry, int rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetStiffness(%d,%d,%d,%d,%d,%d)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetDamping(int x, int y, int z, int rx, int ry, int rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetDamping(%d,%d,%d,%d,%d,%d)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCOff() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "FCOff()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetForceSpeedLimit(int x, int y, int z, int rx, int ry, int rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetForceSpeedLimit(%d,%d,%d,%d,%d,%d)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String FCSetForce(int x, int y, int z, int rx, int ry, int rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("FCSetForce(%d,%d,%d,%d,%d,%d)", x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeaveStart() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "WeaveStart()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeaveParams(int weldType, double frequency, double leftAmplitude, double rightAmplitude, int direction, int stopMode, int stopTime1, int stopTime2, int stopTime3, int stopTime4, double radius, double radian) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("WeaveParams(%d,%f,%f,%f,%d,%d,%d,%d,%d,%d,%f,%f)", weldType, frequency, leftAmplitude, rightAmplitude, direction, stopMode, stopTime1, stopTime2, stopTime3, stopTime4, radius, radian);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeaveEnd() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "WeaveEnd()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeldArcSpeedStart() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "WeldArcSpeedStart()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeldArcSpeed(double speed) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "WeldArcSpeed(" + speed + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeldArcSpeedEnd() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "WeldArcSpeedEnd()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String WeldWeaveStart(int weldType, double frequency, double leftAmplitude, double rightAmplitude, int direction, int stopMode, int stopTime1, int stopTime2, int stopTime3, int stopTime4, double radius, double radian) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("WeldWeaveStart(%d,%f,%f,%f,%d,%d,%d,%d,%d,%d,%f,%f)", weldType, frequency, leftAmplitude, rightAmplitude, direction, stopMode, stopTime1, stopTime2, stopTime3, stopTime4, radius, radian);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String CnvInit(int index, double speed, double direction, double startPos, double endPos, double maxPos, double minPos, int mode) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("CnvInit(%d,%f,%f,%f,%f,%f,%f,%d)", index, speed, direction, startPos, endPos, maxPos, minPos, mode);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String CnvMovL(double a1, double b1, double c1, double d1, double e1, double f1, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("CnvMovL(pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String CnvMovC(double a1, double b1, double c1, double d1, double e1, double f1, double a2, double b2, double c2, double d2, double e2, double f2, int user, int tool, int a, int v, int cp) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("CnvMovC(pose={%f,%f,%f,%f,%f,%f},pose={%f,%f,%f,%f,%f,%f}", a1, b1, c1, d1, e1, f1, a2, b2, c2, d2, e2, f2);
        if (user != -1) str += ",user=" + user;
        if (tool != -1) str += ",tool=" + tool;
        if (a != -1) str += ",a=" + a;
        if (v != -1) str += ",v=" + v;
        if (cp != -1) str += ",cp=" + cp;
        str += ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetCnvObject(int index) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetCnvObject(" + index + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetCnvPointOffset(int index, double x, double y, double z, double rx, double ry, double rz) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = String.format("SetCnvPointOffset(%d,{%f,%f,%f,%f,%f,%f})", index, x, y, z, rx, ry, rz);
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String SetCnvTimeCompensation(int index, double time) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "SetCnvTimeCompensation(" + index + "," + time + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String StartSyncCnv(int index, double speed, double direction) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "StartSyncCnv(" + index + "," + speed + "," + direction + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String StopSyncCnv(int index) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "StopSyncCnv(" + index + ")";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetCurrentCommandID() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetCurrentCommandID()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String LogExportUSB() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "LogExportUSB()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetExportStatus() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetExportStatus()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String RequestControl() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "RequestControl()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String GetError() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "GetError()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String ResetRobot() {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        String str = "ResetRobot()";
        if (!sendData(str, false)) {
            return str + SEND_ERROR;
        }
        return waitReply(5000, false);
    }

    public String TcpSendAndParse(String command) {
        if (socketClient.isClosed()) {
            Logger.instance.log("device does not connected!!!");
            return "device does not connected!!!";
        }
        if (!sendData(command, false)) {
            return command + SEND_ERROR;
        }
        return waitReply(5000, false);
    }
    
    public String Sleep(int ms) {
         try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "0";
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
