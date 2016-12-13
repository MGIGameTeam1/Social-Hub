package com.mgi.kelasku.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Achmad Siddik on 13/12/2016.
 */

public class Utility {
    boolean status;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String value,email;
    public void setEmail(Context context, String email){
        sp= PreferenceManager.getDefaultSharedPreferences(context);
        editor=sp.edit();
        editor.putString("email",email);
        editor.commit();
    }

    public void setJabatan(Context context,String jabatan){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        editor=sp.edit();
        editor.putString("jabatan",jabatan);
        editor.commit();
    }

    public boolean isLogin(Context context){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        value=null;
        email=sp.getString("email",value);
        if(email!=null){
            status=true;
        }else{
            status=false;
        }

        return status;
    }

    public String getPref(Context context,String xPref){
        sp=PreferenceManager.getDefaultSharedPreferences(context);
        value=null;
        String xData=sp.getString(xPref,value);
        if(xData!=null){
            return xData;
        }else{
            return "";
        }
    }
}
