package com.example.yourfitnessapp

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Clase para la actividad del acelerómetro
 */
class AccelerometerActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tvAccelValue: TextView
    private lateinit var tvAccelLevel: TextView

    //Variable para conectar con el acelerómetro
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor?= null

    private var lastAcceleration = 0f
    private var currentAcceleration = 0f
    private var accelerometerIntensity = 0f

    private lateinit var btnEndActivity: Button

    /**
     * Método que se ejecuta al crear la actividad
     * @param savedInstanceState Bundle con el estado de la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        tvAccelValue = findViewById(R.id.tvAccelValue)
        tvAccelLevel = findViewById(R.id.tvAccelLevel)
        btnEndActivity = findViewById<Button>(R.id.btnEndActivity)

        //Obtener el servicio de sensores del sistema y asignárselo a sensorManager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Obtenemos el acelerómetro tras comprobar que existe
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        } else {
            tvAccelLevel.text = "No accelerometer available"
        }

        //Inicializar la aceleración inicial y la última (ojo que el acelerometro puede estar parado y recibiríamos null)
        //Por eso usamos el Gravity_Earth como valor default inicial
        currentAcceleration = SensorManager.GRAVITY_EARTH
        lastAcceleration = SensorManager.GRAVITY_EARTH

        btnEndActivity.setOnClickListener {
            finish()
        }
    }

    /**
     * Método que se ejecuta cuando cambia el valor del acelerómetro
     * @param event SensorEvent con el valor del acelerómetro
     */
    override fun onSensorChanged(event: SensorEvent?) {
        if(event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            var level = ""

            // Obtenemos el fondo del TextView para poder modificarlo de manera independiente
            val backgroundDrawable = tvAccelLevel.background.mutate() as GradientDrawable

            //Calculamos la aceleración según la fórmula física para ello
            val acceleration = sqrt(x * x + y * y + z * z)

            lastAcceleration = currentAcceleration
            currentAcceleration = acceleration

            val intensity = currentAcceleration - lastAcceleration
            accelerometerIntensity = abs(intensity)

            tvAccelValue.text = String.format("%.2f", accelerometerIntensity)

            //Switch para modificar el texto del TextView y el color del background en función de la intensidad
            when {
                accelerometerIntensity < 3f -> {
                    level = "Soft intensity"
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.white))
                    tvAccelLevel.setTypeface(null, Typeface.NORMAL)
                }
                accelerometerIntensity < 10f -> {
                    level = "Medium intensity"
                    tvAccelLevel.setTypeface(null, Typeface.BOLD)
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.teal_200))
                }
                accelerometerIntensity < 20f -> {
                    level = "High intensity"
                    tvAccelLevel.setTypeface(null, Typeface.BOLD)
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.teal_700))
                }
                else -> {
                    level = "Extreme intensity"
                    tvAccelLevel.setTypeface(null, Typeface.BOLD)
                    backgroundDrawable.setColor(ContextCompat.getColor(this, R.color.third))
                }
            }

            tvAccelLevel.text = level
        }
    }

    /**
     * Método que se ejecuta al pausar la actividad y que detiene el acelerómetro
     */
    override fun onPause(){
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    /**
     * Método que se ejecuta al reanudar la actividad y que activa el acelerómetro
     */
    override fun onResume(){
        super.onResume()
        //Si el acelerometro no es nulo (está conectado), ejecuta el bloque después del also, sino, NO
        accelerometer?.also{ sensor ->
            sensorManager.registerListener(
                this,
                sensor,
                SensorManager.SENSOR_DELAY_UI //Frecuencia de actuación (en este caso, moderada)
            )
        }
    }

    /**
     * Método que se ejecuta al destruir la actividad
     * No desarrollamos funcionalidad, pero es necesario para implementar la interfaz de SensorEventListener
     */
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}