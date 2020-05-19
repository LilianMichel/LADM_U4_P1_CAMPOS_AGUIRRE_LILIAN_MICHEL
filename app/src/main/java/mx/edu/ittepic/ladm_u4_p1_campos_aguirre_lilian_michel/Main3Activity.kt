package mx.edu.ittepic.ladm_u4_p1_campos_aguirre_lilian_michel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main3.*

class Main3Activity : AppCompatActivity() {
    var basedatos = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        basedatos.collection("mensajes") //se podria decir que es el nombre de latabla
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    //Si es diferente de null si hay error
                    Toast.makeText(
                        this,
                        "ERROR, NO SE PUEDE ACCEDER A LA CONSULTA",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    return@addSnapshotListener//Termina toda la ejecucion y evita el else
                }//If

                //Se ira en evento y se posiciona en el prmero
                for (document in querySnapshot!!) {
                    editText.setText(document.getString("deseado"))
                    editText2.setText(document.getString("noDeseado"))
                }
            }

        actualizarDeseados.setOnClickListener {
            basedatos.collection("mensajes")
                .document(
                    "lE6uecGjCk2S1iuetsQo")
                .update("deseado", editText.text.toString()
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "ACTUALIZACION REALIZADA", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "ERROR, NO SE PUEDE ACTUALIZAR (NO EXISTE UNA CONEXION)", Toast.LENGTH_LONG)
                        .show()
                }
        }//Fin actualizar

        actualizarNoDeseados.setOnClickListener {
            basedatos.collection("mensajes")
                .document("lE6uecGjCk2S1iuetsQo")
                .update("noDeseado", editText2.text.toString()
                )
                .addOnSuccessListener {
                    Toast.makeText(this, "ACTUALIZACION REALIZADA", Toast.LENGTH_LONG)
                        .show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "ERROR, NO SE PUEDE ACTUALIZAR (NO EXISTE UNA CONEXION)", Toast.LENGTH_LONG)
                        .show()
                }
        }//Fin actualizar




    }
}
