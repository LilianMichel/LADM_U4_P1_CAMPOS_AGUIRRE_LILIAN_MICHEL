package mx.edu.ittepic.ladm_u4_p1_campos_aguirre_lilian_michel

import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var baseRemota = FirebaseFirestore.getInstance()
    var Llamadatelefono = ""
    var status = ""
    var permisoMensaje=1
    var permisoLlamada=2
    var hiloAnimacion:HiloAnimacion?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hiloAnimacion = HiloAnimacion(this)
        hiloAnimacion?.start()

        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.SEND_SMS), permisoMensaje)
        }

        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_CALL_LOG), permisoLlamada)
        }


        contactoDeseado.setOnClickListener {
            var v = Intent(this, Main4Activity::class.java)
            startActivity(v)
        }

        contactoNODeseado.setOnClickListener {
            var v = Intent(this, Main5Activity::class.java)
            startActivity(v)
        }

        añadirUsuario.setOnClickListener {
            var v = Intent(this, Main2Activity::class.java)
            startActivity(v)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permisoMensaje){
            permisoLlamada()
        }
    }
    fun permisoLlamada(){
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_CALL_LOG), permisoLlamada)
        }
    }

    fun EnviarMensaje() {
        baseRemota.collection("llamadas")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!=null){
                    Toast.makeText(this,"Error, no existe conexion",Toast.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                for(document in querySnapshot!!){
                    Llamadatelefono= document.getString("telefono").toString()
                }
            }

        if (Llamadatelefono != ""){
            var copiaLlamada=Llamadatelefono
            baseRemota.collection("contacto").whereEqualTo("telefono", copiaLlamada)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException!=null){
                        Toast.makeText(this,"Error, no es posible la conectividad", Toast.LENGTH_LONG).show()
                        return@addSnapshotListener
                    }
                    for(document in querySnapshot!!) {
                        status = document.get("status").toString()
                    }
                }
            if(status=="true"){
                baseRemota.collection("mensajes")
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        for (document in querySnapshot!!) {
                            SmsManager.getDefault().sendTextMessage(copiaLlamada, null, document.getString("deseado"),null,null)
                            Toast.makeText(this, "Mensaje enviado con exito", Toast.LENGTH_LONG)
                                .show()
                        }
                    }

            }else{
                baseRemota.collection("mensajes")
                    .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                        for (document in querySnapshot!!) {
                            SmsManager.getDefault().sendTextMessage(copiaLlamada, null, document.getString("noDeseado"), null,null)
                            Toast.makeText(this, "Mensaje enviado con exito", Toast.LENGTH_LONG)
                                .show()
                        }
                    }
            }
            baseRemota.collection("llamadas").document("qztvhH6DQ6HfN5wBxOvH")
                .update(
                    "telefono",""
                )
        }
    }
}


