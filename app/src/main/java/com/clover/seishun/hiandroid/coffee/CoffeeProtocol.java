package com.clover.seishun.hiandroid.coffee;

/**
 * Created by heaun.b on 2016. 3. 21..
 */

import android.util.Log;

import com.clover.seishun.hiandroid.user.UserAccountInfo;
import com.clover.seishun.hiandroid.user.UserStateManager;

//import net.hicare.smart.single.manager.UserStateManager;
//import net.hicare.smart.utils.UserAccountInfo;
//import org.apache.log4j.Logger;
import org.joda.time.DateTime;

/**
 * Created by heaun.b on 2016. 3. 8..
 */

//@EService
public class CoffeeProtocol{
//    protected final Logger logger = Logger.getLogger(CoffeeProtocol.class);
    //command code (REQUEST_CODE)
    public static byte NONE                      = 0x00;
    public static byte REQ_ACTIVE_MASS           = 0x01;
    public static byte CMD_REPORT_PERIOD         = 0x02; //30분 단위의 활동랴 데이터를 호스트에 전달하는 주기 설저
    public static byte CMD_PERSONAL_INFO         = 0x03; //고객 개인정보 전달
    public static byte CMD_SET_TIME              = 0x04; //Device 시간 설정
    public static byte CMD_FIRMWARE_UPDATE       = 0x05; //Firmware
    public static byte CMD_FWUPDATE_READY	     = 0x06;
    public static byte READ_CURRENT_ACTIVITY     = 0x07; //현재의 누적된 활동량 데이터를 Device에서 얻는다.
    public static byte SET_LED_PERIOD            = 0x08;
    public static byte SET_ALARM_OPTION          = 0x09;
    public static byte WRITE_UNIQUE_ID           = 0x0A; //녹십자헬스케어에서 할당한 Unique ID를 Device에 전달한다.
    public static byte READ_UNIQUE_ID            = 0x0B; //Device에 저장된 Unique ID를 얻는다.
    public static byte READ_SERIAL_NUMBER        = 0x0C; //Device에 저장된 Serial Number를 얻는다.
    public static byte READ_FW_VERSION           = 0x10;
    public static byte READ_BATT_STATE           = 0x11;
    public static byte READ_LOT_NUMBER           = 0x12;
    public static byte CMD_START_CID             = 0x13;
    public static byte CMD_STOP_CID              = 0x14;
    public static byte CMD_SET_BTCONTIME         = 0x15;
    public static byte CMD_SET_LANGUAGE          = 0x16;
    public static byte CMD_FIND_DEVICE           = 0x17;
    public static byte CMD_REMOTE_FEELING        = 0x18;
    public static byte SET_MORNING_CALL          = 0x19;
    public static byte SET_FEATURE_ONOFF         = 0x1A;
    public static byte WRITE_SERIAL_NUMBER       = 0x1B;
    public static byte SET_TEST_MODE             = 0x1C;
    public static byte CMD_DEV_RESET             = 0x1D;
    public static byte REQ_DAY_DATA              = 0x1E;
    public static byte WRITE_LOT_NUMBER          = 0x1F;
    public static byte INFORM_LINK_DISCONNECTION = 0x20;
    public static byte REQ_SLEEP_MASS            = 0x21;
    public static byte SET_MOVE_SENSITIVITY      = 0x22;

    public static byte COMMAND_COMPLETE        = (byte) 0x82; // Result Code 참조
    public static byte FW_VERSION_NOTIFY       = (byte) 0x83; // Device의 SW 버전을 전달한다.
    public static byte DEVICE_CONNECTED_NOTIFY = (byte) 0x86; // "Device가 연결되었다는 상태를 전달한다.
    public static byte SERIAL_NUMBER_NOTIFY	   = (byte) 0x88; // Device의 Serial Number를 전달한다.
    public static byte ACTIVE_MASS_NOTIFY      = (byte) 0x90; // Device에 30분 단위로 저장된 Activity Data를 전달한다. (한번에 최대 6개)
    public static byte CURRENT_ACTIVITY_NOTIFY = (byte) 0x91; // "Device의 현재 일일 누적(자정 0시부터의 누적) 활동량 데이터를 전달한다.
    public static byte DAY_DATA_NOTIFY         = (byte) 0x95; // "Device의 일별(하루 단위의) 기록 데이터들을 전달한다.

    public static byte RESULT_MSG_SUCCESS                  = (byte) 0x00;
    public static byte RESULT_MSG_UNKNOWN_PACKET           = (byte) 0x01;
    public static byte RESULT_MSG_ILLEAGAL_PACKET_LENGTH   = (byte) 0x02;
    public static byte RESULT_MSG_WRONG_CHCECKSUM          = (byte) 0x03;
    public static byte RESULT_MSG_IMPROPER_STATUS          = (byte) 0x04;
    public static byte RESULT_MSG_NO_FIRMWARE              = (byte) 0x05;
    public static byte RESULT_MSG_LOW_BATTERY              = (byte) 0x06;
    public static byte RESULT_MSG_NOT_SUPPORTED_COMMAND    = (byte) 0x10;
    public static byte RESULT_MSG_ILLEAGAL_DATA            = (byte) 0x11;
    public static byte RESULT_MSG_NO_SAVED_INFORMATION     = (byte) 0x20;
    public static byte RESULT_MSG_FULL_MEMORY              = (byte) 0x21;
    public static byte RESULT_MSG_ILLEAGAL_MEMORY_READING  = (byte) 0x30;
    public static byte RESULT_MSG_ILLEAGAL_MEMORY_WRITING  = (byte) 0x31;

    UserStateManager userManager;

    public static byte[] reqActiveMass(int dataSeq){
        //Device에 저장된 활동량데이터(30분단위)를 Host에 전달한다

        byte[] bytes = new byte[5];

        byte REQUEST_PARAM_DATA_NOT_ERASE  = 0x00;
        byte REQUEST_PARAM_DATA_ERASE      = 0x01;

        //Data_Erase(1byte) : 0x00(Not Erase)/0x01(Erase)

        bytes[0] = REQ_ACTIVE_MASS;//CMD
        bytes[1] = 0x02;//LEN
        bytes[2] = REQUEST_PARAM_DATA_NOT_ERASE;//param ->0x00(not erase), 0x01(erase)
        bytes[3] = (byte) dataSeq;//seq
        bytes[4] = checkSum(bytes);

        return bytes;
    }

    public static byte[] cmdReportPeriod(){

        //30분 단위의 활동량 데이터를 호스트에 전달하는 주기를 설정
        //단위 : 시간 (1,2,4,6,8,....24)
        byte[] bytes = new byte[4];

        bytes[0] = CMD_REPORT_PERIOD;
        bytes[1] = 0x01;
        bytes[2] = 0x01;
        bytes[3] = checkSum(bytes);

        return bytes;
    }

    public static byte[] cmdPersonalInfo(UserAccountInfo userinfo)
    {
        //로그인된 회원정보 받아와서 바인딩하기
        byte[] bytes = new byte[11];

//        byte height = (byte) 0xA2;        //2byte : 162 / 0x00AC
//        byte weight = (byte) 0x30;         //2byte :  48 / 0x0050
//        byte targetKcal = (byte) 0x258;    //2byte / 600 / 0x258

        byte height = (byte) 0xAC;        //2byte : 172 / 0x00AC
        byte weight = (byte) 0x50;         //2byte :  80 / 0x0050
        byte targetKcal = (byte) 0x1f4;    //2byte / 500 / 0x01f4
//
        byte sex = 0;

        String gender = userinfo._uGENDER;
        byte age = (byte) Integer.parseInt(userinfo.age());

        Log.d("coffeeble ==> age", userinfo.age() );
        Log.d("coffeeble ==> gender",gender);
        Log.d("coffeeble ==> targetKcal", String.valueOf((int)targetKcal & 0xff));
        Log.d("coffeeble ==> height", String.valueOf(height& 0xff));
        Log.d("coffeeble ==> weight", String.valueOf(weight& 0xff));

        if("F".equals(gender))
            sex = 0x01;
        else if("M".equals(gender))
            sex = 0x00;

        bytes[0] = CMD_PERSONAL_INFO;
        bytes[1] = 0x08;//seq

        bytes[2] = (byte) ((height & 0xFF00) >> 8);
        bytes[3] = (byte)  (height & 0x00FF);
        bytes[4] = (byte) ((weight & 0xFF00) >> 8);
        bytes[5] = (byte)  (weight & 0x00FF);
        bytes[6] = (byte) ((targetKcal & 0xFF00) >> 8);
        bytes[7] = (byte)  (targetKcal & 0x00FF);
        bytes[8] = sex;
        bytes[9] = age;
        bytes[10] = checkSum(bytes);

        return bytes;
    }


    public static byte[] cmdSetTime() {
        byte[] bytes = new byte[10];
//
        DateTime today = new DateTime();
        bytes[0] = CMD_SET_TIME;
        bytes[1] = 0x07;//seq
        bytes[2] = (byte) ((today.getYear() & 0xFF00) >> 8);
        bytes[3] = (byte) (today.getYear() & 0x00FF); //0x10;

        bytes[4] = (byte) today.getMonthOfYear();
        bytes[5] = (byte) today.getDayOfMonth() ; //0x11;//
        bytes[6] = (byte) today.getHourOfDay();
        bytes[7] = (byte) today.getMinuteOfHour();
        bytes[8] = (byte) today.getSecondOfMinute();
        bytes[9] = checkSum(bytes);

        String str = "";
        String strToInt = "";
        for (byte b: bytes) {
            str += String.valueOf(b)+", ";
            strToInt += String.valueOf(b&0xff)+", ";
        }
        Log.d("CMD_SET_TIME : ",str);
        Log.d("CMD_SET_TIME strToInt: ", strToInt);

        return bytes;
    }

    public static byte[] readyCurrentActivity() {
        byte[] bytes = new byte[3];
        bytes[0] = READ_CURRENT_ACTIVITY;
        bytes[1] = 0x00;//seq
        bytes[2] = checkSum(bytes);
        return bytes;
    }

    public static byte[] reqDayData(int dataSeq)
    {
        byte[] bytes = new byte[5];

        byte REQUEST_PARAM_DATA_NOT_ERASE  = 0x00;
        byte REQUEST_PARAM_DATA_ERASE      = 0x01;

        bytes[0] = REQ_DAY_DATA;
        bytes[1] = 0x02;
        bytes[2] = REQUEST_PARAM_DATA_ERASE;
        bytes[3] = (byte) dataSeq;
        bytes[4] = checkSum(bytes);
        return bytes;
    }

    public static byte[] cmdDevReset()
    {
        byte[] bytes = new byte[4];

        byte SW_RESET        = 0x01;
        byte FACTORY_RESET   = 0x02;

        bytes[0] = CMD_DEV_RESET;
        bytes[1] = 0x01;
        bytes[2] = FACTORY_RESET;
        bytes[3] = checkSum(bytes);
        return bytes;
    }

    public static byte[] readFWVersion()
    {
        byte[] bytes = new byte[3];

        bytes[0] = READ_FW_VERSION;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
        return bytes;
    }


    public void setLedPeriod() {
        byte[] bytes = new byte[6];
        bytes[0] = SET_LED_PERIOD;
        bytes[1] = 0x04;//seq

        int period = 0;//ms
        bytes[2] = 0x27;
        bytes[3] = 0x10;

        int duration = 0;
        bytes[4] = 0x01;
        bytes[5] = (byte) 0xF4;
        bytes[6] = checkSum(bytes);
    }

    public void setAlarmOption() {
        byte[] bytes = new byte[6];
        bytes[0] = SET_ALARM_OPTION;
        bytes[1] = 0x04;//seq

        int featOnOff = 0;  // ms (4byte)
        bytes[2] = 0x00;        //(0~23)
        bytes[3] = 0x00;        //(0~59)

        int alarmHour = 0; // (2byte)
        int alarmMinunte = 0;

        bytes[4] = 0x00;
        bytes[5] = (byte) 0xff;
        bytes[6] = checkSum(bytes);

    }

    public void writeUniqueId(){
        //녹십자헬스케어에서 할당한 unique id를 device에 전달한다
        byte[] bytes = new byte[9];
        bytes[0] = WRITE_UNIQUE_ID;
        bytes[1] = 0x09;
        bytes[2] = 0x08;
        //...
        bytes[3] = checkSum(bytes);

    }

    public void readUniqueId(){
        //device에 저장된 unique id를 얻는다
        byte[] bytes =  new byte[6];
        bytes[0] = READ_UNIQUE_ID;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
    }

    public static byte[] readSerialNumber(){
        //device에 저장된 unique id를 얻는다
        byte[] bytes =  new byte[6];
        bytes[0] = READ_SERIAL_NUMBER;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);

        return bytes;
    }

    public void readBattState(){
        //device에 저장된 unique id를 얻는다
        byte[] bytes =  new byte[6];
        bytes[0] = READ_BATT_STATE;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
    }

    public void readLotNumber(){
        //device에 저장된 unique id를 얻는다
        byte[] bytes =  new byte[6];
        bytes[0] = READ_LOT_NUMBER;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
    }

    public void cmdStartCid(){
        //device에 저장된 unique id를 얻는다
        byte[] bytes =  new byte[6];
        bytes[0] = CMD_START_CID;

        int cidType = 0;
        int cidSize = 0;
        int cid = 0;

        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
        //..
    }

    public void cmdStopCid(){
        byte[] bytes = new byte[6];
        bytes[0] = CMD_STOP_CID;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
    }

    public static byte[] cmdFindDevice()
    {
        byte[] bytes = new byte[6];
        bytes[0] = CMD_FIND_DEVICE;
        bytes[1] = 0x02;
        bytes[1] = 0x00;
        bytes[1] = 0x00;
        bytes[2] = checkSum(bytes);
        return bytes;
    }

    public static byte checkSum(byte[] value) {

        byte checksum = value[0];
        for(int i = 1; i<value.length; i++) {
            checksum ^= value[i];
        }
        return checksum;
    }
    public static byte checkSum(byte[] value, int endIndex)
    {
        byte[] data = new byte[endIndex+1];
        if(endIndex>0){
            for(int i=0; i<=endIndex; i++){
                data[i] = value[i];
            }
        }
        return checkSum(data);
    }

}
