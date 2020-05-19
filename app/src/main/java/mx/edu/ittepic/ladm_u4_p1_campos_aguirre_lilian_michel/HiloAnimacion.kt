package mx.edu.ittepic.ladm_u4_p1_campos_aguirre_lilian_michel

class HiloAnimacion(p:MainActivity) : Thread() {
    var puntero = p

    override fun run() {
        super.run()
        while (true){
            sleep(9000)
            puntero.runOnUiThread {
            puntero.EnviarMensaje()
            }
        }
    }

}