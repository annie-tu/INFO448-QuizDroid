package edu.uw.ischool.annietu8.quizdroid
import android.app.AlertDialog
import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import android.provider.Settings
import kotlin.system.exitProcess

class DownloadService : IntentService("DownloadService") {
    private val TAG: String = "DownloadService"
    override fun onHandleIntent(intent: Intent?) {
        // Retrieve the URL from Preferences
        val preferences = getSharedPreferences("your_preferences", Context.MODE_PRIVATE)
        val url = preferences.getString("question_url", "default_url").toString()

        // Check if internet connection is available
        Log.i("DownloadService", "show")
        if (isNetworkAvailable() && !isAirplaneModeOn()) {
            downloadFile(url)
        } else {
            if (isAirplaneModeOn()) {
                showAirplaneModeDialog()
            } else {
                showToast("No internet connection")
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting == true
    }

    private fun downloadFile(url :String) {
        try {
            showToast("Attempting download")
            val url = URL(url)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream = BufferedInputStream(url.openStream())
            val fileOutputStream =
                openFileOutput("questions.json", Context.MODE_PRIVATE)

            val data = ByteArray(1024)
            var count: Int

            while (inputStream.read(data).also { count = it } != -1) {
                fileOutputStream.write(data, 0, count)
            }

            fileOutputStream.flush()
            fileOutputStream.close()
            inputStream.close()
            Log.i(TAG, "JSON file downloaded successfully")
            showToast("File downloaded successfully")
        } catch (e: Exception) {
            Log.e("DownloadService", "Error downloading file: ${e.message}")
            // showToast("Error downloading file")
            showRetryOrQuitDialog(url)
        }
    }

    private fun isAirplaneModeOn(): Boolean {
        return Settings.System.getInt(
            contentResolver,
            Settings.Global.AIRPLANE_MODE_ON, 0
        ) != 0
    }

    private fun showAirplaneModeDialog() {
        AlertDialog.Builder(this)
            .setTitle("Airplane Mode")
            .setMessage("You are in Airplane Mode. Do you want to turn it off?")
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS))
            }
            .setNegativeButton("No") { _, _ -> }
            .show()
    }

    private fun showRetryOrQuitDialog(url: String) {
        AlertDialog.Builder(this)
            .setTitle("Download Failed")
            .setMessage("Failed to download questions. Do you want to retry or quit?")
            .setPositiveButton("Retry") { _, _ ->
                // Retry the download
                downloadFile(url)
            }
            .setNegativeButton("Quit") { _, _ ->
                exitProcess(0)
            }.show()
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
