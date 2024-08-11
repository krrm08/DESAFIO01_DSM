package sv.edu.udb.renta

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var Nombre: EditText
    lateinit var SalarioBase: EditText
    lateinit var Resultado: TextView
    lateinit var Calcular: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Nombre = findViewById(R.id.Nombre)
        SalarioBase = findViewById(R.id.SalarioBase)
        Resultado = findViewById(R.id.Resultado)
        Calcular = findViewById(R.id.Calcular)

        Calcular.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val nombre: String = Nombre.text.toString()
        val salarioBaseStr: String = SalarioBase.text.toString()

        if (nombre.isEmpty() || salarioBaseStr.isEmpty()) {
            Resultado.text = "Por favor, ingrese el nombre y el salario base."
            return
        }

        try {
            val salarioBase = salarioBaseStr.toDouble()
            val renta = calcularRenta(salarioBase)
            val afp = salarioBase * 0.0725
            val isss = salarioBase * 0.03
            val salarioNeto = salarioBase - renta - afp - isss

            Resultado.text = """
                Nombre: $nombre
                Salario Base: $salarioBase
                Renta: $renta
                AFP: $afp
                ISSS: $isss
                Salario Neto: $salarioNeto
            """.trimIndent()

        } catch (e: NumberFormatException) {
            Resultado.text = "Por favor, ingrese un salario válido."
        }
    }

    private fun calcularRenta(salario: Double): Double {
        return when {
            salario <= 472.0 -> 0.0
            salario <= 895.24 -> (salario - 472.0) * 0.1 + 17.67
            salario <= 2038.10 -> (salario - 895.24) * 0.2 + 60.0
            else -> (salario - 2038.10) * 0.3 + 288.57
        }
    }
}
