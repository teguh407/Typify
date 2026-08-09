# Typify — Product Requirements Document (PRD)

**Version:** 1.1.0
**Date:** August 9, 2026
**Status:** Draft
**Owner:** teguh

---

## 1. Product Overview

### 1.1 Vision

Typify is a Gen Z-native personality test app that makes self-discovery beautiful, shareable, and fair-priced. Think "Spotify Wrapped meets personality test" — dark aesthetic, animated result cards, built for Instagram/TikTok sharing.

### 1.2 Problem

| Pain Point | Evidence (from Play Store research) |
|------------|--------------------------------------|
| Apps look clinical and boring | All competitors use generic UI; none use Gen Z visual language |
| Predatory pricing | DevSect charges $9.99/week; users feel scammed |
| Excessive ads | Study Bunny shows ads every 4 questions; users hate it |
| Poor translation | Multiple apps criticized for "machine translation" English |
| Inconsistent results | "Results change every time I take a test" — top complaint |
| No social sharing | No app has native Instagram Story export |
| 16Personalities abandoned | Official app: 100K downloads, 3.9★, last updated Aug 2024 |
| Single test only | Most apps do ONE test; users want variety |

### 1.3 Target Audience

- **Primary:** Gen Z (18-27), global English-speaking
- **Secondary:** Gen Z Indonesia (bilingual EN/ID)
- **Psychographic:** Interested in self-discovery, mental health awareness, MBTI/TikTok personality content
- **Platform behavior:** Instagram Story sharers, TikTok consumers, identity-label seekers

### 1.4 Competitive Landscape

| Competitor | Downloads | Rating | Gap we exploit |
|------------|-----------|--------|----------------|
| Personality Tests & IQ Test (DevSect) | 1M+ | 4.3★ | Predatory pricing, ugly UI |
| 16 Personalities Test 96Q (Redev) | 500K+ | 4.2★ | Poor translation, no sharing |
| Dimensional Personality Test | 100K+ | 4.5★ | Expensive IAP, paywall surprise |
| 16Personalities (official) | 100K+ | 3.9★ | Abandoned since Aug 2024 |

**Our advantage:** Beautiful UI + fair pricing + social sharing + multi-test + privacy-first

---

## 2. Goals & Success Metrics

### 2.1 Goals

1. Launch v1.0.0 with MBTI test fully functional
2. Achieve 4.6+★ on Play Store (no competitor exceeds 4.5★)
3. Viral loop via Instagram Story sharing
4. 10K+ downloads in first 3 months (organic)

### 2.2 Success Metrics

| Metric | Target | Measurement |
|--------|--------|-------------|
| Play Store rating | ≥4.6★ | Play Store Console |
| Downloads (3 months) | 10K+ | Play Store Console |
| Test completion rate | ≥80% | Analytics (local) |
| Share rate (result → IG Story) | ≥15% | Share intent tracking |
| Retake rate (7-day) | ≥20% | Room DB history |
| Crash-free sessions | ≥99.5% | Crashlytics |
| Day-1 retention | ≥35% | Analytics |

---

## 3. Feature Requirements

### 3.1 P0 — Must Have (v1.0.0)

| ID | Feature | Description | Status |
|----|---------|-------------|--------|
| F-001 | MBTI Test | 60 questions across 4 dimensions (E/I, S/N, T/F, J/P), 5-point Likert scale | ✅ Done |
| F-002 | 16 Personality Types | Full descriptions: nickname, description, strengths, weaknesses, career fits, emoji, gradient | ✅ Done |
| F-003 | Confidence Score | Per-dimension confidence percentage + overall match % | ✅ Done |
| F-004 | Dark OLED Theme | #0F0F23 background, neon gradient cards, Material3 | ✅ Done |
| F-005 | Quiz UX | Animated question transitions, progress bar, back navigation | ✅ Done |
| F-006 | Result Card | Gradient card with type code, emoji, description, match % | ✅ Done |
| F-007 | Result Breakdown | Visual bars for each dimension (E↔I, S↔N, T↔F, J↔P) | ✅ Done |
| F-008 | Strengths/Weaknesses/Careers | Sectioned lists with color coding | ✅ Done |
| F-009 | Share Result | Shareable card (text + image) via system share intent | 🟡 Text only, needs image card |
| F-010 | History | Room DB stores all past results, sorted by date | ✅ Done |
| F-011 | Privacy Screen | Settings: "No data collection" declaration | ✅ Done |
| F-012 | Offline-first | All data local, no network required | ✅ Done |
| F-013 | Instagram Story Export | Generate visual card image → share to IG Story / TikTok / WhatsApp | ❌ Missing |

### 3.2 P1 — Should Have (v1.1.0)

| ID | Feature | Description | Status |
|----|---------|-------------|--------|
| F-014 | Enneagram Test | 36 questions, 9 types with wing system | ❌ Missing |
| F-015 | Big Five Test | 50 questions, OCEAN model with percentile scores | ❌ Missing |
| F-016 | Vibe Check Quiz | 20 fun Gen Z questions ("aura type", "vibe check") | ❌ Missing |
| F-017 | i18n (EN/ID) | All UI strings localized, language switch in settings | ❌ Placeholder only |
| F-018 | Test Comparison | Compare two results side-by-side | ❌ Missing |
| F-019 | Result Consistency | Show test-retest reliability indicator | ❌ Missing |
| F-020 | Onboarding | 3-screen intro flow for first launch | ❌ Missing |

### 3.3 P2 — Nice to Have (v1.2.0+)

| ID | Feature | Description |
|----|---------|-------------|
| F-021 | Friend Comparison | Share link → friend takes test → compare types |
| F-022 | Type Compatibility | "You're INTJ, best match: ENFP" relationship guide |
| F-023 | Daily Insights | "Did you know? INTJs are 2% of population" cards |
| F-024 | Custom Quizzes | User-generated personality quizzes |
| F-025 | Light Theme | Optional light mode toggle |
| F-026 | Monetization | Freemium: free MBTI + $3.99/mo unlimited (Enneagram, Big Five, Vibe Check) |
| F-027 | Notifications | "Time to retake your test!" reminder after 30 days |

---

## 4. Technical Requirements

### 4.1 Stack

| Layer | Technology |
|-------|-----------|
| Language | Kotlin |
| UI | Jetpack Compose + Material3 |
| DB | Room (SQLite, offline-first) |
| Navigation | Navigation Compose |
| Image generation | Compose Canvas → Bitmap → PNG (for share cards) |
| Min SDK | 26 (Android 8.0) |
| Target SDK | 34 |
| Build | GitHub Actions CI |

### 4.2 Architecture

```
MVVM + Repository pattern

UI Layer (Compose Screens)
    ↓
ViewModel (StateFlow)
    ↓
Repository
    ↓
Room DAO + DataStore
```

### 4.3 Performance

- App startup < 2 seconds
- Quiz transition animation < 300ms
- Result calculation < 100ms
- Share image generation < 500ms
- Memory usage < 100MB

### 4.4 Security

- No network calls (offline-first)
- No analytics SDK (privacy-first)
- No hardcoded secrets
- No exported components except launcher
- Room DB not backed up to cloud (encrypted backup disabled)

---

## 5. Screen Specifications

### 5.1 Onboarding (P1 — first launch only)

**3 screens, swipeable:**
1. "Discover your personality" — gradient bg, emoji 🧠
2. "60 questions, 5 minutes" — timer illustration
3. "100% private, no data collected" — lock icon 🔒

Skip button on each. "Get Started" on last.

### 5.2 Home Screen

**Layout:**
- Top bar: "Typify" logo + History icon + Settings icon
- Hero banner: gradient card "Discover yourself" with AutoAwesome icon
- Section title: "Tests"
- Test cards (vertical list):
  - MBTI: 🧠 gradient purple→pink, "16 Personality Types", "60 questions · 5 min"
  - Enneagram: 🌀 gradient purple→cyan, "9 Types", "Coming Soon" chip
  - Big Five: 🌊 gradient green→blue, "OCEAN Model", "Coming Soon" chip
  - Vibe Check: ✨ gradient amber→red, "Gen Z Quiz", "Coming Soon" chip

**Interactions:**
- Tap available test → navigate to Quiz
- Tap "Coming Soon" card → toast "Coming soon!"

### 5.3 Quiz Screen

**Layout:**
- Top bar: back arrow + "3/60" counter
- Progress bar (gradient, animated)
- Question text (headlineLarge, centered)
- 5-point answer scale:
  - 5 circles: Strongly Disagree → Strongly Disagree → Neutral → Agree → Strongly Agree
  - Colors: red → pink → gray → purple → deep purple
  - Selected circle scales up 1.15x with bounce
- Bottom: "Next" glow button (disabled until answer selected)
- Last question: "See Results" button

**Interactions:**
- Select answer → circle bounces + checkmark appears
- Back arrow → previous question (preserves answer)
- "Next" → slide animation to next question
- Progress bar animates on question change

### 5.4 Result Screen

**Layout (scrollable):**
1. **Result Card** (gradient, rounded 32dp):
   - Emoji (64sp)
   - Type code (48sp bold white, e.g. "INTJ")
   - Title (18sp, e.g. "The Architect")
   - Description (15sp, 3-4 lines)
   - Match % chip (e.g. "73% match")

2. **Dimension Breakdown:**
   - Title: "Your Breakdown"
   - 4 bars: E↔I, S↔N, T↔F, J↔P
   - Each bar: gradient fill, percentage label, animated width

3. **Strengths section:**
   - Title: "💪 Strengths"
   - Bullet list (green dots)

4. **Growth Areas section:**
   - Title: "🌱 Growth Areas"
   - Bullet list (pink dots)

5. **Career Fits section:**
   - Title: "💼 Career Fits"
   - Bullet list (purple dots)

6. **Actions:**
   - "Share My Type" — glow button (generates image card → share intent)
   - "Retake Test" — outlined button
   - "Back to Home" — text button

### 5.5 History Screen

**Layout:**
- Top bar: back arrow + "History" title
- Empty state: 📭 "No tests yet" + "Take your first test!"
- List of result cards:
  - Type code (bold, colored)
  - Title (secondary)
  - Date (tertiary)
  - "60 questions · 73% match" (tertiary)

### 5.6 Settings Screen

**Layout:**
- Top bar: back arrow + "Settings" title
- About section:
  - "Typify" + version
  - App description
- Privacy section:
  - "Data Collection: None" (green badge)
  - Privacy description
- Language section (P1):
  - English / Bahasa Indonesia radio buttons
- Footer: "Made with 💜"

---

## 6. Design System

### 6.1 Colors

| Token | Value | Usage |
|-------|-------|-------|
| DarkBg | #0F0F23 | Background |
| DarkSurface | #1A1A2E | Cards |
| DarkSurfaceVariant | #252540 | Input fields |
| NeonPurple | #7C3AED | Primary |
| NeonPink | #EC4899 | Secondary |
| NeonCyan | #06B6D4 | Tertiary |
| NeonGreen | #10B981 | Success/Strengths |
| TextPrimary | #F8F8FF | Primary text |
| TextSecondary | #A0A0B8 | Secondary text |
| TextTertiary | #6B6B80 | Tertiary/hint |

### 6.2 Typography

| Style | Size | Weight | Usage |
|-------|------|--------|-------|
| Display Large | 42sp | Bold | App title |
| Display Medium | 32sp | Bold | Screen titles |
| Headline Large | 24sp | SemiBold | Question text |
| Headline Medium | 20sp | SemiBold | Section titles |
| Title Large | 18sp | SemiBold | Card titles |
| Body Large | 16sp | Normal | Descriptions |
| Body Medium | 14sp | Normal | Secondary text |
| Label Large | 14sp | SemiBold | Buttons |
| Label Medium | 12sp | Medium | Chips, hints |

### 6.3 Gradients (per personality type)

Each type has a unique gradient pair. See QuestionBank.kt for all 16.

### 6.4 Spacing

- Screen padding: 20dp horizontal
- Card padding: 24dp
- Section spacing: 16dp
- Item spacing: 12dp
- Button height: 54dp
- Corner radius: cards 24dp, buttons 16dp, chips 12dp

### 6.5 Animations

| Element | Animation | Duration |
|---------|-----------|----------|
| Question transition | Slide horizontal + fade | 300ms |
| Progress bar | Tween ease-out | 600ms |
| Answer selection | Spring bounce (scale 1.15x) | 200ms |
| Result card pop | Spring (dampingRatio medium) | 400ms |
| Dimension bars | Tween ease-out (width fill) | 800ms |
| Button press | Scale 0.97x | 100ms |

---

## 7. Monetization (v1.2.0+)

### 7.1 Model: Freemium

| Tier | Price | Includes |
|------|-------|----------|
| Free | $0 | MBTI test + history + share (with tasteful banner ads) |
| Premium | $3.99/month | All tests (Enneagram, Big Five, Vibe Check) + no ads + test comparison |

### 7.2 Principles

- NO $9.99/week (competitor's predatory model)
- NO paywall on results (results always free)
- NO artificial delays
- NO data selling
- Ads: banner only, never interrupt quiz flow
- Premium = more tests, not better results

---

## 8. Testing Requirements

### 8.1 Functional Tests

| Test Case | Expected |
|-----------|----------|
| Complete MBTI test (60 Q) | Result displayed with correct type |
| Back navigation during quiz | Previous question shown, answer preserved |
| Retake test | New result calculated, saved to history |
| History view | All past results listed by date |
| Share result | Share intent opens with image card |
| App restart | History preserved (Room DB) |
| Empty state | "No tests yet" shown in History |

### 8.2 Edge Cases

| Case | Expected |
|------|----------|
| Rapid tap through quiz | No crash, all answers registered |
| All neutral answers | Type still calculated (closest letter) |
| Background app during quiz | State preserved on return |
| Kill app during quiz | Quiz restarts from beginning |
| Very long screen | Scroll works, no clipping |

### 8.3 Device Tests

| Device | Screen | Expected |
|--------|--------|----------|
| Small (5.0") | 720x1280 | No clipping, scrollable |
| Medium (6.1") | 1080x2400 | Optimal layout |
| Large (6.7") | 1440x3200 | No stretching |
| Tablet (10") | 2560x1600 | Acceptable (not optimized) |

---

## 9. Release Checklist

### 9.1 v1.0.0 (MVP)

- [x] MBTI test functional (60 Q, 16 types)
- [x] Dark OLED theme
- [x] Gradient result cards
- [x] Dimension breakdown with confidence
- [x] Strengths/weaknesses/career sections
- [x] History (Room DB)
- [x] Settings (privacy declaration)
- [x] GitHub Actions CI
- [ ] Instagram Story export (image card)
- [ ] Device testing (at least 2 screen sizes)
- [ ] i18n strings wired up
- [ ] Onboarding flow

### 9.2 Store Listing

- [ ] App icon (512x512 PNG)
- [ ] Feature graphic (1024x500)
- [ ] Screenshots (min 2, max 8)
- [ ] Description (EN + ID)
- [ ] Privacy policy URL
- [ ] Content rating questionnaire
- [ ] Target audience: 18+

---

## 10. Open Questions

| # | Question | Status |
|---|----------|--------|
| Q-001 | Should we add ads in v1.0.0 or wait until v1.2.0? | TBD |
| Q-002 | Should onboarding be skippable or mandatory? | Lean: skippable |
| Q-003 | Should we support landscape mode? | Lean: portrait only for v1 |
| Q-004 | Should results be shareable as image or just text? | PRD: image (F-013) |
| Q-005 | Should we add type compatibility (romantic/career)? | P2 (F-022) |
