package mx.edu.ittepic.ladm_u4_p1_campos_aguirre_lilian_michel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {
    var baseRemota = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        button.setOnClickListener {
            insertarContacto()
        }
        regresar.setOnClickListener {
            regresar()
        }
        mensaje.setOnClickListener {
            hola()
        }
    }
fun hola(){
    var v = Intent(this, Main3Activity::class.java)
    startActivity(v)
}
    fun insertarContacto(){
        var estadoContacto=false
        if (status.isChecked==true){
            estadoContacto=true
        }
        var datos = hashMapOf(
            "nombre" to nombre.text.toString(),
            "telefono" to telefono.text.toString(),
            "status" to estadoContacto
        )
        baseRemota.collection("contacto")
            .add(datos)
            .addOnSuccessListener {
                mensajeSI()
            }
            .addOnFailureListener {
                mensajeNO()
            }
        limpiarCampos()
    }//Fun insertarContacto
    fun regresar(){
        finish()
    }

    private fun limpiarCampos(){
        nombre.setText("")
        telefono.setText("")
    }
    private fun mensajeSI(){
        Toast.makeText(this, "SE CAPTURO", Toast.LENGTH_LONG)
            .show()
    }
    private fun mensajeNO(){
        Toast.makeText(this, "ERROR, NO SE PUDO CAPTURAR", Toast.LENGTH_LONG)
            .show()

    }

}
