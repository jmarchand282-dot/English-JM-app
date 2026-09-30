package com.josephmarchand.joenglish.lessons;

import com.josephmarchand.joenglish.models.Lesson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LessonBank {
    private static final List<Lesson> LESSONS = new ArrayList<>();

    static {
        LESSONS.add(lesson(1,"A1","Se présenter","Apprendre à dire son nom, son âge et son identité.","Communication","Débutant",20,10,
                new String[]{"hello","hi","name","I","you","my","your","am","are","student"},
                new String[]{"bonjour","salut","nom","je","tu / vous","mon / ma","ton / ta / votre","suis","es / êtes","étudiant"},
                new String[]{"Hello, my name is Joseph.","Hi, I am a student.","What is your name?","My name is Joseph.","I am from the Democratic Republic of the Congo."},
                new String[]{"Hello, my name is Joseph.","Hi, I am a student.","What is your name?","My name is Joseph.","I am from the Democratic Republic of the Congo."},
                new String[]{"Nice to meet you.","My name is...","What is your name?","I am a student."},
                new String[]{"What is your name?","My name ___ Joseph.","I ___ a student.","___, my name is Joseph."},
                new String[]{"is","is","am","Hello"},
                new String[]{"Avec le verbe be, on utilise « is » avec my name.","My name is... est la structure normale pour donner son nom.","Avec I, on utilise am.","Hello est une formule courante pour saluer quelqu’un."}));

        LESSONS.add(lesson(2,"A1","Les salutations","Apprendre les salutations anglaises les plus courantes.","Communication","Débutant",20,10,
                new String[]{"hello","hi","good morning","good afternoon","good evening","good night","bye","goodbye","see you","welcome"},
                new String[]{"bonjour","salut","bonjour le matin","bonjour l'après-midi","bonsoir","bonne nuit","salut / au revoir","au revoir","à bientôt","bienvenue"},
                new String[]{"Good morning!","Good afternoon!","Good evening!","Good night!","See you tomorrow!"},
                new String[]{"Good morning!","Good afternoon!","Good evening!","Good night!","See you tomorrow!"},
                new String[]{"How are you?","Nice to meet you.","See you later.","Have a nice day!"},
                new String[]{"___ morning!","___ evening!","___ you later.","___ night!"},
                new String[]{"Good","Good","See","Good"},
                new String[]{"Good morning est utilisé le matin.","Good evening est utilisé pour saluer le soir.","See you later signifie à plus tard.","Good night est utilisé pour souhaiter une bonne nuit."}));

        LESSONS.add(lesson(3,"A1","Le verbe BE","Découvrir les formes principales du verbe être.","Grammaire","Débutant",25,12,
                new String[]{"I am","you are","he is","she is","it is","we are","they are","I'm","you're","they're"},
                new String[]{"je suis","tu es / vous êtes","il est","elle est","il / elle est pour une chose","nous sommes","ils / elles sont","je suis","tu es / vous êtes","ils / elles sont"},
                new String[]{"I am happy.","You are my friend.","He is a teacher.","She is from Canada.","They are students."},
                new String[]{"I am happy.","You are my friend.","He is a teacher.","She is from Canada.","They are students."},
                new String[]{"I am...","You are...","He is...","She is..."},
                new String[]{"I ___ happy.","She ___ a teacher.","They ___ students.","You ___ my friend."},
                new String[]{"am","is","are","are"},
                new String[]{"Avec I, la forme correcte est am.","Avec she, la forme correcte est is.","Avec they, la forme correcte est are.","Avec you, la forme correcte est are."}));

        LESSONS.add(lesson(4,"A1","Les nombres","Apprendre les nombres anglais de 1 à 20.","Vocabulaire","Débutant",20,10,
                new String[]{"one","two","three","four","five","six","seven","eight","nine","ten"},
                new String[]{"un","deux","trois","quatre","cinq","six","sept","huit","neuf","dix"},
                new String[]{"I have one book.","I have two friends.","She is three years old.","There are five students.","I need ten minutes."},
                new String[]{"I have one book.","I have two friends.","She is three years old.","There are five students.","I need ten minutes."},
                new String[]{"How many?","One more, please.","I have two.","Ten minutes."},
                new String[]{"I have ___ book.","She has ___ friends.","There are ___ students.","I need ___ minutes."},
                new String[]{"one","two","five","ten"},
                new String[]{"One correspond au nombre 1.","Two correspond au nombre 2.","Five correspond au nombre 5.","Ten correspond au nombre 10."}));

        LESSONS.add(lesson(5,"A1","La famille","Apprendre les principaux mots pour parler de sa famille.","Vocabulaire","Débutant",25,12,
                new String[]{"family","father","mother","parent","brother","sister","son","daughter","husband","wife"},
                new String[]{"famille","père","mère","parent","frère","sœur","fils","fille","mari","épouse"},
                new String[]{"This is my family.","My father is a teacher.","My mother is kind.","I have one brother.","She is my sister."},
                new String[]{"This is my family.","My father is a teacher.","My mother is kind.","I have one brother.","She is my sister."},
                new String[]{"This is my family.","My mother is...","My father is...","I have one brother."},
                new String[]{"My ___ is a teacher.","She is my ___.","I have one ___.","This is my ___."},
                new String[]{"father","sister","brother","family"},
                new String[]{"Father signifie père.","Sister signifie sœur.","Brother signifie frère.","Family signifie famille."}));
    }

    private static Lesson lesson(int id,String level,String title,String description,String category,String difficulty,int xp,int minutes,
                                 String[] vocabulary,String[] translations,String[] examples,String[] audioTexts,String[] expressions,
                                 String[] questions,String[] answers,String[] explanations) {
        return new Lesson(id,level,title,description,category,difficulty,xp,minutes,
                Arrays.asList(vocabulary),Arrays.asList(translations),Arrays.asList(examples),Arrays.asList(audioTexts),
                Arrays.asList(expressions),Arrays.asList(questions),Arrays.asList(answers),Arrays.asList(explanations));
    }

    public static List<Lesson> getLessons() { return new ArrayList<>(LESSONS); }

    public static Lesson getLessonById(int id) {
        for (Lesson lesson : LESSONS) if (lesson.getId() == id) return lesson;
        return null;
    }

    public static int getLessonCount() { return LESSONS.size(); }

    public static List<Lesson> getLessonsByLevel(String level) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson lesson : LESSONS) {
            if (lesson.getLevel().equalsIgnoreCase(level)) result.add(lesson);
        }
        return result;
    }
                           }
                                          
