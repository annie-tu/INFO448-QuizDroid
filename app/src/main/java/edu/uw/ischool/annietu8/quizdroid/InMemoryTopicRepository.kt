package edu.uw.ischool.annietu8.quizdroid

class InMemoryTopicRepository : TopicRepository{
    // Hard-coded list of topics and quizzes
    private val topic1 = Topic(
        "Math",
        "math is super fun!",
        "Mathematics is a subject that deals with numbers, shapes, logic, quantity, and arrangements.",
        listOf(
            Quiz("What is 2+2?", listOf("1", "2", "3", "4"), 3),
            Quiz("What is 3+3", listOf("6, 0, -5, 2"), 0)
        )
    )

    private val topic2 = Topic(
        "Physics",
        "love physics!",
        "Physics involves the study of matter and its motion and behavior through space and time.",
        listOf(
            Quiz("F = ?", listOf("va", "ma", "-9.8", "x"), 1),
            Quiz(
                "Momentum is...",
                listOf(
                    "mass * velocity",
                    "Earth's gravitational pull",
                    "A fictional concept",
                    "not related to magnitude or direction"
                ),
                0
            )
        )
    )

    private val topic3 = Topic(
        "Marvel Superheroes",
        "cool movies",
        "Marvel Comics is an American comic book publisher and popular movie franchise.",
        listOf(
            Quiz(
                "What is Iron Man's real name?",
                listOf("Tony Stark", "Steve Rogers", "Bruce Banner", "Pepper Potts"), 0
            ),
            Quiz(
                "What was the first Avengers movie (by US release date?",
                listOf(
                    "Iron Man",
                    "Captain America: The First Avenger",
                    "Thor",
                    "Doctor Strange"
                ), 0
            )
        )
    )
    private val topics: List<Topic> =  listOf(topic1, topic2, topic3)
    override fun getTopicTitles(): List<String> {
        return getTopics().map { it.title }
    }
    override fun getTopics(): List<Topic> {
        return topics
    }

}
