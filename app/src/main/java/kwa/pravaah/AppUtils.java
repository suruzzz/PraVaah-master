package kwa.pravaah;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

public class AppUtils {
    private static final String TAG = "KWATest: AppUtils";

    static void dial(String number, Context context) {

        try {
            number = new String(number.trim().replace(" ", "%20").replace("&", "%26")
                    .replace(",", "%2c").replace("(", "%28").replace(")", "%29")
                    .replace("!", "%21").replace("=", "%3D").replace("<", "%3C")
                    .replace(">", "%3E").replace("#", "%23").replace("$", "%24")
                    .replace("'", "%27").replace("*", "%2A").replace("-", "%2D")
                    .replace(".", "%2E").replace("/", "%2F").replace(":", "%3A")
                    .replace(";", "%3B").replace("?", "%3F").replace("@", "%40")
                    .replace("[", "%5B").replace("\\", "%5C").replace("]", "%5D")
                    .replace("_", "%5F").replace("`", "%60").replace("{", "%7B")
                    .replace("|", "%7C").replace("}", "%7D"));




            Toast.makeText(context, "In apputils", Toast.LENGTH_SHORT).show();



            String num = "tel:" + number;
            Uri uri = Uri.parse(num);

            Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
            Toast.makeText(context, num, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Intent.ACTION_CALL, uri);

            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                    || ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "makeCall: Calling Phone activity");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Hello", Toast.LENGTH_SHORT).show();
                context.startActivity(i);
            } else {
                Log.e(TAG, "makeCall: No permission");
                Toast.makeText(context, "No permission for Phone", Toast.LENGTH_SHORT).show();
            }


        }
        catch (Exception e) {
            //getAlertDialog().setMessage("Invalid number");
            e.printStackTrace();
        }

    }


}
