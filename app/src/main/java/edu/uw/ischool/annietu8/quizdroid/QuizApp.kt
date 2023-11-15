package edu.uw.ischool.annietu8.quizdroid
import android.app.Application
import android.util.Log

class QuizApp : Application() {
    private val TAG : String = "QuizApp"
    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(TAG, "QuizApp is being loaded and run.")
    }

    val topicRepository: TopicRepository = InMemoryTopicRepository()

    companion object {
        lateinit var instance: QuizApp
            private set
    }
}