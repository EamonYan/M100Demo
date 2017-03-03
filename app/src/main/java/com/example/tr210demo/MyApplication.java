package com.example.tr210demo;

import com.codecorp.CortexDecoderLibrary;
import com.codecorp.internal.Debug;
import com.example.tr210demo.utils.CrashHandler;
import com.example.tr210demo.utils.LogUtils;
import com.example.tr210demo.utils.SharedPreferenceUtils;
import com.example.tr210demo.utils.Utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

import static com.codecorp.CortexDecoderLibrary.Focus.Focus_Far;
import static com.codecorp.CortexDecoderLibrary.Focus.Focus_Fix_Far;
import static com.codecorp.CortexDecoderLibrary.Focus.Focus_Fix_Normal;
import static com.codecorp.CortexDecoderLibrary.Focus.Focus_Normal;
import static com.codecorp.CortexDecoderLibrary.Resolution.Resolution_1280x720;
import static com.codecorp.CortexDecoderLibrary.Resolution.Resolution_1920x1080;
import static com.codecorp.CortexDecoderLibrary.Resolution.Resolution_352x288;
import static com.codecorp.CortexDecoderLibrary.Resolution.Resolution_640x480;
import static com.codecorp.internal.Debug.debug;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    CortexDecoderLibrary mCortexDecoderLibrary;

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.closeLight();
        LogUtils.getInstanse(this).saveMassage("MyApplication", "onCreate");
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        SharedPreferenceUtils.initSharedPreference(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        Utils.closeLight();
    }
    public void pushPreference(SharedPreferences sharedPreferences,
                               String key) {

        if (    (key.equals("focus.Back-Facing")) ||
                (key.equals("focus.Front-Facing")) ||
                (key.equals("illumination.Back-Facing")) ||
                (key.equals("illumination.Front-Facing"))) {
            // Do nothing for now by checking for these types this prevents an exception that was occurring
            return;
        }

        debug(TAG, "Sending preference " + key + " to decoder");

        if (key.equals("focus")) {
            String focus = sharedPreferences.getString(key, "ERROR");
            debug(TAG, "Sending preference " + key + "=" + focus + " to decoder");
            if (focus.equals("Normal"))
                mCortexDecoderLibrary.setFocus(Focus_Normal);
            else if (focus.equals("Far Fixed"))
                mCortexDecoderLibrary.setFocus(Focus_Fix_Far);
            else if (focus.equals("Normal Fixed"))
                mCortexDecoderLibrary.setFocus(Focus_Fix_Normal);
            else if (focus.equals("Far"))
                mCortexDecoderLibrary.setFocus(Focus_Far);
            else
                Log.e(TAG, "Unknown focus setting: " + focus);
        } else if (key.equals("camera_type")) {
            String cameraTypeString = sharedPreferences.getString(key, "ERROR");
            debug(TAG, "Sending preference " + key + "=" + cameraTypeString + " to decoder");
            if (cameraTypeString.equals(getString(R.string.camera_type_back)))
                mCortexDecoderLibrary.setCameraType(CortexDecoderLibrary.CameraType.BackFacing);
            else if (cameraTypeString.equals(getString(R.string.camera_type_front)))
                mCortexDecoderLibrary.setCameraType(CortexDecoderLibrary.CameraType.FrontFacing);
            else
                Log.e(TAG, "Unknown camera type: " + cameraTypeString);
        } else if (key.equals("resolution")) {
            String resolution = sharedPreferences.getString(key, "ERROR");
            debug(TAG, "Sending preference " + key + "=" + resolution + " to decoder");
            if (resolution.equals("1920 x 1080"))
                mCortexDecoderLibrary.setDecoderResolution(Resolution_1920x1080);
            else if (resolution.equals("1280 x 720"))
                mCortexDecoderLibrary.setDecoderResolution(Resolution_1280x720);
            else if (resolution.equals("640 x 480"))
                mCortexDecoderLibrary.setDecoderResolution(Resolution_640x480);
            else if (resolution.equals("352 x 288"))
                mCortexDecoderLibrary.setDecoderResolution(Resolution_352x288);
            else
                Log.e(TAG, "Unknown resolution setting: " + resolution);
        } else if (key.equals("illumination")) {
            boolean illumination = sharedPreferences.getBoolean(key, false);
            debug(TAG, "Sending preference " + key + "=" + illumination + " to decoder");
            mCortexDecoderLibrary.setTorch(illumination);
        } else if (key.equals("low_contrast_mode")) {
            boolean mode = sharedPreferences.getBoolean(key, false);
            debug(TAG, "Sending preference " + key + "=" + mode + " to decoder");
            mCortexDecoderLibrary.lowContrastDecodingEnabled(mode);
        } else if (key.equals("large_data_display")) {
            // Nothing to do here
        } else if (key.equals("vibrate_on_scan")) {
            boolean mode = sharedPreferences.getBoolean(key, false);
            debug(TAG, "Sending preference " + key + "=" + mode + " to decoder");
            mCortexDecoderLibrary.enableVibrateOnScan(mode);
        } else if (key.equals("continuous_scan_mode")) {
            // Nothing to do here
        } else if (key.equals("debug_mode")) {
            boolean enabled = sharedPreferences.getBoolean(key, false);
            debug(TAG, "Sending preference " + key + "=" + enabled + " to decoder");
            if (enabled) {
                mCortexDecoderLibrary.enableScannedImageCapture(true);
            }
            else {
                mCortexDecoderLibrary.enableScannedImageCapture(false);
            }
        } else if (key.equals("debug_level")) {
            String debugLevelString = sharedPreferences.getString("debug_level", "4");
            Debug.debugLevel = Integer.parseInt(debugLevelString);
        } else {
            // It must be a symbology
            boolean enabled = sharedPreferences.getBoolean(key, false);
            debug(TAG, "Symbology " + key + " enabled? " + enabled);
            if (key.equals("aztec")) {
                CortexDecoderLibrary.Symbologies.AztecProperties p = new CortexDecoderLibrary.Symbologies.AztecProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("bc412")) {
                CortexDecoderLibrary.Symbologies.BC412Properties p = new CortexDecoderLibrary.Symbologies.BC412Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("code11")) {
                CortexDecoderLibrary.Symbologies.Code11Properties p = new CortexDecoderLibrary.Symbologies.Code11Properties();
                p.enabled = enabled;
                // p.checksumProperties = CortexDecoderLibrary.Symbologies.Code11PropertiesChecksum.Code11PropertiesChecksum_Disabled;
                // p.stripChecksumDigitsEnabled = false;
                p.saveProperties();
            } else if (key.equals("code128")) {
                CortexDecoderLibrary.Symbologies.Code128Properties p = new CortexDecoderLibrary.Symbologies.Code128Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("code32")) {
                CortexDecoderLibrary.Symbologies.Code32Properties p = new CortexDecoderLibrary.Symbologies.Code32Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("code39")) {
                CortexDecoderLibrary.Symbologies.Code39Properties p = new CortexDecoderLibrary.Symbologies.Code39Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("code49")) {
                CortexDecoderLibrary.Symbologies.Code49Properties p = new CortexDecoderLibrary.Symbologies.Code49Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("code93")) {
                CortexDecoderLibrary.Symbologies.Code93Properties p = new CortexDecoderLibrary.Symbologies.Code93Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("codabar")) {
                CortexDecoderLibrary.Symbologies.CodabarProperties p = new CortexDecoderLibrary.Symbologies.CodabarProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("codablockf")) {
                CortexDecoderLibrary.Symbologies.CodablockFProperties p = new CortexDecoderLibrary.Symbologies.CodablockFProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("data_matrix")) {
                CortexDecoderLibrary.Symbologies.DataMatrixProperties p = new CortexDecoderLibrary.Symbologies.DataMatrixProperties();
                p.enabled = enabled;
                p.extendedRectEnabled = enabled;
                p.saveProperties();
            } else if (key.equals("ean13")) {
                CortexDecoderLibrary.Symbologies.EAN13Properties p = new CortexDecoderLibrary.Symbologies.EAN13Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("ean8")) {
                CortexDecoderLibrary.Symbologies.EAN8Properties p = new CortexDecoderLibrary.Symbologies.EAN8Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("grid_matrix")) {
                CortexDecoderLibrary.Symbologies.GridMatrixProperties p = new CortexDecoderLibrary.Symbologies.GridMatrixProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("gs1_databar")) {
                CortexDecoderLibrary.Symbologies.GS1DataBar14Properties p = new CortexDecoderLibrary.Symbologies.GS1DataBar14Properties();
                p.enabled = enabled;
                p.ccaDecodingEnabled = enabled;
                p.ccbDecodingEnabled = enabled;
                p.cccDecodingEnabled = enabled;
                p.expandedDecodingEnabled = enabled;
                p.expandedStackDecodingEnabled = enabled;
                p.limitedDecodingEnabled = enabled;
                p.stackedDecodingEnabled = enabled;
                p.saveProperties();
            } else if (key.equals("hanxin_code")) {
                CortexDecoderLibrary.Symbologies.HanXinCodeProperties p = new CortexDecoderLibrary.Symbologies.HanXinCodeProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("hong_kong_2_of_5")) {
                CortexDecoderLibrary.Symbologies.HongKong2of5Properties p = new CortexDecoderLibrary.Symbologies.HongKong2of5Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("iata_2_of_5")) {
                CortexDecoderLibrary.Symbologies.IATA2of5Properties p = new CortexDecoderLibrary.Symbologies.IATA2of5Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("interleaved_2_of_5")) {
                CortexDecoderLibrary.Symbologies.Interleaved2of5Properties p = new CortexDecoderLibrary.Symbologies.Interleaved2of5Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("maxi_code")) {
                CortexDecoderLibrary.Symbologies.MaxiCodeProperties p = new CortexDecoderLibrary.Symbologies.MaxiCodeProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("matrix_2_of_5")) {
                CortexDecoderLibrary.Symbologies.Matrix2of5Properties p = new CortexDecoderLibrary.Symbologies.Matrix2of5Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("micropdf417")) {
                CortexDecoderLibrary.Symbologies.MicroPDF417Properties p = new CortexDecoderLibrary.Symbologies.MicroPDF417Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("microqr")) {
                CortexDecoderLibrary.Symbologies.MicroQRProperties p = new CortexDecoderLibrary.Symbologies.MicroQRProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("msi_plessey")) {
                CortexDecoderLibrary.Symbologies.MSIPlesseyProperties p = new CortexDecoderLibrary.Symbologies.MSIPlesseyProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("nec_2_of_5")) {
                CortexDecoderLibrary.Symbologies.NEC2of5Properties p = new CortexDecoderLibrary.Symbologies.NEC2of5Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("pdf417")) {
                CortexDecoderLibrary.Symbologies.PDF417Properties p = new CortexDecoderLibrary.Symbologies.PDF417Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("pharmacode")) {
                CortexDecoderLibrary.Symbologies.PharmacodeProperties p = new CortexDecoderLibrary.Symbologies.PharmacodeProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("plessey")) {
                CortexDecoderLibrary.Symbologies.PlesseyProperties p = new CortexDecoderLibrary.Symbologies.PlesseyProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("qr_code")) {
                CortexDecoderLibrary.Symbologies.QRProperties p = new CortexDecoderLibrary.Symbologies.QRProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("straight_2_of_5")) {
                CortexDecoderLibrary.Symbologies.Straight2of5Properties p = new CortexDecoderLibrary.Symbologies.Straight2of5Properties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("telepen")) {
                CortexDecoderLibrary.Symbologies.TelepenProperties p = new CortexDecoderLibrary.Symbologies.TelepenProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("trioptic")) {
                CortexDecoderLibrary.Symbologies.TriopticProperties p = new CortexDecoderLibrary.Symbologies.TriopticProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("upc-a")) {
                CortexDecoderLibrary.Symbologies.UPCAProperties p = new CortexDecoderLibrary.Symbologies.UPCAProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("upc-e")) {
                CortexDecoderLibrary.Symbologies.UPCEProperties p = new CortexDecoderLibrary.Symbologies.UPCEProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("australia_post")) {
                CortexDecoderLibrary.Symbologies.AustraliaPostProperties p = new CortexDecoderLibrary.Symbologies.AustraliaPostProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("canada_post")) {
                CortexDecoderLibrary.Symbologies.CanadaPostProperties p = new CortexDecoderLibrary.Symbologies.CanadaPostProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("dutch_post")) {
                CortexDecoderLibrary.Symbologies.DutchPostProperties p = new CortexDecoderLibrary.Symbologies.DutchPostProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("dutch_post")) {
                CortexDecoderLibrary.Symbologies.DutchPostProperties p = new CortexDecoderLibrary.Symbologies.DutchPostProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("japan_post")) {
                CortexDecoderLibrary.Symbologies.JapanPostProperties p = new CortexDecoderLibrary.Symbologies.JapanPostProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("korea_post")) {
                CortexDecoderLibrary.Symbologies.KoreaPostProperties p = new CortexDecoderLibrary.Symbologies.KoreaPostProperties();
                p.enabled = enabled;
                p.saveProperties();
             } else if (key.equals("royal_mail")) {
                CortexDecoderLibrary.Symbologies.RoyalMailProperties p = new CortexDecoderLibrary.Symbologies.RoyalMailProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("usps_intelligent_mail")) {
                CortexDecoderLibrary.Symbologies.USPSIntelligentMailProperties p = new CortexDecoderLibrary.Symbologies.USPSIntelligentMailProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("usps_planet")) {
                CortexDecoderLibrary.Symbologies.USPSPlanetProperties p = new CortexDecoderLibrary.Symbologies.USPSPlanetProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("usps_postnet")) {
                CortexDecoderLibrary.Symbologies.USPSPostnetProperties p = new CortexDecoderLibrary.Symbologies.USPSPostnetProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else if (key.equals("upu")) {
                CortexDecoderLibrary.Symbologies.UPUProperties p = new CortexDecoderLibrary.Symbologies.UPUProperties();
                p.enabled = enabled;
                p.saveProperties();
            } else {
                Log.e(TAG, "Unknown preference key" + key);
            }
        }
    }
}
