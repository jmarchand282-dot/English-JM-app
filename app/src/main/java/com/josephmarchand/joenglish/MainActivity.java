package com.josephmarchand.joenglish;

import android.app.*;
import android.content.*;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.*;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.josephmarchand.joenglish.lessons.LessonBank;
import com.josephmarchand.joenglish.models.Lesson;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private LinearLayout root, content;
    private TextToSpeech tts;
    private SharedPreferences prefs;
    private int xp, streak, completedLessons, currentLesson, dailyXp;
    private String firstName, username, level, targetLanguage, variant, goal, dailyGoal;
    private Uri profilePhoto;
    private final int BLUE=Color.rgb(37,99,235), INDIGO=Color.rgb(79,70,229);
    private final int BG=Color.rgb(246,248,252), DARK=Color.rgb(24,35,58);
    private final int MUTED=Color.rgb(100,116,139), WHITE=Color.WHITE;

    private int dp(float v){
        return (int)(v*getResources().getDisplayMetrics().density+0.5f);
    }

    @Override
    protected void onCreate(Bundle b){
        super.onCreate(b);
        prefs=getSharedPreferences("joenglish",MODE_PRIVATE);
        load();

        tts=new TextToSpeech(this,s->{
            if(s==TextToSpeech.SUCCESS)
                tts.setLanguage(Locale.US);
        });

        if(firstName.isEmpty())
            welcome();
        else
            home();
    }

    private void load(){
        firstName=prefs.getString("firstName","");
        username=prefs.getString("username","");
        level=prefs.getString("level","A1");
        targetLanguage=prefs.getString("targetLanguage","English");
        variant=prefs.getString("variant","American English");
        goal=prefs.getString("goal","Anglais général");
        dailyGoal=prefs.getString("dailyGoal","20 minutes");

        String photo=prefs.getString("photo","");
        profilePhoto=photo.isEmpty()?null:Uri.parse(photo);

        xp=prefs.getInt("xp",0);
        streak=prefs.getInt("streak",0);
        completedLessons=prefs.getInt("completed",0);
        currentLesson=prefs.getInt("currentLesson",1);
        dailyXp=prefs.getInt("dailyXp",0);
    }

    private void save(){
        prefs.edit()
                .putString("firstName",firstName)
                .putString("username",username)
                .putString("level",level)
                .putString("targetLanguage",targetLanguage)
                            .putString("variant",variant)
                .putString("goal",goal)
                .putString("dailyGoal",dailyGoal)
                .putString("photo",profilePhoto==null?"":profilePhoto.toString())
                .putInt("xp",xp)
                .putInt("streak",streak)
                .putInt("completed",completedLessons)
                .putInt("currentLesson",currentLesson)
                .putInt("dailyXp",dailyXp)
                .apply();
    }

    private void screen(){
        root=new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);

        ScrollView sv=new ScrollView(this);

        content=new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(dp(20),dp(16),dp(20),dp(28));

        sv.addView(content);

        root.addView(
                sv,
                new LinearLayout.LayoutParams(-1,0,1)
        );

        setContentView(root);
    }

    private TextView tv(String s,float z){
        TextView t=new TextView(this);
        t.setText(s);
        t.setTextSize(z);
        t.setTextColor(DARK);
        t.setPadding(0,dp(5),0,dp(5));
        return t;
    }

    private TextView title(String s){
        TextView t=tv(s,27);
        t.setTypeface(null,1);
        t.setPadding(0,dp(10),0,dp(10));
        return t;
    }

    private GradientDrawable bg(int color,float r){
        GradientDrawable g=new GradientDrawable();
        g.setColor(color);
        g.setCornerRadius(dp(r));
        return g;
    }

    private Button btn(String s){
        Button b=new Button(this);
        b.setText(s);
        b.setTextSize(15);
        b.setTextColor(WHITE);
        b.setAllCaps(false);
        b.setBackground(bg(INDIGO,14));

        LinearLayout.LayoutParams p=
                new LinearLayout.LayoutParams(-1,dp(52));

        p.setMargins(0,dp(6),0,dp(6));
        b.setLayoutParams(p);

        return b;
    }

    private Button lightBtn(String s){
        Button b=btn(s);
        b.setTextColor(INDIGO);
        b.setBackground(bg(Color.rgb(232,236,255),14));
        return b;
    }

    private EditText input(String hint){
        EditText e=new EditText(this);
        e.setHint(hint);
        e.setTextSize(16);
        e.setPadding(dp(14),dp(8),dp(14),dp(8));
        e.setBackground(bg(WHITE,12));

        LinearLayout.LayoutParams p=
                new LinearLayout.LayoutParams(-1,dp(55));

        p.setMargins(0,dp(6),0,dp(6));
        e.setLayoutParams(p);

        return e;
    }

    private LinearLayout card(){
        LinearLayout l=new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.setPadding(dp(16),dp(12),dp(16),dp(12));
        l.setBackground(bg(WHITE,18));

        LinearLayout.LayoutParams p=
                new LinearLayout.LayoutParams(-1,-2);

        p.setMargins(0,dp(6),0,dp(10));
        l.setLayoutParams(p);

        return l;
    }
        private void welcome(){
        screen();

        TextView logo=title("🎓  JoEnglish");
        logo.setTextColor(BLUE);
        logo.setGravity(17);
        content.addView(logo);

        TextView s=tv(
                "Apprends aujourd’hui,\nun meilleur demain !",
                21
        );
        s.setGravity(17);
        content.addView(s);

        content.addView(tv(
                "\nUne vraie application d’apprentissage : " +
                "parcours A1 à C2, leçons, révisions, expressions, " +
                "statistiques, profil, audio et défis.",
                16
        ));

        Button b=btn("Commencer");
        b.setOnClickListener(v->profileSetup());
        content.addView(b);
    }

    private void profileSetup(){
        screen();

        content.addView(title("Créer ton profil"));
        content.addView(tv(
                "Personnalise ton espace JoEnglish.",
                15
        ));

        EditText n=input("Prénom / nom d’affichage");
        EditText u=input("@nom_utilisateur");

        content.addView(n);
        content.addView(u);

        content.addView(tv(
                "Pourquoi apprends-tu ?",
                18
        ));

        Spinner g=spin(new String[]{
                "Anglais général",
                "Voyage",
                "Travail",
                "Études",
                "Conversation",
                "Films et séries"
        });

        content.addView(g);

        Button next=btn("Continuer");

        next.setOnClickListener(v->{
            if(n.getText().toString().trim().isEmpty()){
                n.setError("Entre ton nom");
                return;
            }

            firstName=n.getText().toString().trim();
            username=u.getText().toString().trim();
            goal=g.getSelectedItem().toString();

            save();
            languageSetup();
        });

        content.addView(next);
    }

    private Spinner spin(String[] a){
        Spinner s=new Spinner(this);

        s.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_dropdown_item,
                        a
                )
        );

        return s;
    }

    private void languageSetup(){
        screen();

        content.addView(title(
                "Configurer ton apprentissage"
        ));

        content.addView(tv(
                "Langue à apprendre",
                18
        ));

        Spinner lang=spin(new String[]{
                "English",
                "Français",
                "Español",
                "Português",
                "Deutsch"
        });

        content.addView(lang);

        content.addView(tv(
                "Ton niveau",
                18
        ));

        Spinner lev=spin(new String[]{
                "A1 — Débutant",
                "A2 — Élémentaire",
                "B1 — Intermédiaire",
                "B2 — Avancé",
                "C1 — Très avancé",
                "C2 — Maîtrise",
                "Test de niveau"
        });

        content.addView(lev);

        content.addView(tv(
                "Variante de l’anglais",
                18
        ));

        Spinner va=spin(new String[]{
                "American English",
                "British English"
        });

        content.addView(va);

        content.addView(tv(
                "Objectif quotidien",
                18
        ));

        Spinner dg=spin(new String[]{
                "5 minutes",
                "10 minutes",
                "15 minutes",
                "20 minutes",
                "30 minutes",
                "45 minutes",
                "60 minutes"
        });

        content.addView(dg);        Button start=btn("Créer mon parcours");

        start.setOnClickListener(v->{
            targetLanguage=lang.getSelectedItem().toString();
            variant=va.getSelectedItem().toString();
            dailyGoal=dg.getSelectedItem().toString();

            String x=lev.getSelectedItem().toString();

            level=x.substring(0,2);

            save();

            if(x.startsWith("Test"))
                placement();
            else
                home();
        });

        content.addView(start);
    }

    private void placement(){
        screen();

        content.addView(title("Test de niveau"));

        content.addView(tv(
                "Quelques questions pour proposer ton niveau.",
                15
        ));

        final String[] q={
                "I ___ a student.",
                "She ___ English every day.",
                "Where ___ you live?",
                "I have ___ apple.",
                "They ___ yesterday.",
                "He has ___ car.",
                "We are ___ dinner now.",
                "I have lived here ___ 2020."
        };

        final String[][] o={
                {"am","is","are","be"},
                {"study","studies","studying","studied"},
                {"do","does","are","is"},
                {"a","an","the","some"},
                {"go","goes","went","going"},
                {"a","an","the","some"},
                {"cook","cooked","cooking","cooks"},
                {"for","since","at","on"}
        };

        final int[] c={
                0,1,0,1,2,0,2,1
        };

        placementQ(q,o,c,0,0);
    }

    private void placementQ(
            String[] q,
            String[][] o,
            int[] c,
            int i,
            int score
    ){
        content.removeAllViews();

        content.addView(title("Test de niveau"));

        content.addView(tv(
                "Question "+(i+1)+" / "+q.length,
                15
        ));

        content.addView(tv(
                q[i],
                21
        ));

        RadioGroup g=new RadioGroup(this);

        for(String x:o[i]){
            RadioButton r=new RadioButton(this);
            r.setText(x);
            r.setTextSize(17);
            g.addView(r);
        }

        content.addView(g);

        Button b=btn(
                i==q.length-1
                        ? "Terminer"
                        : "Suivant"
        );

        b.setOnClickListener(v->{
            int id=g.getCheckedRadioButtonId();

            if(id<0){
                Toast.makeText(
                        this,
                        "Choisis une réponse.",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            int ix=g.indexOfChild(
                    g.findViewById(id)
            );

            int ns=score+
                    (ix==c[i]?1:0);

            if(i<q.length-1){
                placementQ(
                        q,o,c,i+1,ns
                );
            }else{
                level=
                        ns<=2?"A1":
                        ns<=4?"A2":
                        ns<=5?"B1":
                        ns<=6?"B2":
                        "C1";

                save();
                home();
            }
        });

        content.addView(b);
    }

    private TextView header(){
        TextView h=tv(
                "🎓  JoEnglish                         ☰",
                27
        );

        h.setTextColor(WHITE);
        h.setTypeface(null,1);
        h.setPadding(
                dp(10),
                dp(16),
                dp(10),
                dp(16)
        );
        h.setBackground(bg(BLUE,18));

        return h;
    }

    private void home(){
        screen();

        content.addView(header());

        TextView hi=title(
                "Bonjour "+
                (firstName.isEmpty()
                        ?"👋"
                        :firstName+" 👋")
        );

        content.addView(hi);

        LinearLayout stats=
                new LinearLayout(this);

        stats.setOrientation(
                LinearLayout.HORIZONTAL
        );

        String[] a={
                "⭐ XP\n"+xp,
                "🔥 Série\n"+streak+" jours",
                "🎯 Aujourd’hui\n"+dailyXp+"/50",
                "🎁 Récompense\n"+
                        Math.max(
                                0,
                                2-(completedLessons%3)
                        )+" leçons"
        };

        for(String s:a){
            TextView t=tv(s,14);
            t.setGravity(17);
            t.setBackground(bg(WHITE,14));

            stats.addView(
                    t,
                    new LinearLayout.LayoutParams(
                            0,
                            dp(78),
                            1
                    )
            );
        }

        content.addView(stats);

        LinearLayout banner=card();

        TextView bt=tv(
                "De petites leçons pour de grands rêves !",
                23
        );

        bt.setTextColor(WHITE);
        bt.setTypeface(null,1);
        bt.setBackground(bg(BLUE,16));

        banner.addView(bt);

        banner.addView(tv(
                "Apprends. Progresse. Réalise tes objectifs.",
                15
        ));

        content.addView(banner);

        content.addView(
                section("Leçon recommandée")
        );

        Lesson l=
                LessonBank.getLessonById(
                        currentLesson
                );

        if(l==null)
            l=LessonBank.getLessonById(1);

        LinearLayout lc=card();

        lc.addView(tv(
                level+"   Leçon "+l.getId(),
                14
        ));

        TextView lt=tv(
                l.getTitle(),
                20
        );

        lt.setTypeface(null,1);

        lc.addView(lt);

        final Lesson currentLesson = l;

lc.addView(tv(
        currentLesson.getDescription(),
        14
));

Button go=btn("Continuer  ›");

go.setOnClickListener(
        v->lesson(currentLesson.getId())
);

lc.addView(go);
content.addView(lc);

        content.addView(
                section("Ton parcours : A1 → C2")
        );

        TextView path=tv(
                "🔵 A1   ─   ⚪ A2   ─   ⚪ B1   ─   ⚪ B2   ─   ⚪ C1   ─   ⚪ C2",
                15
        );

        content.addView(path);
                addMenu(
                "📚 Parcours",
                "Toutes les leçons",
                v->course()
        );

        addMenu(
                "💬 Expressions",
                "1 000+ expressions",
                v->expressions()
        );

        addMenu(
                "🔄 Révision",
                "Tes points faibles",
                v->review()
        );

        addMenu(
                "🏆 Quiz",
                "Teste tes connaissances",
                v->quiz()
        );

        addMenu(
                "👥 Conversation",
                "Pratique avec JoBot",
                v->conversation()
        );

        addMenu(
                "Aa Vocabulaire",
                "Des milliers de mots",
                v->vocabulary()
        );

        addMenu(
                "📖 Grammaire",
                "Règles et exemples",
                v->grammar()
        );

        addMenu(
                "🎯 Défis",
                "Missions quotidiennes",
                v->challenges()
        );

        addMenu(
                "🎁 Récompenses",
                "Gagne des trophées",
                v->rewards()
        );

        addMenu(
                "📊 Statistiques",
                "Suis tes progrès",
                v->stats()
        );

        addMenu(
                "🗓 Planning",
                "Organise tes cours",
                v->planning()
        );

        addMenu(
                "⚙️ Paramètres",
                "Son, notifications, profil",
                v->settings()
        );

        LinearLayout bot=card();

        bot.addView(tv(
                "🤖  Un petit pas chaque jour fait une grande différence !",
                18
        ));

        bot.addView(tv(
                "“Practice makes progress!” — JoBot",
                14
        ));

        content.addView(bot);
    }

    private TextView section(String s){
        TextView t=tv(s,21);
        t.setTypeface(null,1);
        t.setTextColor(INDIGO);
        return t;
    }

    private void addMenu(
            String a,
            String b,
            View.OnClickListener c
    ){
        LinearLayout x=card();

        TextView t=tv(
                a+"   ›",
                18
        );

        t.setTypeface(null,1);

        x.addView(t);

        x.addView(tv(
                b,
                13
        ));

        x.setOnClickListener(c);

        content.addView(x);
    }

    private void lesson(int id){
        Lesson l=
                LessonBank.getLessonById(id);

        if(l==null){
            Toast.makeText(
                    this,
                    "Leçon indisponible.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        screen();

        content.addView(
                title("Leçon "+l.getId())
        );

        content.addView(
                tv(l.getTitle(),27)
        );

        content.addView(
                tv(l.getDescription(),16)
        );

        content.addView(
                section("📚 Vocabulaire")
        );

        for(String w:l.getVocabulary()){
            LinearLayout row=card();
            row.setOrientation(
                    LinearLayout.HORIZONTAL
            );

            TextView t=tv(w,17);

            row.addView(
                    t,
                    new LinearLayout.LayoutParams(
                            0,
                            -2,
                            1
                    )
            );

            Button a=lightBtn("🔊");

            a.setOnClickListener(
                    v->speak(
                            w.split("=")[0].trim()
                    )
            );

            row.addView(
                    a,
                    new LinearLayout.LayoutParams(
                            dp(70),
                            dp(52)
                    )
            );

            content.addView(row);
        }

        content.addView(
                section("🗣 Exemples")
        );

        for(int i=0;
            i<l.getExamples().size();
            i++){

            TextView e=tv(
                    l.getExamples().get(i)+
                    "\n→ "+
                    l.getTranslations().get(i),
                    17
            );

            content.addView(e);

            Button a=btn("🔊 ÉCOUTER");

            String z=l.getExamples().get(i);

            a.setOnClickListener(
                    v->speak(z)
            );

            content.addView(a);
        }

        Button done=
                btn("✅ TERMINER LA LEÇON");

        done.setOnClickListener(
                v->complete(l)
        );

        content.addView(done);

        Button back=lightBtn("← Retour");

        back.setOnClickListener(
                v->home()
        );

        content.addView(back);
    }
        private void complete(Lesson l){
        completedLessons++;
        xp+=l.getXp();
        dailyXp+=l.getXp();

        currentLesson=
                Math.min(
                        l.getId()+1,
                        Math.max(
                                1,
                                LessonBank.getLessons().size()
                        )
                );

        streak=Math.max(1,streak);

        save();

        screen();

        TextView t=
                title("🎉 Leçon terminée !");

        t.setTextColor(
                Color.rgb(22,163,74)
        );

        content.addView(t);

        content.addView(
                tv(
                        "+"+l.getXp()+" XP",
                        24
                )
        );

        content.addView(
                tv(
                        "Excellent travail, "+
                        firstName+" !",
                        19
                )
        );

        content.addView(
                tv(
                        "Total XP : "+xp,
                        17
                )
        );

        Button n=btn(
                "▶ Leçon suivante"
        );

        n.setOnClickListener(
                v->lesson(
                        Math.min(
                                l.getId()+1,
                                LessonBank.getLessons().size()
                        )
                )
        );

        content.addView(n);

        Button h=lightBtn(
                "🏠 Accueil"
        );

        h.setOnClickListener(
                v->home()
        );

        content.addView(h);
    }

    private void course(){
        screen();

        content.addView(
                title("📚 Parcours")
        );

        content.addView(
                tv(
                        "A1 → C2 · progression sauvegardée sur cet appareil.",
                        15
                )
        );

        String[] ls={
                "A1 — Débutant",
                "A2 — Élémentaire",
                "B1 — Intermédiaire",
                "B2 — Avancé",
                "C1 — Très avancé",
                "C2 — Maîtrise"
        };

        for(String x:ls){
            LinearLayout c=card();

            c.addView(
                    tv(x,19)
            );

            c.addView(
                    tv(
                            x.startsWith(level)
                                    ?"● Niveau actuel"
                                    :"○ Parcours disponible",
                            14
                    )
            );

            content.addView(c);
        }

        for(Lesson l:
                LessonBank.getLessons()){

            Button b=lightBtn(
                    "Leçon "+
                    l.getId()+
                    " · "+
                    l.getTitle()
            );

            b.setOnClickListener(
                    v->lesson(l.getId())
            );

            content.addView(b);
        }

        backHome();
    }

    private void expressions(){
        screen();

        content.addView(
                title("💬 Expressions utiles")
        );

        String[][] e={
                {"How are you?","Comment vas-tu ?"},
                {"Nice to meet you.","Ravi de te rencontrer."},
                {"Can you help me?","Peux-tu m’aider ?"},
                {"I don’t understand.","Je ne comprends pas."},
                {"Could you repeat, please?","Pouvez-vous répéter ?"},
                {"What does this mean?","Qu’est-ce que cela signifie ?"},
                {"See you later.","À plus tard."},
                {"Have a nice day!","Bonne journée !"},
                {"I need some help.","J’ai besoin d’aide."},
                {"Where is the bathroom?","Où sont les toilettes ?"}
        };

        for(String[] x:e){
            LinearLayout c=card();

            c.addView(
                    tv(x[0],18)
            );

            c.addView(
                    tv(
                            "→ "+x[1],
                            15
                    )
            );

            Button a=lightBtn(
                    "🔊 Écouter"
            );

            a.setOnClickListener(
                    v->speak(x[0])
            );

            c.addView(a);

            content.addView(c);
        }

        backHome();
    }

    private void review(){
        screen();

        content.addView(
                title("🔄 Révisions")
        );

        content.addView(
                tv(
                        "Révise le vocabulaire de ta dernière leçon.",
                        15
                )
        );

        Lesson l=
                LessonBank.getLessonById(
                        Math.max(
                                1,
                                currentLesson-1
                        )
                );

        if(l!=null){
            content.addView(
                    tv(l.getTitle(),20)
            );

            for(String x:l.getVocabulary())
                content.addView(
                        tv("• "+x,17)
                );

            Button a=btn(
                    "🔊 Écouter un exemple"
            );

            a.setOnClickListener(
                    v->speak(
                            l.getExamples().get(0)
                    )
            );

            content.addView(a);
        }

        backHome();
    }
       private void quiz(){
        screen();

        content.addView(
                title("🏆 Quiz")
        );

        content.addView(
                tv(
                        "Choisis la bonne réponse.",
                        16
                )
        );

        String[][] q={
                {
                        "I ___ a student.",
                        "am","is","are","be"
                },
                {
                        "She ___ English.",
                        "study","studies","studying","studied"
                },
                {
                        "They ___ yesterday.",
                        "go","goes","went","going"
                }
        };

        quizQ(q,0,0);
    }

    private void quizQ(
            String[][] q,
            int i,
            int score
    ){
        content.removeAllViews();

        content.addView(
                title("🏆 Quiz")
        );

        content.addView(
                tv(
                        "Question "+(i+1)+
                        " / "+q.length,
                        15
                )
        );

        content.addView(
                tv(q[i][0],21)
        );

        RadioGroup g=
                new RadioGroup(this);

        for(int k=1;k<=4;k++){
            RadioButton r=
                    new RadioButton(this);

            r.setText(q[i][k]);
            r.setTextSize(17);

            g.addView(r);
        }

        content.addView(g);

        Button b=btn(
                i==q.length-1
                        ?"Voir le résultat"
                        :"Suivant"
        );

        b.setOnClickListener(v->{
            int id=
                    g.getCheckedRadioButtonId();

            if(id<0)
                return;

            int ix=
                    g.indexOfChild(
                            g.findViewById(id)
                    );

            int ns=
                    score+
                    (
                        (i==0&&ix==0)||
                        (i==1&&ix==1)||
                        (i==2&&ix==2)
                                ?1
                                :0
                    );

            if(i<q.length-1){

                quizQ(
                        q,
                        i+1,
                        ns
                );

            }else{

                xp+=ns*5;
                save();

                content.removeAllViews();

                content.addView(
                        title("Résultat")
                );

                content.addView(
                        tv(
                                "Score : "+
                                ns+
                                " / "+
                                q.length,
                                24
                        )
                );

                content.addView(
                        tv(
                                "+"+(ns*5)+" XP",
                                20
                        )
                );

                backHome();
            }
        });

        content.addView(b);
    }

    private void conversation(){
        screen();

        content.addView(
                title(
                        "👥 Conversation avec JoBot"
                )
        );

        content.addView(
                tv(
                        "Version hors ligne : choisis une situation et pratique.",
                        16
                )
        );

        String[] a={
                "Se présenter",
                "Au restaurant",
                "À l’aéroport",
                "Au travail",
                "Faire des achats",
                "Petite conversation"
        };

        for(String x:a){

            Button b=lightBtn(
                    "💬 "+x
            );

            b.setOnClickListener(
                    v->conversationRoom(x)
            );

            content.addView(b);
        }

        backHome();
    }

    private void conversationRoom(
            String topic
    ){
        screen();

        content.addView(
                title(
                        "JoBot · "+topic
                )
        );

        content.addView(
                tv(
                        "JoBot : Hello! How are you today?",
                        18
                )
        );

        content.addView(
                tv(
                        "Toi : réponds en anglais.",
                        15
                )
        );

        EditText e=input(
                "Écris ta réponse en anglais…"
        );

        content.addView(e);

        Button b=btn("Envoyer");

        b.setOnClickListener(v->{

            String r=
                    e.getText()
                     .toString()
                     .trim();

            if(r.isEmpty())
                return;

            content.addView(
                    tv(
                            "Toi : "+r,
                            17
                    )
            );

            content.addView(
                    tv(
                            "JoBot : Great! Keep practicing. Try another sentence.",
                            17
                    )
            );

            e.setText("");
        });

        content.addView(b);

        backHome();
    }

    private void vocabulary(){
        screen();

        content.addView(
                title("Aa Vocabulaire")
        );

        content.addView(
                tv(
                        "Mots étudiés et vocabulaire essentiel.",
                        16
                )
        );

        String[] v={
                "hello = bonjour",
                "family = famille",
                "friend = ami",
                "house = maison",
                "school = école",
                "work = travail",
                "food = nourriture",
                "water = eau",
                "book = livre",
                "learn = apprendre",
                "speak = parler",
                "understand = comprendre"
        };

        for(String x:v){

            LinearLayout c=card();

            c.addView(
                    tv(x,18)
            );

            Button a=lightBtn("🔊");

            a.setOnClickListener(
                    z->speak(
                            x.split("=")[0].trim()
                    )
            );

            c.addView(a);

            content.addView(c);
        }

        backHome();
            }
        private void grammar(){
        screen();

        content.addView(
                title("📖 Grammaire")
        );

        String[][] g={
                {
                        "BE",
                        "I am · You are · He is",
                        "Pour parler de l’identité ou de l’état."
                },
                {
                        "Present simple",
                        "I work · She works",
                        "Habitudes et faits généraux."
                },
                {
                        "Questions",
                        "Do you...? · Does she...?",
                        "Questions au présent simple."
                },
                {
                        "Past simple",
                        "I worked · I went",
                        "Actions terminées dans le passé."
                },
                {
                        "Present continuous",
                        "I am learning",
                        "Action en cours."
                }
        };

        for(String[] x:g){

            LinearLayout c=card();

            c.addView(
                    tv(x[0],19)
            );

            c.addView(
                    tv(x[1],17)
            );

            c.addView(
                    tv(x[2],14)
            );

            content.addView(c);
        }

        backHome();
    }

    private void challenges(){
        screen();

        content.addView(
                title("🎯 Défis")
        );

        String[] d={
                "Faire une leçon aujourd’hui",
                "Gagner 50 XP",
                "Réviser 10 mots",
                "Écouter 5 phrases",
                "Répondre à un quiz"
        };

        for(String x:d){

            LinearLayout c=card();

            c.addView(
                    tv(
                            "🎯 "+x,
                            17
                    )
            );

            c.addView(
                    tv(
                            "Progression : "+
                            Math.min(
                                    1,
                                    completedLessons
                            )+
                            " / 1",
                            14
                    )
            );

            content.addView(c);
        }

        backHome();
    }

    private void rewards(){
        screen();

        content.addView(
                title("🎁 Récompenses")
        );

        content.addView(
                tv(
                        "Continue à apprendre pour débloquer des trophées.",
                        15
                )
        );

        String[][] r={
                {"🥉 Premier pas","Terminer 1 leçon"},
                {"🥈 Régulier","Terminer 5 leçons"},
                {"🥇 Série","Étudier plusieurs jours"},
                {"🏆 Maître A1","Finir le parcours A1"},
                {"💎 Expert","Atteindre un niveau avancé"}
        };

        for(String[] x:r){

            LinearLayout c=card();

            c.addView(
                    tv(x[0],19)
            );

            c.addView(
                    tv(x[1],14)
            );

            content.addView(c);
        }

        backHome();
    }

    private void stats(){
        screen();

        content.addView(
                title("📊 Statistiques")
        );

        String[][] s={
                {"⭐ XP total",String.valueOf(xp)},
                {"🔥 Série",streak+" jours"},
                {"📚 Leçons terminées",
                        String.valueOf(completedLessons)},
                {"🎯 XP aujourd’hui",
                        String.valueOf(dailyXp)},
                {"📖 Niveau",level},
                {"🌍 Langue",targetLanguage}
        };

        for(String[] x:s){

            LinearLayout c=card();

            c.addView(
                    tv(x[0],17)
            );

            TextView z=
                    tv(x[1],24);

            z.setTextColor(BLUE);

            c.addView(z);

            content.addView(c);
        }

        backHome();
    }

    private void planning(){
        screen();

        content.addView(
                title("🗓 Planning")
        );

        content.addView(
                tv(
                        "Organise tes séances d’apprentissage.",
                        16
                )
        );

        String[] days={
                "Lundi",
                "Mardi",
                "Mercredi",
                "Jeudi",
                "Vendredi",
                "Samedi",
                "Dimanche"
        };

        for(String d:days){

            LinearLayout c=card();

            c.addView(
                    tv(
                            "📅 "+d,
                            18
                    )
            );

            c.addView(
                    tv(
                            "• "+dailyGoal+
                            " · anglais",
                            14
                    )
            );

            content.addView(c);
        }

        backHome();
    }
        private void settings(){
        screen();

        content.addView(
                title("⚙️ Paramètres")
        );

        content.addView(
                tv(
                        "Compte et apprentissage",
                        18
                )
        );

        LinearLayout p=card();

        p.addView(
                tv("👤 Profil",18)
        );

        p.addView(
                tv(
                        firstName+
                        " · "+
                        (
                            username.isEmpty()
                            ?"@utilisateur"
                            :username
                        ),
                        14
                )
        );

        Button edit=
                lightBtn(
                        "Modifier le profil"
                );

        edit.setOnClickListener(
                v->editProfile()
        );

        p.addView(edit);
        content.addView(p);

        LinearLayout l=card();

        l.addView(
                tv("🌍 Langue",18)
        );

        l.addView(
                tv(
                        targetLanguage+
                        " · "+
                        variant,
                        14
                )
        );

        l.addView(
                tv(
                        "Niveau : "+level,
                        14
                )
        );

        l.addView(
                tv(
                        "Objectif : "+goal,
                        14
                )
        );

        Button change=
                lightBtn(
                        "Modifier les préférences"
                );

        change.setOnClickListener(
                v->languageSetup()
        );

        l.addView(change);
        content.addView(l);

        LinearLayout audio=card();

        audio.addView(
                tv("🔊 Audio",18)
        );

        Button test=
                lightBtn(
                        "Tester la prononciation"
                );

        test.setOnClickListener(
                v->speak(
                        "Welcome to JoEnglish. Let's learn English!"
                )
        );

        audio.addView(test);
        content.addView(audio);

        LinearLayout notif=card();

        notif.addView(
                tv(
                        "🔔 Notifications",
                        18
                )
        );

        notif.addView(
                tv(
                        "Les rappels peuvent être activés dans les réglages Android.",
                        14
                )
        );

        Button open=
                lightBtn(
                        "Ouvrir les réglages des notifications"
                );

        open.setOnClickListener(v->{
            try{
                startActivity(
                        new Intent(
                                Settings.ACTION_APP_NOTIFICATION_SETTINGS
                        ).putExtra(
                                Settings.EXTRA_APP_PACKAGE,
                                getPackageName()
                        )
                );
            }catch(Exception e){
                startActivity(
                        new Intent(
                                Settings.ACTION_SETTINGS
                        )
                );
            }
        });

        notif.addView(open);
        content.addView(notif);

        LinearLayout reset=card();

        reset.addView(
                tv("♻️ Données",18)
        );

        Button rb=
                lightBtn(
                        "Réinitialiser la progression"
                );

        rb.setOnClickListener(v->
                new AlertDialog.Builder(this)
                        .setTitle("Réinitialiser ?")
                        .setMessage(
                                "XP, série, leçons et progression seront remis à zéro."
                        )
                        .setNegativeButton(
                                "Annuler",
                                null
                        )
                        .setPositiveButton(
                                "Réinitialiser",
                                (d,w)->{
                                    xp=0;
                                    streak=0;
                                    completedLessons=0;
                                    currentLesson=1;
                                    dailyXp=0;
                                    save();
                                    home();
                                }
                        )
                        .show()
        );

        reset.addView(rb);
        content.addView(reset);

        content.addView(
                tv(
                        "\nJoEnglish\n"+
                        "Apprends aujourd’hui, un meilleur demain !\n"+
                        "Créé par Joseph Marchand\n"+
                        "Version 1.0",
                        14
                )
        );

        backHome();
    }

    private void editProfile(){
        screen();

        content.addView(
                title("👤 Modifier le profil")
        );

        EditText n=
                input(
                        "Nom d’affichage"
                );

        n.setText(firstName);

        EditText u=
                input(
                        "@nom_utilisateur"
                );

        u.setText(username);

        content.addView(n);
        content.addView(u);

        Button photo=
                btn(
                        "🖼 Choisir une photo de profil"
                );

        photo.setOnClickListener(v->{
            Intent i=
                    new Intent(
                            Intent.ACTION_OPEN_DOCUMENT
                    );

            i.setType("image/*");

            i.addCategory(
                    Intent.CATEGORY_OPENABLE
            );

            startActivityForResult(i,77);
        });

        content.addView(photo);

        Button saveb=
                btn("Enregistrer");

        saveb.setOnClickListener(v->{
            firstName=
                    n.getText()
                     .toString()
                     .trim();

            username=
                    u.getText()
                     .toString()
                     .trim();

            save();
            settings();
        });

        content.addView(saveb);

        backHome();
                }
        @Override
    protected void onActivityResult(
            int request,
            int result,
            Intent data
    ){
        super.onActivityResult(
                request,
                result,
                data
        );

        if(
                request==77 &&
                result==RESULT_OK &&
                data!=null &&
                data.getData()!=null
        ){
            profilePhoto=data.getData();

            try{
                getContentResolver()
                        .takePersistableUriPermission(
                                profilePhoto,
                                data.getFlags()
                                &
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        );
            }catch(Exception ignored){}

            save();

            Toast.makeText(
                    this,
                    "Photo enregistrée.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void backHome(){
        Button b=
                lightBtn(
                        "🏠 Retour à l’accueil"
                );

        b.setOnClickListener(
                v->home()
        );

        content.addView(b);
    }

    private void speak(String s){
        if(tts!=null){

            if(Build.VERSION.SDK_INT>=21){

                tts.speak(
                        s,
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "jo_"+
                        System.currentTimeMillis()
                );

            }else{

                tts.speak(
                        s,
                        TextToSpeech.QUEUE_FLUSH,
                        null
                );
            }
        }
    }

    @Override
    protected void onDestroy(){
        if(tts!=null){
            tts.stop();
            tts.shutdown();
            tts=null;
        }

        super.onDestroy();
    }
}
