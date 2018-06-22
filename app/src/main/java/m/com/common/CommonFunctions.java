package m.com.common;

import android.util.Log;
import android.widget.Switch;

import com.google.zxing.Result;

import m.com.bo.ContactBO;
import m.com.dboperation.FBContactDBOperation;

/**
 * Created by Bhavin on 24-Apr-18.
 */

public class CommonFunctions {

    //public static String extractQRData(Result rawResult, String strMainKey)
    public static String extractQRData(String  strResult, String strMainKey)
    {

      //  String strResult = rawResult.getText();
        String strRespose = "";

        try
        {
            if (strResult != null && !"".equalsIgnoreCase(strResult) && !"null".equalsIgnoreCase(strResult))
            {
                String[] strResArr = strResult.split("\\|",0);


                if (strResArr != null && strResArr.length > 0 && "BEGIN:USERPROFILE".equalsIgnoreCase(strResArr[0]) )
                {
                    ContactBO contBO =  new ContactBO();

                    for (int i =0;i<strResArr.length;i++)
                    {
                        String[] strTempArr = strResArr[i].split("\\$",0);


                        if("firstName".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setFirstName(strTempArr[1]);
                        }
                        else if("lastName".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setLastName(strTempArr[1]);
                        }
                        else if("email".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setEmail(strTempArr[1]);
                        }
                        else if("phone".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setPhone(strTempArr[1]);
                        }
                        else if("company".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setCompany(strTempArr[1]);
                        }
                        else if("deskphone".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setDeskphone(strTempArr[1]);
                        }
                        else if("title".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setTitle(strTempArr[1]);
                        }
                        else if("address".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setAddress(strTempArr[1]);
                        }
                        else if("website".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setWebsite(strTempArr[1]);
                        }
                        else if("notes".equalsIgnoreCase(strTempArr[0]))
                        {
                            contBO.setNotes(strTempArr[1]);
                        }
                        else if("END".equalsIgnoreCase(strTempArr[0]) && "USERPROFILE".equalsIgnoreCase(strTempArr[1]))
                        {
                            break;
                        }
                    }

                    FBContactDBOperation dbOpr = new FBContactDBOperation();
                    dbOpr.insertContact(contBO, strMainKey);
                }

                strRespose = "success";
            }
        }
        catch(Exception e)
        {
            Log.d("bhavin","error in scan"+e.getMessage());
            e.printStackTrace();
            strRespose="error";
        }



        return strRespose;
    }

    public static String EncodeKeyString(String str)
    {
        if(str != null)
            return str.replace(".", ",");
        else
            return "";
    }

    public static String DecodeKeyString(String str)
    {
        if(str != null)
            return str.replace(",", ".");
        else
            return  "";
    }

}


