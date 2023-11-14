package edu.uw.ischool.annietu8.quizdroid
import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class QuizApp : Application() {
    private val TAG : String = "QuizApp"
    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(TAG, "QuizApp is being loaded and run.")
    }

    val topicRepository: TopicRepository by lazy {
        InMemoryTopicRepository()
    }

    companion object {
        lateinit var instance: QuizApp
            private set
    }

    class InMemoryTopicRepository : TopicRepository {

        private val topics: List<Topic> by lazy {
            // Hard-coded list of topics and quizzes
            val topic1 = Topic(
                "Math",
                "math is super fun!",
                "Mathematics is a subject that deals with numbers, shapes, logic, quantity, and arrangements.",
                listOf(
                    Quiz("What is 2+2?", listOf("1", "2", "3", "4"), 3),
                    Quiz("What is 3+3", listOf("6, 0, -5, 2"), 0)
                )
            )

            val topic2 = Topic(
                "Physics",
                "love physics!",
                "Physics involves the study of matter and its motion and behavior through space and time.",
                listOf(
                    Quiz("F = ?", listOf("va", "ma", "-9.8", "x"), 1),
                    Quiz("Momentum is...", listOf("mass * velocity", "Earth's gravitational pull", "A fictional concept", "not related to magnitude or direction"), 0)
                )
            )

            val topic3 = Topic(
                "Marvel Superheroes",
                "cool movies",
                "Marvel Comics is an American comic book publisher and popular movie franchise.",
                listOf(
                    Quiz("What is Iron Man's real name?",
                        listOf("Tony Stark", "Steve Rogers", "Bruce Banner", "Pepper Potts"), 0),
                    Quiz("What was the first Avengers movie (by US release date?",
                        listOf("Iron Man", "Captain America: The First Avenger", "Thor", "Doctor Strange"), 0)
                )
            )
            listOf(topic1, topic2)
        }

        override suspend fun getTopics(): List<Topic> {
            return topics
        }
    }

}