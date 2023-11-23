package com.example.memoriary.ui.home

import android.app.AlertDialog
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memoriary.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment(), SensorEventListener {
    lateinit var binding: FragmentExerciseBinding
    lateinit var sensorManager: SensorManager
    private var shakeCount = 0
    private var targetShakeCount = 0
    private val delayBetweenShakes = 800L // 2 seconds
    private val handler = Handler(Looper.getMainLooper())
    private var isShakeInProgress = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        }

        startExercise()
    }

    private fun startExercise() {
        val randomTimes = (1..5).random()
        targetShakeCount = randomTimes
        shakeCount = 0

        // Initiate the first shake after a delay
        binding.instruction.text = "Shake your phone $targetShakeCount times"
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            handleShake(event)
        }
    }

    private fun handleShake(event: SensorEvent) {
        if (!isShakeInProgress) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val acceleration =
                Math.sqrt((x * x + y * y + z * z).toDouble()) - SensorManager.GRAVITY_EARTH

            if (acceleration > 10) { // You can adjust this threshold
                Log.d("Exercise", "$acceleration")
                shakeCount++
                showProgress(targetShakeCount - shakeCount)
                isShakeInProgress = true

                // Delay for 2 seconds before allowing the next shake
                handler.postDelayed({
                    isShakeInProgress = false
                }, delayBetweenShakes)

                if (shakeCount == targetShakeCount) {
                    showSuccessDialog()
                    shakeCount = 0
                    startExercise()
                }
            }
        }
    }

    private fun showProgress(num: Int) {
        binding.instruction.text = "$num more!"
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Success!")
            .setMessage("You completed the shake exercise.")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //Do nothing
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sensorManager.unregisterListener(this)
    }
}