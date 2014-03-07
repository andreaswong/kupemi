package aw.app.kupemi;

import android.util.Log;

/**
 * Created by User on 3/5/14.
 */
public class Rujak {
	public static void d(Object tag, Object msg) {
		Log.d(String.valueOf(tag), String.valueOf(msg));
	}

	public static void d(Object msg) {
		Log.d("_Rujak_", String.valueOf(msg));
	}
}
