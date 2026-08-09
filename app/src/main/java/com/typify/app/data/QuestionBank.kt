package com.typify.app.data

import com.typify.app.model.*

object QuestionBank {

    val mbtiQuestions: List<Question> = listOf(
        // E-I Dimension (15 questions)
        Question(1, "At a party, I find myself energized by being around lots of people.", Dimension.EI, Direction.LEFT),
        Question(2, "I prefer spending my Friday night at a social gathering rather than staying home.", Dimension.EI, Direction.LEFT),
        Question(3, "After being in a crowd, I need alone time to recharge.", Dimension.EI, Direction.RIGHT),
        Question(4, "I think out loud and process ideas by talking with others.", Dimension.EI, Direction.LEFT),
        Question(5, "I prefer one-on-one conversations over group discussions.", Dimension.EI, Direction.RIGHT),
        Question(6, "I feel drained after socializing for a few hours.", Dimension.EI, Direction.RIGHT),
        Question(7, "I'm usually the one to initiate conversations with strangers.", Dimension.EI, Direction.LEFT),
        Question(8, "I prefer quiet environments over loud, busy ones.", Dimension.EI, Direction.RIGHT),
        Question(9, "I get energy from being active and interacting with the world.", Dimension.EI, Direction.LEFT),
        Question(10, "I need a lot of personal space and quiet time.", Dimension.EI, Direction.RIGHT),
        Question(11, "I enjoy being the center of attention.", Dimension.EI, Direction.LEFT),
        Question(12, "I'd rather observe from the sidelines than be in the spotlight.", Dimension.EI, Direction.RIGHT),
        Question(13, "I have a wide circle of friends and acquaintances.", Dimension.EI, Direction.LEFT),
        Question(14, "I prefer deep conversations with a few close friends.", Dimension.EI, Direction.RIGHT),
        Question(15, "Silence in a conversation doesn't make me uncomfortable.", Dimension.EI, Direction.RIGHT),

        // S-N Dimension (15 questions)
        Question(16, "I focus on concrete facts and details rather than abstract concepts.", Dimension.SN, Direction.LEFT),
        Question(17, "I trust my intuition over past experience when making decisions.", Dimension.SN, Direction.RIGHT),
        Question(18, "I prefer learning practical skills over theoretical ideas.", Dimension.SN, Direction.LEFT),
        Question(19, "I often think about future possibilities and 'what could be'.", Dimension.SN, Direction.RIGHT),
        Question(20, "I'm more interested in what is real and tangible than what is possible.", Dimension.SN, Direction.LEFT),
        Question(21, "I enjoy abstract theories and philosophical discussions.", Dimension.SN, Direction.RIGHT),
        Question(22, "I rely on my five senses to understand the world.", Dimension.SN, Direction.LEFT),
        Question(23, "I notice patterns and connections that others miss.", Dimension.SN, Direction.RIGHT),
        Question(24, "I prefer step-by-step instructions over general guidelines.", Dimension.SN, Direction.LEFT),
        Question(25, "I like to imagine how things could be improved.", Dimension.SN, Direction.RIGHT),
        Question(26, "I value experience and proven methods over innovation.", Dimension.SN, Direction.LEFT),
        Question(27, "I'm drawn to creative and unconventional ideas.", Dimension.SN, Direction.RIGHT),
        Question(28, "I focus on the present reality rather than future possibilities.", Dimension.SN, Direction.LEFT),
        Question(29, "I enjoy discussing symbolic meanings and metaphors.", Dimension.SN, Direction.RIGHT),
        Question(30, "I prefer art that depicts real-life scenes over abstract art.", Dimension.SN, Direction.LEFT),

        // T-F Dimension (15 questions)
        Question(31, "I make decisions based on logic rather than feelings.", Dimension.TF, Direction.LEFT),
        Question(32, "I prioritize harmony and people's feelings over objective truth.", Dimension.TF, Direction.RIGHT),
        Question(33, "I value fairness and justice over empathy and compassion.", Dimension.TF, Direction.LEFT),
        Question(34, "I can easily pick up on others' emotions.", Dimension.TF, Direction.RIGHT),
        Question(35, "I prefer to analyze problems objectively.", Dimension.TF, Direction.LEFT),
        Question(36, "I consider how decisions will affect people before making them.", Dimension.TF, Direction.RIGHT),
        Question(37, "I'm good at staying calm in emotionally charged situations.", Dimension.TF, Direction.LEFT),
        Question(38, "I feel what others are feeling as if they were my own emotions.", Dimension.TF, Direction.RIGHT),
        Question(39, "I believe truth is more important than tact.", Dimension.TF, Direction.LEFT),
        Question(40, "I go out of my way to make people feel comfortable.", Dimension.TF, Direction.RIGHT),
        Question(41, "I evaluate others by their competence, not their warmth.", Dimension.TF, Direction.LEFT),
        Question(42, "I make decisions with my heart, not just my head.", Dimension.TF, Direction.RIGHT),
        Question(43, "I find it hard to give negative feedback to someone.", Dimension.TF, Direction.RIGHT),
        Question(44, "I'm more interested in fairness than in pleasing everyone.", Dimension.TF, Direction.LEFT),
        Question(45, "I take criticism personally even when it's constructive.", Dimension.TF, Direction.RIGHT),

        // J-P Dimension (15 questions)
        Question(46, "I prefer having a plan and sticking to it.", Dimension.JP, Direction.LEFT),
        Question(47, "I like to keep my options open rather than committing early.", Dimension.JP, Direction.RIGHT),
        Question(48, "I feel stressed when things are disorganized or unplanned.", Dimension.JP, Direction.LEFT),
        Question(49, "I'm comfortable adapting to unexpected changes.", Dimension.JP, Direction.RIGHT),
        Question(50, "I prefer to finish tasks well before the deadline.", Dimension.JP, Direction.LEFT),
        Question(51, "I work best under pressure and last-minute urgency.", Dimension.JP, Direction.RIGHT),
        Question(52, "I like having a structured routine.", Dimension.JP, Direction.LEFT),
        Question(53, "I prefer spontaneity over schedules.", Dimension.JP, Direction.RIGHT),
        Question(54, "I make lists and check things off as I go.", Dimension.JP, Direction.LEFT),
        Question(55, "I find routines boring and restrictive.", Dimension.JP, Direction.RIGHT),
        Question(56, "I need closure and dislike leaving things unresolved.", Dimension.JP, Direction.LEFT),
        Question(57, "I enjoy exploring options before deciding.", Dimension.JP, Direction.RIGHT),
        Question(58, "I'm happiest when my environment is organized.", Dimension.JP, Direction.LEFT),
        Question(59, "I find too much structure stifling.", Dimension.JP, Direction.RIGHT),
        Question(60, "I prefer making decisions quickly and moving on.", Dimension.JP, Direction.LEFT),
    )

    val testTypes: List<TestType> = listOf(
        TestType(
            id = "mbti",
            title = "MBTI Test",
            subtitle = "16 Personality Types",
            icon = "🧠",
            questionCount = 60,
            gradientColors = listOf(0xFF6366F1, 0xFFEC4899)
        ),
        TestType(
            id = "enneagram",
            title = "Enneagram",
            subtitle = "9 Types — Coming Soon",
            icon = "🌀",
            questionCount = 36,
            gradientColors = listOf(0xFF8B5CF6, 0xFF06B6D4),
            available = false
        ),
        TestType(
            id = "bigfive",
            title = "Big Five",
            subtitle = "OCEAN Model — Coming Soon",
            icon = "🌊",
            questionCount = 50,
            gradientColors = listOf(0xFF10B981, 0xFF3B82F6),
            available = false
        ),
        TestType(
            id = "vibecheck",
            title = "Vibe Check",
            subtitle = "Gen Z Quiz — Coming Soon",
            icon = "✨",
            questionCount = 20,
            gradientColors = listOf(0xFFF59E0B, 0xFFEF4444),
            available = false
        ),
    )

    val personalityTypes: Map<String, PersonalityType> = mapOf(
        "INTJ" to PersonalityType(
            code = "INTJ",
            title = "The Architect",
            nickname = "Architect",
            description = "Strategic, imaginative, and deeply independent. You see patterns where others see chaos. You're driven by a vision of how things could be — and the determination to make it real.",
            strengths = listOf("Strategic thinker", "Independent", "Visionary", "Determined", "Analytical"),
            weaknesses = listOf("Can be overly critical", "May struggle with emotional expression", "Can be impatient with inefficiency"),
            careerFits = listOf("Software Architect", "Strategy Consultant", "Research Scientist", "Investment Analyst"),
            gradientStart = 0xFF6366F1,
            gradientEnd = 0xFF8B5CF6,
            emoji = "♟️"
        ),
        "INTP" to PersonalityType(
            code = "INTP",
            title = "The Logician",
            nickname = "Logician",
            description = "Inventive, analytical, endlessly curious. You live to understand how things work. You approach life as a puzzle to be solved — and you genuinely enjoy the process.",
            strengths = listOf("Logical", "Innovative", "Curious", "Objective", "Independent thinker"),
            weaknesses = listOf("May overthink decisions", "Can be distant emotionally", "Dislikes routine"),
            careerFits = listOf("Software Engineer", "Data Scientist", "Researcher", "Systems Analyst"),
            gradientStart = 0xFF8B5CF6,
            gradientEnd = 0xFF6366F1,
            emoji = "🔬"
        ),
        "ENTJ" to PersonalityType(
            code = "ENTJ",
            title = "The Commander",
            nickname = "Commander",
            description = "Bold, decisive, a natural leader. You see inefficiency and fix it. You thrive on challenge and have a relentless drive to achieve your goals.",
            strengths = listOf("Natural leader", "Strategic", "Efficient", "Confident", "Decisive"),
            weaknesses = listOf("Can be intimidating", "May struggle with patience", "Can be blunt"),
            careerFits = listOf("CEO/Founder", "Management Consultant", "Project Director", "Investment Banker"),
            gradientStart = 0xFFEC4899,
            gradientEnd = 0xFFF59E0B,
            emoji = "👑"
        ),
        "ENTP" to PersonalityType(
            code = "ENTP",
            title = "The Debater",
            nickname = "Debater",
            description = "Smart, curious, endlessly inventive. You love a good argument — not to win, but to explore. You see every side of every issue.",
            strengths = listOf("Quick thinker", "Innovative", "Adaptable", "Charismatic", "Enthusiastic"),
            weaknesses = listOf("May lack follow-through", "Can be argumentative", "Easily bored by routine"),
            careerFits = listOf("Startup Founder", "Lawyer", "Product Manager", "Marketing Strategist"),
            gradientStart = 0xFFF59E0B,
            gradientEnd = 0xFFEC4899,
            emoji = "⚡"
        ),
        "INFJ" to PersonalityType(
            code = "INFJ",
            title = "The Advocate",
            nickname = "Advocate",
            description = "Idealistic, principled, quietly driven. You have a deep sense of purpose and an ability to see the big picture. You fight for what you believe in.",
            strengths = listOf("Insightful", "Empathetic", "Idealistic", "Determined", "Creative"),
            weaknesses = listOf("Prone to burnout", "Perfectionist", "Can be overly sensitive"),
            careerFits = listOf("Psychologist", "Writer", "Non-profit Leader", "UX Designer"),
            gradientStart = 0xFF6366F1,
            gradientEnd = 0xFF10B981,
            emoji = "🌙"
        ),
        "INFP" to PersonalityType(
            code = "INFP",
            title = "The Mediator",
            nickname = "Mediator",
            description = "Poetic, kind, altruistic. You believe in the power of individuals to make the world better. You're driven by your values and a deep inner life.",
            strengths = listOf("Empathetic", "Creative", "Values-driven", "Adaptable", "Open-minded"),
            weaknesses = listOf("Can be impractical", "Takes criticism hard", "May isolate from conflict"),
            careerFits = listOf("Writer/Author", "Counselor", "Artist", "Social Worker"),
            gradientStart = 0xFF10B981,
            gradientEnd = 0xFF6366F1,
            emoji = "🍃"
        ),
        "ENFJ" to PersonalityType(
            code = "ENFJ",
            title = "The Protagonist",
            nickname = "Protagonist",
            description = "Charismatic, empathetic, a natural mentor. You see the best in people and help them see it too. You lead with warmth and conviction.",
            strengths = listOf("Charismatic", "Empathetic", "Inspiring", "Reliable", "Natural leader"),
            weaknesses = listOf("Can be overly idealistic", "Sensitive to criticism", "May neglect self-care"),
            careerFits = listOf("Teacher/Mentor", "HR Director", "Life Coach", "Community Organizer"),
            gradientStart = 0xFFEC4899,
            gradientEnd = 0xFF8B5CF6,
            emoji = "🌟"
        ),
        "ENFP" to PersonalityType(
            code = "ENFP",
            title = "The Campaigner",
            nickname = "Campaigner",
            description = "Enthusiastic, creative, free-spirited. You approach life with curiosity and infectious energy. You see connections and possibilities everywhere.",
            strengths = listOf("Enthusiastic", "Creative", "Sociable", "Empathetic", "Spontaneous"),
            weaknesses = listOf("May lack focus", "Can be disorganized", "Sensitive to criticism"),
            careerFits = listOf("Content Creator", "Event Planner", "Creative Director", "Startup Founder"),
            gradientStart = 0xFFF59E0B,
            gradientEnd = 0xFF06B6D4,
            emoji = "🔥"
        ),
        "ISTJ" to PersonalityType(
            code = "ISTJ",
            title = "The Logistician",
            nickname = "Logistician",
            description = "Practical, reliable, detail-oriented. You're the person everyone counts on. You value tradition, order, and getting things done right.",
            strengths = listOf("Reliable", "Detail-oriented", "Responsible", "Organized", "Practical"),
            weaknesses = listOf("May resist change", "Can be rigid", "Uncomfortable with ambiguity"),
            careerFits = listOf("Accountant", "Project Manager", "Engineer", "Auditor"),
            gradientStart = 0xFF3B82F6,
            gradientEnd = 0xFF64748B,
            emoji = "📐"
        ),
        "ISFJ" to PersonalityType(
            code = "ISFJ",
            title = "The Defender",
            nickname = "Defender",
            description = "Warm, dedicated, fiercely loyal. You protect the people you care about with quiet strength. You notice the details others miss.",
            strengths = listOf("Loyal", "Detail-oriented", "Patient", "Supportive", "Hardworking"),
            weaknesses = listOf("May neglect own needs", "Resistant to change", "Takes criticism hard"),
            careerFits = listOf("Nurse", "Teacher", "Admin Manager", "Customer Success"),
            gradientStart = 0xFF10B981,
            gradientEnd = 0xFF3B82F6,
            emoji = "🛡️"
        ),
        "ESTJ" to PersonalityType(
            code = "ESTJ",
            title = "The Executive",
            nickname = "Executive",
            description = "Organized, decisive, natural administrator. You bring order to chaos. You value tradition, structure, and getting results.",
            strengths = listOf("Organized", "Decisive", "Reliable", "Direct", "Strong leader"),
            weaknesses = listOf("Can be inflexible", "May seem bossy", "Uncomfortable with emotion"),
            careerFits = listOf("Operations Manager", "Financial Advisor", "Military Officer", "Judge"),
            gradientStart = 0xFF64748B,
            gradientEnd = 0xFF3B82F6,
            emoji = "💼"
        ),
        "ESFJ" to PersonalityType(
            code = "ESFJ",
            title = "The Consul",
            nickname = "Consul",
            description = "Warm, sociable, community-minded. You're the glue that holds groups together. You genuinely care about people's wellbeing.",
            strengths = listOf("Sociable", "Loyal", "Organized", "Empathetic", "Dutiful"),
            weaknesses = listOf("May care too much about social status", "Resistant to change", "Needs approval"),
            careerFits = listOf("HR Manager", "Event Planner", "Healthcare Worker", "Retail Manager"),
            gradientStart = 0xFFEC4899,
            gradientEnd = 0xFF3B82F6,
            emoji = "💝"
        ),
        "ISTP" to PersonalityType(
            code = "ISTP",
            title = "The Virtuoso",
            nickname = "Virtuoso",
            description = "Practical, observant, hands-on problem solver. You learn by doing. You're calm under pressure and love figuring out how things work.",
            strengths = listOf("Practical", "Calm under pressure", "Adaptable", "Skilled with hands", "Independent"),
            weaknesses = listOf("May be private", "Can be risk-prone", "Dislikes long-term planning"),
            careerFits = listOf("Mechanical Engineer", "Pilot", "Paramedic", "Software Developer"),
            gradientStart = 0xFF64748B,
            gradientEnd = 0xFF8B5CF6,
            emoji = "🔧"
        ),
        "ISFP" to PersonalityType(
            code = "ISFP",
            title = "The Adventurer",
            nickname = "Adventurer",
            description = "Creative, sensitive, fiercely independent. You live in the moment and express yourself through action and art. You see beauty where others don't.",
            strengths = listOf("Creative", "Sensitive", "Adaptable", "Artistic", "Present-minded"),
            weaknesses = listOf("May avoid conflict", "Can be overly sensitive", "Dislikes abstract theory"),
            careerFits = listOf("Designer", "Artist", "Veterinarian", "Chef"),
            gradientStart = 0xFF10B981,
            gradientEnd = 0xFFEC4899,
            emoji = "🎨"
        ),
        "ESTP" to PersonalityType(
            code = "ESTP",
            title = "The Entrepreneur",
            nickname = "Entrepreneur",
            description = "Energetic, perceptive, action-first. You live for the thrill of the moment. You're sharp, adaptable, and always ready to act.",
            strengths = listOf("Energetic", "Adaptable", "Practical", "Social", "Crisis-ready"),
            weaknesses = listOf("May be impulsive", "Can be impatient", "May miss long-term patterns"),
            careerFits = listOf("Sales Rep", "Paramedic", "Entrepreneur", "Sports Coach"),
            gradientStart = 0xFFF59E0B,
            gradientEnd = 0xFF64748B,
            emoji = "🚀"
        ),
        "ESFP" to PersonalityType(
            code = "ESFP",
            title = "The Entertainer",
            nickname = "Entertainer",
            description = "Vibrant, spontaneous, the life of the party. You bring joy wherever you go. You live for experiences and connection.",
            strengths = listOf("Enthusiastic", "Sociable", "Present", "Creative", "Empathetic"),
            weaknesses = listOf("May avoid planning", "Can be impulsive", "Dislikes theory"),
            careerFits = listOf("Event Planner", "Performer", "Tour Guide", "Interior Designer"),
            gradientStart = 0xFFEC4899,
            gradientEnd = 0xFFF59E0B,
            emoji = "🎉"
        ),
    )
}
