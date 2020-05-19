package mx.edu.ittepic.ladm_u4_p1_campos_aguirre_lilian_michel

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class Receiver:BroadcastReceiver() {
    var baseRemota = FirebaseFirestore.getInstance()
    var cursor : Context ?= null
    var contestar = true
    var i = 0

    override fun onReceive(context : Context, intent: Intent?) {
        try {
            cursor = context
            val tmgr = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val PhoneListener = MyPhoneStateListener()
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE)

        } catch (e: Exception) {
            Log.e("Phone Receive Error", " $e")
        }
    }

    private inner class MyPhoneStateListener : PhoneStateListener() {

        override fun onCallStateChanged(state: Int, incomingNumber: String) {
            Log.d("MyPhoneListener", "$state   incoming no:$incomingNumber")

            if(state == 2){
                contestar = false
            }

            if (state == 0 && contestar == true) {
                val numTelefono = "$incomingNumber"
                Log.d("LLamadaPerdida", numTelefono)
                i++
                try {
                    if(!numTelefono.isEmpty()) {

                        baseRemota.collection("llamadas").document("qztvhH6DQ6HfN5wBxOvH")
                            .update(
                                "telefono",numTelefono
                            )
                    }
                } catch (err : Exception) {

                }
            }
        }
    }
}