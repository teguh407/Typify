package com.typify.app.data

/**
 * Deep-dive data for each of the 16 MBTI personality types.
 * Includes famous people, compatibility, cognitive functions, growth paths.
 */
object TypeDetails {

    data class TypeDetail(
        val code: String,
        val famousPeople: List<String>,
        val bestMatches: List<String>,
        val worstMatches: List<String>,
        val cognitiveFunctions: List<CognitiveFunction>,
        val growthPath: List<String>,
        val blindSpot: String,
        val dailyInsights: List<String>
    )

    data class CognitiveFunction(
        val name: String,
        val fullName: String,
        val description: String,
        val emoji: String
    )

    private val allTypes = mapOf(
        "INTJ" to TypeDetail(
            code = "INTJ",
            famousPeople = listOf("Elon Musk", "Nikola Tesla", "Friedrich Nietzsche", "Lisa Su", "Christopher Nolan"),
            bestMatches = listOf("ENFP", "ENTP"),
            worstMatches = listOf("ESFP", "ISFP"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Ni", "Introverted Intuition", "Sees patterns and future implications", "🔮"),
                CognitiveFunction("Te", "Extraverted Thinking", "Organizes and executes systems", "⚙️"),
                CognitiveFunction("Fi", "Introverted Feeling", "Internal values and authenticity", "💎"),
                CognitiveFunction("Se", "Extraverted Sensing", "Present-moment awareness", "👁️")
            ),
            growthPath = listOf(
                "Develop your feeling side — express emotions openly",
                "Practice presence — stop living only in the future",
                "Let go of perfectionism — ship imperfect work",
                "Connect with others through shared vulnerability"
            ),
            blindSpot = "Emotional expression — you analyze feelings instead of feeling them",
            dailyInsights = listOf(
                "Your strategic mind sees 10 moves ahead. Today, try seeing 1 step at a time.",
                "Not everything needs to be optimized. Some things just need to be experienced.",
                "Your standards are high — remember others are doing their best too.",
                "Ideas are cheap. Execution is everything. Ship something today.",
                "Vulnerability isn't weakness — it's the ultimate strength."
            )
        ),
        "INTP" to TypeDetail(
            code = "INTP",
            famousPeople = listOf("Albert Einstein", "Bill Gates", "Marie Curie", "Tina Fey", "Sherlock Holmes"),
            bestMatches = listOf("ENTJ", "ESTJ"),
            worstMatches = listOf("ESFJ", "ISFJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Ti", "Introverted Thinking", "Analyzes for internal consistency", "🔬"),
                CognitiveFunction("Ne", "Extraverted Intuition", "Explores possibilities and ideas", "💡"),
                CognitiveFunction("Si", "Introverted Sensing", "Recalls detailed past experiences", "📚"),
                CognitiveFunction("Fe", "Extraverted Feeling", "Reads social dynamics", "🤝")
            ),
            growthPath = listOf(
                "Turn analysis into action — stop researching, start doing",
                "Connect emotionally — logic isn't always the answer",
                "Finish what you start — avoid the next-shiny-thing trap",
                "Share your ideas — they're more valuable than you think"
            ),
            blindSpot = "Analysis paralysis — you think so much you never start",
            dailyInsights = listOf(
                "Your mind generates infinite possibilities. Today, pick ONE and execute.",
                "Not every idea needs to be perfect before sharing. Ship it.",
                "Emotions aren't illogical — they're data you haven't learned to read.",
                "You understand systems better than anyone. Teach someone today.",
                "The best experiment is the one you actually run."
            )
        ),
        "ENTJ" to TypeDetail(
            code = "ENTJ",
            famousPeople = listOf("Steve Jobs", "Margaret Thatcher", "Gordon Ramsay", "Napoleon Bonaparte", "Jeff Bezos"),
            bestMatches = listOf("INTP", "INFP"),
            worstMatches = listOf("ISFP", "ISFJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Te", "Extraverted Thinking", "Drives efficiency and results", "⚡"),
                CognitiveFunction("Ni", "Introverted Intuition", "Visionary long-term planning", "🎯"),
                CognitiveFunction("Se", "Extraverted Sensing", "Takes immediate action", "🔥"),
                CognitiveFunction("Fi", "Introverted Feeling", "Personal values drive decisions", "❤️")
            ),
            growthPath = listOf(
                "Listen before directing — others have insights too",
                "Patience — not everyone operates at your speed",
                "Empathy isn't weakness — it's leadership superpower",
                "Celebrate small wins — not just the final result"
            ),
            blindSpot = "Dismissiveness — you can steamroll others without noticing",
            dailyInsights = listOf(
                "You're a natural leader. Today, try leading by asking, not telling.",
                "Efficiency isn't just about speed — it's about sustainable pace.",
                "Your vision inspires. Share it with someone who needs direction.",
                "Not every problem needs your command. Some need your presence.",
                "The strongest leaders admit when they're wrong."
            )
        ),
        "ENTP" to TypeDetail(
            code = "ENTP",
            famousPeople = listOf("Thomas Edison", "Leonardo da Vinci", "Mark Cuban", "Sacha Baron Cohen", "Tony Stark"),
            bestMatches = listOf("INFJ", "INTJ"),
            worstMatches = listOf("ISFJ", "ISTJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Ne", "Extraverted Intuition", "Brainstorms and innovates", "🌪️"),
                CognitiveFunction("Ti", "Introverted Thinking", "Finds logical frameworks", "🧩"),
                CognitiveFunction("Fe", "Extraverted Feeling", "Charm and social adaptability", "😎"),
                CognitiveFunction("Si", "Introverted Sensing", "Routine and detail management", "📌")
            ),
            growthPath = listOf(
                "Follow through — the idea is 10%, execution is 90%",
                "Stop debating for sport — some conversations need sincerity",
                "Respect routines — structure enables creativity, not kills it",
                "Listen to understand, not to respond"
            ),
            blindSpot = "Starting but never finishing — you chase the next debate",
            dailyInsights = listOf(
                "You see possibilities everywhere. Today, finish what you started yesterday.",
                "Not every conversation is a debate. Sometimes people just want to be heard.",
                "Your charm opens doors. Use it to build, not just to entertain.",
                "Structure isn't your enemy — it's the scaffolding for your chaos.",
                "The best idea is the one you actually execute."
            )
        ),
        "INFJ" to TypeDetail(
            code = "INFJ",
            famousPeople = listOf("Martin Luther King Jr.", "Gandhi", "Carl Jung", "Taylor Swift", "Goose from Top Gun"),
            bestMatches = listOf("ENTP", "ENFP"),
            worstMatches = listOf("ESTP", "ESTJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Ni", "Introverted Intuition", "Deep insight and future vision", "🔮"),
                CognitiveFunction("Fe", "Extraverted Feeling", "Empathic social harmony", "🌊"),
                CognitiveFunction("Ti", "Introverted Thinking", "Internal logical analysis", "🧠"),
                CognitiveFunction("Se", "Extraverted Sensing", "Physical presence awareness", "🍃")
            ),
            growthPath = listOf(
                "Set boundaries — you can't heal everyone",
                "Trust your intuition but verify with facts",
                "Take action — planning without execution is just dreaming",
                "Practice self-care — you pour from an empty cup"
            ),
            blindSpot = "Burnout — you give until you're empty, then disappear",
            dailyInsights = listOf(
                "You feel others' emotions deeply. Today, check in with YOUR feelings.",
                "Your intuition is a gift. Share it — the world needs your vision.",
                "Saying no is an act of self-love. Practice it today.",
                "You don't have to fix everyone. Some people need to fix themselves.",
                "Your depth scares some people. Find those who dive with you."
            )
        ),
        "INFP" to TypeDetail(
            code = "INFP",
            famousPeople = listOf("William Shakespeare", "J.R.R. Tolkien", "Frodo Baggins", "Audrey Hepburn", "Princess Diana"),
            bestMatches = listOf("ENTJ", "ENFJ"),
            worstMatches = listOf("ESTJ", "ESTP"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Fi", "Introverted Feeling", "Deep personal values", "🌹"),
                CognitiveFunction("Ne", "Extraverted Intuition", "Imaginative exploration", "🎨"),
                CognitiveFunction("Si", "Introverted Sensing", "Nostalgic memory", "📖"),
                CognitiveFunction("Te", "Extraverted Thinking", "Organizing the outer world", "📋")
            ),
            growthPath = listOf(
                "Take action on your ideals — don't just dream them",
                "Accept imperfection — the world isn't as beautiful as your vision",
                "Set boundaries — your empathy attracts energy vampires",
                "Share your creative work — it's more valuable than you think"
            ),
            blindSpot = "Idealism — you compare reality to an impossible standard",
            dailyInsights = listOf(
                "Your imagination is a superpower. Create something today.",
                "Not everything has to be meaningful. Sometimes fun is enough.",
                "Your values are your compass. Trust them, but stay flexible.",
                "You see beauty others miss. Share that perspective today.",
                "The world needs your gentleness. Don't let it harden you."
            )
        ),
        "ENFJ" to TypeDetail(
            code = "ENFJ",
            famousPeople = listOf("Barack Obama", "Oprah Winfrey", "Maya Angelou", "Sean Connery", "Dumbledore"),
            bestMatches = listOf("INFP", "ISFP"),
            worstMatches = listOf("ISTP", "ISTJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Fe", "Extraverted Feeling", "Connects and inspires others", "✨"),
                CognitiveFunction("Ni", "Introverted Intuition", "Sees people's potential", "🔮"),
                CognitiveFunction("Se", "Extraverted Sensing", "Engages with the moment", "🌟"),
                CognitiveFunction("Ti", "Introverted Thinking", "Internal logical analysis", "⚙️")
            ),
            growthPath = listOf(
                "Focus on yourself — you can't pour from an empty cup",
                "Accept that not everyone wants to grow — and that's okay",
                "Make decisions for yourself, not just for others",
                "Learn to receive help — you don't have to do it all"
            ),
            blindSpot = "Over-giving — you help others to avoid your own needs",
            dailyInsights = listOf(
                "You inspire everyone around you. Today, inspire yourself.",
                "It's okay to be selfish sometimes. Your needs matter too.",
                "Not everyone needs fixing. Some people just need a friend.",
                "Your warmth is contagious. Spread it intentionally today.",
                "Receiving is a skill. Let someone help you today."
            )
        ),
        "ENFP" to TypeDetail(
            code = "ENFP",
            famousPeople = listOf("Robin Williams", "Walt Disney", "Quentin Tarantino", "Ariel (Little Mermaid)", "Ellen DeGeneres"),
            bestMatches = listOf("INTJ", "INFJ"),
            worstMatches = listOf("ISTJ", "ISFJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Ne", "Extraverted Intuition", "Endless curiosity and ideas", "🌈"),
                CognitiveFunction("Fi", "Introverted Feeling", "Authentic personal values", "💝"),
                CognitiveFunction("Te", "Extraverted Thinking", "Organizes when motivated", "📐"),
                CognitiveFunction("Si", "Introverted Sensing", "Cherishes memories", "📷")
            ),
            growthPath = listOf(
                "Focus — you start everything, finish nothing",
                "Create routines — freedom needs structure",
                "Don't take feedback personally — it's about the work, not you",
                "Ground your enthusiasm with follow-through"
            ),
            blindSpot = "Overcommitment — you say yes to everything then burn out",
            dailyInsights = listOf(
                "Your enthusiasm is contagious. Channel it into ONE project today.",
                "Not every idea is worth pursuing. Some are just thoughts.",
                "You feel deeply. Use that to connect, not to overwhelm.",
                "Routine isn't a cage — it's the launchpad for your adventures.",
                "Finish what you started. The last 10% is where the magic happens."
            )
        ),
        "ISTJ" to TypeDetail(
            code = "ISTJ",
            famousPeople = listOf("George Washington", "Warren Buffett", "Natalie Portman", "Hermione Granger", "Darth Vader"),
            bestMatches = listOf("ESFP", "ESTP"),
            worstMatches = listOf("ENFP", "ENTP"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Si", "Introverted Sensing", "Reliable memory and routine", "📚"),
                CognitiveFunction("Te", "Extraverted Thinking", "Efficient execution", "📊"),
                CognitiveFunction("Fi", "Introverted Feeling", "Loyalty and duty", "🛡️"),
                CognitiveFunction("Ne", "Extraverted Intuition", "Exploring new approaches", "🔧")
            ),
            growthPath = listOf(
                "Embrace change — not everything needs a precedent",
                "Express emotions — vulnerability builds deeper connections",
                "Try new approaches — your way works but isn't the only way",
                "Relax — you don't always need to be responsible"
            ),
            blindSpot = "Rigidity — you resist change even when it's beneficial",
            dailyInsights = listOf(
                "Your reliability is your superpower. Today, be reliable to YOURSELF.",
                "Not everything needs a plan. Try spontaneity once.",
                "Your experience is valuable. Share it with someone younger.",
                "It's okay to have fun. Responsibility and joy aren't enemies.",
                "Tradition has value, but so does innovation. Find the balance."
            )
        ),
        "ISFJ" to TypeDetail(
            code = "ISFJ",
            famousPeople = listOf("Mother Teresa", "Queen Elizabeth II", "Rosa Parks", "Samwise Gamgee", "Beyoncé"),
            bestMatches = listOf("ESFP", "ESTP"),
            worstMatches = listOf("ENTP", "ENTJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Si", "Introverted Sensing", "Detailed memory and care", "💗"),
                CognitiveFunction("Fe", "Extraverted Feeling", "Nurtures and supports", "🤗"),
                CognitiveFunction("Ti", "Introverted Thinking", "Quiet logical analysis", "🔍"),
                CognitiveFunction("Ne", "Extraverted Intuition", "Gentle exploration", "🌱")
            ),
            growthPath = listOf(
                "Speak up — your needs matter as much as others'",
                "Set boundaries — saying no is an act of love (for yourself)",
                "Try new things — comfort zones become cages",
                "Accept help — you don't have to carry everything alone"
            ),
            blindSpot = "Self-sacrifice — you give so much you forget yourself",
            dailyInsights = listOf(
                "You take care of everyone. Today, let someone take care of you.",
                "Your kindness changes lives. Don't let it deplete you.",
                "Saying no doesn't make you selfish. It makes you sustainable.",
                "You notice what others miss. Share your observations today.",
                "Your loyalty is precious. Make sure it's earned."
            )
        ),
        "ESTJ" to TypeDetail(
            code = "ESTJ",
            famousPeople = listOf("Frank Sinatra", "Sonia Sotomayor", "Michelle Obama", "James Monroe", "Gordon Gekko"),
            bestMatches = listOf("ISTP", "INTP"),
            worstMatches = listOf("INFP", "INFJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Te", "Extraverted Thinking", "Organizes and leads", "📋"),
                CognitiveFunction("Si", "Introverted Sensing", "Respects tradition and order", "🏛️"),
                CognitiveFunction("Ne", "Extraverted Intuition", "Brainstorms improvements", "💡"),
                CognitiveFunction("Fi", "Introverted Feeling", "Quiet personal values", "🌿")
            ),
            growthPath = listOf(
                "Listen to emotions — yours and others'",
                "Flexibility — not everything fits your system",
                "Praise more than you criticize",
                "Ask before you direct — collaboration beats command"
            ),
            blindSpot = "Control — you take over when you should let others lead",
            dailyInsights = listOf(
                "You get things done. Today, ask others how they want to help.",
                "Not everything needs to be efficient. Some things need to be felt.",
                "Your leadership is strongest when you listen first.",
                "Praise someone today. Your words carry weight.",
                "Control is an illusion. Trust the process."
            )
        ),
        "ESFJ" to TypeDetail(
            code = "ESFJ",
            famousPeople = listOf("Taylor Swift", "Jennifer Garner", "Hugh Jackman", "Sally Field", "Steve Harvey"),
            bestMatches = listOf("ISFP", "ISTP"),
            worstMatches = listOf("INTP", "INTJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Fe", "Extraverted Feeling", "Creates harmony and community", "🌻"),
                CognitiveFunction("Si", "Introverted Sensing", "Remembers what matters to others", "🎁"),
                CognitiveFunction("Ne", "Extraverted Intuition", "Open to new social ideas", "🎈"),
                CognitiveFunction("Ti", "Introverted Thinking", "Quiet logical analysis", "⚙️")
            ),
            growthPath = listOf(
                "Accept that not everyone wants your help",
                "Make decisions for yourself, not just for others' approval",
                "Handle conflict directly — don't avoid it to keep peace",
                "Develop your own identity beyond relationships"
            ),
            blindSpot = "People-pleasing — you lose yourself trying to keep everyone happy",
            dailyInsights = listOf(
                "You bring people together. Today, connect with yourself first.",
                "Not everyone needs your help. Some need your trust.",
                "Your generosity is beautiful. Make sure it's replenished.",
                "It's okay to disappoint people. Your authenticity matters more.",
                "You are more than your relationships. Discover solo joy today."
            )
        ),
        "ISTP" to TypeDetail(
            code = "ISTP",
            famousPeople = listOf("Clint Eastwood", "Bruce Lee", "Daniel Craig", "Boba Fett", "Katniss Everdeen"),
            bestMatches = listOf("ESFJ", "ESTJ"),
            worstMatches = listOf("ENFJ", "ENFP"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Ti", "Introverted Thinking", "Analyzes how things work", "🔧"),
                CognitiveFunction("Se", "Extraverted Sensing", "Lives in the moment", "⚡"),
                CognitiveFunction("Ni", "Introverted Intuition", "Sudden insights", "💡"),
                CognitiveFunction("Fe", "Extraverted Feeling", "Under-appreciated warmth", "🔥")
            ),
            growthPath = listOf(
                "Express feelings — people can't read your mind",
                "Commit — you avoid being tied down but growth needs roots",
                "Share your knowledge — teach what you know",
                "Plan ahead — spontaneity is fun but not a life strategy"
            ),
            blindSpot = "Emotional distance — you're present but not connected",
            dailyInsights = listOf(
                "You fix everything. Today, fix your connection with someone.",
                "Your independence is freedom. Connection doesn't reduce it.",
                "Words matter. Say what you feel, not just what you think.",
                "Not everything needs to be practical. Some things just matter.",
                "Your skills are valuable. Teach someone today."
            )
        ),
        "ISFP" to TypeDetail(
            code = "ISFP",
            famousPeople = listOf("Bob Dylan", "Michael Jackson", "Frida Kahlo", "Lady Gaga", "Mulan"),
            bestMatches = listOf("ESFJ", "ENFJ"),
            worstMatches = listOf("ENTJ", "ESTJ"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Fi", "Introverted Feeling", "Deeply authentic values", "🎨"),
                CognitiveFunction("Se", "Extraverted Sensing", "Appreciates beauty in the moment", "🌸"),
                CognitiveFunction("Ni", "Introverted Intuition", "Quiet inner vision", "🌌"),
                CognitiveFunction("Te", "Extraverted Thinking", "Organizes when needed", "📌")
            ),
            growthPath = listOf(
                "Speak up — your perspective matters",
                "Plan ahead — living in the moment has limits",
                "Share your art — the world needs your expression",
                "Handle conflict — avoidance breeds resentment"
            ),
            blindSpot = "Avoidance — you disappear instead of confronting issues",
            dailyInsights = listOf(
                "You see beauty everywhere. Create something today.",
                "Your silence isn't peace. Sometimes it needs words.",
                "Share your art. Even if it's not perfect, it's yours.",
                "You don't need to agree to understand. Listen actively today.",
                "Your sensitivity is a gift. Protect it, but don't hide it."
            )
        ),
        "ESTP" to TypeDetail(
            code = "ESTP",
            famousPeople = listOf("Madonna", "Ernest Hemingway", "Donald Trump", "James Bond", "Han Solo"),
            bestMatches = listOf("ISFJ", "ISTJ"),
            worstMatches = listOf("INFJ", "INFP"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Se", "Extraverted Sensing", "Acts boldly in the moment", "🔥"),
                CognitiveFunction("Ti", "Introverted Thinking", "Quick tactical analysis", "♟️"),
                CognitiveFunction("Fe", "Extraverted Feeling", "Charms and reads rooms", "😎"),
                CognitiveFunction("Ni", "Introverted Intuition", "Gut instincts", "🎯")
            ),
            growthPath = listOf(
                "Think before you act — impulse isn't always wisdom",
                "Consider consequences — fun now can cost later",
                "Develop patience — not everything is a sprint",
                "Listen to feelings — yours and others'"
            ),
            blindSpot = "Impulsivity — you act first, think later, regret often",
            dailyInsights = listOf(
                "You live boldly. Today, try thinking before your next move.",
                "Not everything is a challenge. Some things just need patience.",
                "Your energy is contagious. Use it to build, not just to thrill.",
                "The best adventure is the one you plan for. Try it.",
                "Feelings aren't weakness. They're information. Listen to them."
            )
        ),
        "ESFP" to TypeDetail(
            code = "ESFP",
            famousPeople = listOf("Marilyn Monroe", "Adele", "Jamie Oliver", "Serena Williams", "Peter Pan"),
            bestMatches = listOf("ISTJ", "ISFJ"),
            worstMatches = listOf("INTJ", "INTP"),
            cognitiveFunctions = listOf(
                CognitiveFunction("Se", "Extraverted Sensing", "Full sensory presence", "🎉"),
                CognitiveFunction("Fi", "Introverted Feeling", "Genuine emotional depth", "💗"),
                CognitiveFunction("Te", "Extraverted Thinking", "Organizes events and people", "🎪"),
                CognitiveFunction("Ni", "Introverted Intuition", "Occasional deep insights", "✨")
            ),
            growthPath = listOf(
                "Plan for the future — the party doesn't last forever",
                "Face difficult emotions — avoidance costs more later",
                "Follow through — enthusiasm without completion is noise",
                "Develop depth — there's more to life than the surface"
            ),
            blindSpot = "Surface-level living — you avoid anything heavy or painful",
            dailyInsights = listOf(
                "You light up every room. Today, sit with yourself in silence.",
                "Fun is your gift. Depth is your growth. Try both today.",
                "Not every feeling needs to be fixed. Some just need to be felt.",
                "Your energy inspires. Use it to finish what you started.",
                "The present moment is beautiful. So is planning for the next one."
            )
        )
    )

    fun getDetail(typeCode: String): TypeDetail? = allTypes[typeCode.uppercase()]

    fun getAllCodes(): List<String> = allTypes.keys.sorted()

    fun getCompatibility(yourType: String, theirType: String): String {
        val detail = allTypes[yourType.uppercase()] ?: return "Unknown"
        return when (theirType.uppercase()) {
            in detail.bestMatches -> "✅ Great Match — natural chemistry"
            in detail.worstMatches -> "⚠️ Challenging — growth opportunity"
            else -> "🤝 Moderate — different but workable"
        }
    }
}
