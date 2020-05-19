package mx.edu.ittepic.ladm_u4_p1_campos_aguirre_lilian_michel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main4.*
import kotlinx.android.synthetic.main.activity_main5.*

class Main5Activity : AppCompatActivity() {
    var baseRemota = FirebaseFirestore.getInstance()
    var dataLista = ArrayList<String>()
    var listaID = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)
        baseRemota.collection("contacto").whereEqualTo("status",false) //se podria decir que es el nombre de latabla
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException!=null){
                    //Si es diferente de null si hay error
                    Toast.makeText(this, "ERROR, NO SE PUEDE ACCEDER A LA CONSULTA", Toast.LENGTH_LONG)
                        .show()
                    return@addSnapshotListener//Termina toda la ejecucion y evita el else
                }//If
                dataLista.clear()
                listaID.clear()
                //Se ira en evento y se posiciona en el prmero
                for( document in querySnapshot!!){
                    var cadena=document.getString("nombre")+"\n"+document.getString("telefono")
                    dataLista.add(cadena)
                    listaID.add(document.id)//Guarda el id de los JSON
                }
                if (dataLista.size==0) {
                    dataLista.add("NO HAY DATA")
                }
                var adaptador=
                    ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataLista)
                listaNoDeseados.adapter=adaptador //Recupera los datos en la nube
            }
    }
}
