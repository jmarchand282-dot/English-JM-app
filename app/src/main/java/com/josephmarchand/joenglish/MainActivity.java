package com.josephmarchand.joenglish;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.josephmarchand.joenglish.lessons.LessonBank;
import com.josephmarchand.joenglish.models.Lesson;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    private LinearLayout root;
    private TextToSpeech tts;
    private android.content.SharedPreferences prefs;

    private int xp = 0;
    private int streak = 0;
    private int completedLessons = 0;
    private int currentLesson = 1;

    private String firstName = "";
    private String username = "";
    private String level = "A1";
    private String targetLanguage = "English";
    private String englishVariant = "American English";

    private final int BLUE = Color.rgb(79, 70, 229);
    private final int GREEN = Color.rgb(22, 163, 74);
    private final int BG = Color.rgb(247, 248, 252);
    private final int DARK = Color.rgb(30, 30, 40);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("joenglish_data", MODE_PRIVATE);
        loadData();

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                tts.setLanguage(Locale.US);
            }
        });

        if (firstName.isEmpty()) {
            showWelcome();
        } else {
            showHome();
        }
    }

    private void loadData() {
        firstName = prefs.getString("firstName", "");
        username = prefs.getString("username", "");
        level = prefs.getString("level", "A1");
        targetLanguage = prefs.getString("targetLanguage", "English");
        englishVariant = prefs.getString("variant", "American English");

        xp = prefs.getInt("xp", 0);
        streak = prefs.getInt("streak", 0);
        completedLessons = prefs.getInt("completedLessons", 0);
        currentLesson = prefs.getInt("currentLesson", 1);
    }

    private void saveData() {
        prefs.edit()
                .putString("firstName", firstName)
                .putString("username", username)
                .putString("level", level)
                .putString("targetLanguage", targetLanguage)
                .putString("variant", englishVariant)
                .putInt("xp", xp)
                .putInt("streak", streak)
                .putInt("completedLessons", completedLessons)
                .putInt("currentLesson", currentLesson)
                .apply();
    }

    private void baseScreen() {
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(24, 28, 24, 32);

        scroll.addView(content);
        root.addView(scroll);

        setContentView(root);
    }

    private TextView text(String value, float size) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(DARK);
        t.setPadding(0, 8, 0, 8);
        return t;
    }

    private Button button(String label) {
        Button b = new Button(this);
        b.setText(label);
        b.setTextSize(15);
        b.setTextColor(Color.WHITE);
        b.setBackgroundColor(BLUE);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        p.setMargins(0, 8, 0, 8);
        b.setLayoutParams(p);

        return b;
    }

    private EditText input(String hint) {
        EditText e = new EditText(this);
        e.setHint(hint);
        e.setTextSize(16);
        e.setPadding(16, 12, 16, 12);

        LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );

        p.setMargins(0, 8, 0, 8);
        e.setLayoutParams(p);

        return e;
    }

    private void showWelcome() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        TextView logo = text("🎓 JoEnglish", 34);
        logo.setTextColor(BLUE);
        logo.setGravity(Gravity.CENTER);

        content.addView(logo);

        TextView slogan = text(
                "Apprends aujourd'hui,\nun meilleur demain !",
                20
        );
        slogan.setGravity(Gravity.CENTER);

        content.addView(slogan);

        content.addView(text(
                "\nUne application pour apprendre l'anglais " +
                "avec des leçons, des exercices et une progression personnelle.",
                16
        ));

        Button start = button("Créer mon profil");

        start.setOnClickListener(v -> showProfileCreation());

        content.addView(start);
    }

    private void showProfileCreation() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text("Créer ton profil", 28));

        content.addView(text(
                "Quelques informations pour personnaliser ton apprentissage.",
                15
        ));

        EditText first = input("Prénom ou nom d'affichage");
        EditText user = input("@nom_utilisateur");

        content.addView(first);
        content.addView(user);

        content.addView(text("Ton objectif", 20));

        Spinner goal = new Spinner(this);

        String[] goals = {
                "Voyage",
                "Travail",
                "Études",
                "Conversation",
                "Films et séries",
                "Culture",
                "Anglais général"
        };

        goal.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                goals
        ));

        content.addView(goal);

        Button next = button("Continuer");

        next.setOnClickListener(v -> {

            String name = first.getText().toString().trim();

            if (name.isEmpty()) {
                first.setError("Entre ton prénom");
                return;
            }

            firstName = name;
            username = user.getText().toString().trim();

            saveData();

            showLearningSettings();
        });

        content.addView(next);
    }

    private void showLearningSettings() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text("Configurer ton apprentissage", 27));

        content.addView(text("Langue à apprendre", 19));

        Spinner language = new Spinner(this);

        String[] languages = {
                "English",
                "Français",
                "Español",
                "Português",
                "Deutsch"
        };

        language.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                languages
        ));

        content.addView(language);

        content.addView(text("Niveau actuel", 19));

        Spinner levels = new Spinner(this);

        String[] levelChoices = {
                "Débutant complet — A1",
                "Quelques mots — A1",
                "Je peux communiquer — A2",
                "Intermédiaire — B1",
                "Intermédiaire avancé — B2",
                "Avancé — C1",
                "Très avancé — C2",
                "Je ne sais pas — Test"
        };

        levels.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                levelChoices
        ));

        content.addView(levels);

        content.addView(text("Variante de l'anglais", 19));

        Spinner variant = new Spinner(this);

        String[] variants = {
                "American English",
                "British English"
        };

        variant.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                variants
        ));

        content.addView(variant);

        content.addView(text("Objectif quotidien", 19));

        Spinner daily = new Spinner(this);

        String[] dailyGoals = {
                "5 minutes",
                "10 minutes",
                "15 minutes",
                "20 minutes",
                "30 minutes",
                "45 minutes",
                "60 minutes"
        };

        daily.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                dailyGoals
        ));

        content.addView(daily);

        Button continueButton =
                button("Commencer mon parcours");

        continueButton.setOnClickListener(v -> {

            targetLanguage =
                    language.getSelectedItem().toString();

            englishVariant =
                    variant.getSelectedItem().toString();

            String selected =
                    levels.getSelectedItem().toString();

            if (selected.contains("B2")) {
                level = "B2";
            } else if (selected.contains("C1")) {
                level = "C1";
            } else if (selected.contains("C2")) {
                level = "C2";
            } else if (selected.contains("B1")) {
                level = "B1";
            } else if (selected.contains("A2")) {
                level = "A2";
            } else {
                level = "A1";
            }

            saveData();

            if (selected.contains("Test")) {
                showPlacementTest();
            } else {
                showHome();
            }
        });

        content.addView(continueButton);
            }
      private void showPlacementTest() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text("Test de niveau", 28));

        content.addView(text(
                "Réponds aux questions pour déterminer ton niveau.",
                15
        ));

        showPlacementQuestion(content, 0);
    }

    private void showPlacementQuestion(
            LinearLayout content,
            int questionNumber
    ) {

        content.removeAllViews();

        String[] questions = {
                "Choose the correct answer: I ___ a student.",
                "Choose the correct answer: She ___ English every day.",
                "Choose the correct answer: Where ___ you live?",
                "Choose the correct answer: I have ___ apple.",
                "Choose the correct answer: They ___ yesterday."
        };

        String[][] options = {
                {"am", "is", "are", "be"},
                {"study", "studies", "studying", "studied"},
                {"do", "does", "are", "is"},
                {"a", "an", "the", "some"},
                {"go", "goes", "went", "going"}
        };

        int[] correctAnswers = {
                0,
                1,
                0,
                1,
                2
        };

        content.addView(text(
                "Question " + (questionNumber + 1) + " / " + questions.length,
                18
        ));

        content.addView(text(
                questions[questionNumber],
                21
        ));

        RadioGroup group = new RadioGroup(this);

        for (String option : options[questionNumber]) {

            RadioButton radio = new RadioButton(this);
            radio.setText(option);
            radio.setTextSize(17);
            radio.setPadding(8, 12, 8, 12);

            group.addView(radio);
        }

        content.addView(group);

        Button next = button(
                questionNumber == questions.length - 1
                        ? "Terminer le test"
                        : "Question suivante"
        );

        next.setOnClickListener(v -> {

            int selected = group.getCheckedRadioButtonId();

            if (selected == -1) {
                Toast.makeText(
                        this,
                        "Choisis une réponse.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            RadioButton selectedButton =
                    group.findViewById(selected);

            int selectedIndex =
                    group.indexOfChild(selectedButton);

            int score = prefs.getInt(
                    "placementScore",
                    0
            );

            if (selectedIndex == correctAnswers[questionNumber]) {
                score++;
            }

            prefs.edit()
                    .putInt("placementScore", score)
                    .apply();

            if (questionNumber < questions.length - 1) {

                showPlacementQuestion(
                        content,
                        questionNumber + 1
                );

            } else {

                showPlacementResult(score);
            }
        });

        content.addView(next);
    }

    private void showPlacementResult(int score) {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "Résultat du test",
                28
        ));

        String detectedLevel;

        if (score <= 1) {
            detectedLevel = "A1";
        } else if (score == 2) {
            detectedLevel = "A2";
        } else if (score == 3) {
            detectedLevel = "B1";
        } else if (score == 4) {
            detectedLevel = "B2";
        } else {
            detectedLevel = "C1";
        }

        level = detectedLevel;

        content.addView(text(
                "Score : " + score + " / 5",
                20
        ));

        content.addView(text(
                "Niveau proposé : " + detectedLevel,
                22
        ));

        content.addView(text(
                "Tu peux commencer ton parcours avec ce niveau.",
                16
        ));

        Button start = button(
                "Commencer mon parcours"
        );

        start.setOnClickListener(v -> {

            saveData();

            showHome();
        });

        content.addView(start);
    }

    private void showHome() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        TextView logo = text(
                "🎓 JoEnglish",
                30
        );

        logo.setTextColor(BLUE);
        logo.setGravity(Gravity.CENTER);

        content.addView(logo);

        content.addView(text(
                "Bonjour " +
                        (firstName.isEmpty()
                                ? "👋"
                                : firstName + " 👋"),
                26
        ));

        content.addView(text(
                "Ton parcours : " + level,
                18
        ));

        LinearLayout stats =
                new LinearLayout(this);

        stats.setOrientation(
                LinearLayout.HORIZONTAL
        );

        stats.setGravity(Gravity.CENTER);

        TextView xpText = text(
                "⭐ XP\n" + xp,
                17
        );

        TextView streakText = text(
                "🔥 Série\n" + streak + " jour(s)",
                17
        );

        TextView lessonsText = text(
                "📚 Leçons\n" + completedLessons,
                17
        );

        stats.addView(xpText,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));

        stats.addView(streakText,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));

        stats.addView(lessonsText,
                new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1
                ));

        content.addView(stats);

        content.addView(text(
                "🎯 Objectif quotidien",
                20
        ));

        content.addView(text(
                "Continue ton apprentissage aujourd'hui.",
                15
        ));

        Button course = button(
                "📚 Continuer le cours"
        );

        course.setOnClickListener(v ->
                showLesson(currentLesson)
        );

        content.addView(course);

        Button review = button(
                "🔄 Révisions"
        );

        review.setOnClickListener(v ->
                showReview()
        );

        content.addView(review);

        Button expressions = button(
                "💬 Expressions utiles"
        );

        expressions.setOnClickListener(v ->
                showExpressions()
        );

        content.addView(expressions);

        Button statsButton = button(
                "📊 Mes statistiques"
        );

        statsButton.setOnClickListener(v ->
                showStats()
        );

        content.addView(statsButton);

        Button profile = button(
                "👤 Mon profil"
        );

        profile.setOnClickListener(v ->
                showProfile()
        );

        content.addView(profile);

        Button settings = button(
                "⚙️ Paramètres"
        );

        settings.setOnClickListener(v ->
                showSettings()
        );

        content.addView(settings);
    }

    private void showLesson(int id) {

        Lesson lesson =
                LessonBank.getLessonById(id);

        if (lesson == null) {

            Toast.makeText(
                    this,
                    "Cette leçon n'est pas encore disponible.",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "Leçon " + lesson.getId(),
                18
        ));

        content.addView(text(
                lesson.getTitle(),
                28
        ));

        content.addView(text(
                lesson.getObjective(),
                16
        ));

        content.addView(text(
                "📚 Vocabulaire",
                21
        ));

        for (String word : lesson.getVocabulary()) {

            LinearLayout row =
                    new LinearLayout(this);

            row.setOrientation(
                    LinearLayout.HORIZONTAL
            );

            TextView wordText =
                    text(word, 16);

            Button audio =
                    new Button(this);

            audio.setText("🔊");

            audio.setOnClickListener(v ->
                    speakText(word.split("=")[0].trim())
            );

            row.addView(
                    wordText,
                    new LinearLayout.LayoutParams(
                            0,
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            1
                    )
            );

            row.addView(audio);

            content.addView(row);
        }

        content.addView(text(
                "🗣️ Exemples",
                21
        ));

        for (int i = 0;
             i < lesson.getExamples().length;
             i++) {

            String example =
                    lesson.getExamples()[i];

            String translation =
                    lesson.getTranslations()[i];

            TextView exampleText =
                    text(
                            example +
                                    "\n→ " +
                                    translation,
                            17
                    );

            content.addView(exampleText);

            Button listen =
                    button("🔊 Écouter");

            listen.setOnClickListener(v ->
                    speakText(example)
            );

            content.addView(listen);
        }

        Button complete =
                button("✅ Terminer la leçon");

        complete.setOnClickListener(v ->
                showLessonCompleted(lesson)
        );

        content.addView(complete);
    }

    private void showLessonCompleted(Lesson lesson) {

        completedLessons++;

        xp += lesson.getXp();

        currentLesson = lesson.getId() + 1;

        if (currentLesson >
                LessonBank.getLessons().size()) {

            currentLesson =
                    LessonBank.getLessons().size();
        }

        saveData();

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        TextView title =
                text(
                        "🎉 Leçon terminée !",
                        30
                );

        title.setTextColor(GREEN);
        title.setGravity(Gravity.CENTER);

        content.addView(title);

        content.addView(text(
                "+" + lesson.getXp() + " XP",
                24
        ));

        content.addView(text(
                "Excellent travail, " +
                        firstName + " !",
                20
        ));

        content.addView(text(
                "Total XP : " + xp,
                17
        ));

        Button next =
                button("▶ Leçon suivante");

        next.setOnClickListener(v -> {

            int nextId =
                    lesson.getId() + 1;

            Lesson nextLesson =
                    LessonBank.getLessonById(nextId);

            if (nextLesson != null) {
                showLesson(nextId);
            } else {
                showHome();
            }
        });

        content.addView(next);

        Button home =
                button("🏠 Retour à l'accueil");

        home.setOnClickListener(v ->
                showHome()
        );

        content.addView(home);
                  }
      private void showReview() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "🔄 Révisions",
                28
        ));

        content.addView(text(
                "Révise les mots et expressions déjà étudiés.",
                16
        ));

        Lesson lesson =
                LessonBank.getLessonById(
                        Math.max(1, currentLesson - 1)
                );

        if (lesson != null) {

            content.addView(text(
                    "Dernière leçon : " +
                            lesson.getTitle(),
                    20
            ));

            for (String word :
                    lesson.getVocabulary()) {

                TextView item =
                        text(word, 17);

                content.addView(item);
            }

            Button audio =
                    button("🔊 Écouter les exemples");

            audio.setOnClickListener(v -> {

                if (lesson.getExamples().length > 0) {
                    speakText(
                            lesson.getExamples()[0]
                    );
                }
            });

            content.addView(audio);
        }

        Button home =
                button("🏠 Retour à l'accueil");

        home.setOnClickListener(v ->
                showHome()
        );

        content.addView(home);
    }

    private void showExpressions() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "💬 Expressions utiles",
                28
        ));

        String[][] expressions = {
                {
                        "How are you?",
                        "Comment vas-tu ?"
                },
                {
                        "I'm fine, thank you.",
                        "Je vais bien, merci."
                },
                {
                        "Nice to meet you.",
                        "Ravi de te rencontrer."
                },
                {
                        "Can you help me?",
                        "Peux-tu m'aider ?"
                },
                {
                        "I don't understand.",
                        "Je ne comprends pas."
                },
                {
                        "Could you repeat, please?",
                        "Pourriez-vous répéter, s'il vous plaît ?"
                },
                {
                        "What does this mean?",
                        "Qu'est-ce que cela signifie ?"
                },
                {
                        "See you later.",
                        "À plus tard."
                },
                {
                        "Have a nice day!",
                        "Bonne journée !"
                },
                {
                        "I need some help.",
                        "J'ai besoin d'aide."
                }
        };

        for (String[] expression :
                expressions) {

            TextView item =
                    text(
                            expression[0] +
                                    "\n→ " +
                                    expression[1],
                            17
                    );

            content.addView(item);

            Button listen =
                    button("🔊 Écouter");

            listen.setOnClickListener(v ->
                    speakText(expression[0])
            );

            content.addView(listen);
        }

        Button home =
                button("🏠 Retour à l'accueil");

        home.setOnClickListener(v ->
                showHome()
        );

        content.addView(home);
    }

    private void showStats() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "📊 Mes statistiques",
                28
        ));

        content.addView(text(
                "⭐ XP total : " + xp,
                19
        ));

        content.addView(text(
                "🔥 Série actuelle : " +
                        streak + " jour(s)",
                19
        ));

        content.addView(text(
                "📚 Leçons terminées : " +
                        completedLessons,
                19
        ));

        content.addView(text(
                "📖 Niveau actuel : " +
                        level,
                19
        ));

        int nextLevelXp =
                ((xp / 100) + 1) * 100;

        content.addView(text(
                "🎯 Prochain palier : " +
                        nextLevelXp + " XP",
                19
        ));

        Button home =
                button("🏠 Retour à l'accueil");

        home.setOnClickListener(v ->
                showHome()
        );

        content.addView(home);
    }

    private void showProfile() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "👤 Mon profil",
                28
        ));

        content.addView(text(
                "Nom : " +
                        (firstName.isEmpty()
                                ? "Non défini"
                                : firstName),
                18
        ));

        content.addView(text(
                "Nom d'utilisateur : " +
                        (username.isEmpty()
                                ? "Non défini"
                                : username),
                18
        ));

        content.addView(text(
                "Langue : " +
                        targetLanguage,
                18
        ));

        content.addView(text(
                "Variante : " +
                        englishVariant,
                18
        ));

        content.addView(text(
                "Niveau : " +
                        level,
                18
        ));

        content.addView(text(
                "⭐ XP : " + xp,
                18
        ));

        content.addView(text(
                "🔥 Série : " +
                        streak + " jour(s)",
                18
        ));

        Button home =
                button("🏠 Retour à l'accueil");

        home.setOnClickListener(v ->
                showHome()
        );

        content.addView(home);
    }

    private void showSettings() {

        baseScreen();

        LinearLayout content = (LinearLayout)
                ((ScrollView) root.getChildAt(0)).getChildAt(0);

        content.addView(text(
                "⚙️ Paramètres",
                28
        ));

        content.addView(text(
                "Langue : " +
                        targetLanguage,
                18
        ));

        content.addView(text(
                "Variante : " +
                        englishVariant,
                18
        ));

        content.addView(text(
                "Niveau : " +
                        level,
                18
        ));

        content.addView(text(
                "Les données de progression sont " +
                        "enregistrées localement sur ton appareil.",
                15
        ));

        Button testAudio =
                button("🔊 Tester l'audio");

        testAudio.setOnClickListener(v ->
                speakText("Welcome to JoEnglish!")
        );

        content.addView(testAudio);

        Button reset =
                button("♻️ Réinitialiser la progression");

        reset.setOnClickListener(v -> {

            new AlertDialog.Builder(this)
                    .setTitle("Réinitialiser ?")
                    .setMessage(
                            "Cette action supprimera ta progression, " +
                            "ton XP et tes leçons terminées."
                    )
                    .setNegativeButton(
                            "Annuler",
                            null
                    )
                    .setPositiveButton(
                            "Réinitialiser",
                            (dialog, which) -> {

                                xp = 0;
                                streak = 0;
                                completedLessons = 0;
                                currentLesson = 1;

                                saveData();

                                showHome();
                            }
                    )
                    .show();
        });

        content.addView(reset);

        Button home =
                button("🏠 Retour à l'accueil");

        home.setOnClickListener(v ->
                showHome()
        );

        content.addView(home);

        content.addView(text(
                "\nJoEnglish\n" +
                        "Apprends aujourd'hui, un meilleur demain !\n\n" +
                        "Créé par Joseph Marchand",
                14
        ));
    }

    private void speakText(String value) {

        if (tts == null) {
            return;
        }

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.LOLLIPOP) {

            tts.speak(
                    value,
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    "joenglish_" +
                            System.currentTimeMillis()
            );

        } else {

            tts.speak(
                    value,
                    TextToSpeech.QUEUE_FLUSH,
                    null
            );
        }
    }

    @Override
    protected void onDestroy() {

        if (tts != null) {

            tts.stop();
            tts.shutdown();
            tts = null;
        }

        super.onDestroy();
    }
                  }
