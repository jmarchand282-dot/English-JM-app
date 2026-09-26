package com.josephmarchand.joenglish.lessons;

import com.josephmarchand.joenglish.models.Lesson;

import java.util.ArrayList;
import java.util.List;

public final class LessonBank {

    private LessonBank() {
    }

    public static List<Lesson> getLessons() {
        List<Lesson> lessons = new ArrayList<>();

        lessons.add(new Lesson(
                1,
                "A1",
                "Greetings",
                "Apprendre les salutations essentielles.",
                new String[]{
                        "Hello = Bonjour",
                        "Hi = Salut",
                        "Good morning = Bonjour (matin)",
                        "Good evening = Bonsoir",
                        "Goodbye = Au revoir"
                },
                new String[]{
                        "Hello",
                        "Hi",
                        "Good morning"
                },
                new String[]{
                        "Bonjour",
                        "Salut",
                        "Bonjour (le matin)"
                },
                10
        ));

        lessons.add(new Lesson(
                2,
                "A1",
                "Introducing yourself",
                "Savoir se présenter simplement.",
                new String[]{
                        "My name is... = Je m'appelle...",
                        "I am... = Je suis...",
                        "Nice to meet you = Ravi de vous rencontrer",
                        "Where are you from? = Tu viens d'où ?",
                        "I am from... = Je viens de..."
                },
                new String[]{
                        "My name is Joseph.",
                        "I am from Congo.",
                        "Nice to meet you."
                },
                new String[]{
                        "Je m'appelle Joseph.",
                        "Je viens du Congo.",
                        "Ravi de vous rencontrer."
                },
                10
        ));

        lessons.add(new Lesson(
                3,
                "A1",
                "Family",
                "Apprendre les principaux mots de la famille.",
                new String[]{
                        "Father = Père",
                        "Mother = Mère",
                        "Brother = Frère",
                        "Sister = Sœur",
                        "Family = Famille"
                },
                new String[]{
                        "father",
                        "mother",
                        "brother"
                },
                new String[]{
                        "père",
                        "mère",
                        "frère"
                },
                10
        ));

        lessons.add(new Lesson(
                4,
                "A1",
                "Numbers",
                "Apprendre les nombres de base.",
                new String[]{
                        "One = Un",
                        "Two = Deux",
                        "Three = Trois",
                        "Four = Quatre",
                        "Five = Cinq"
                },
                new String[]{
                        "one",
                        "two",
                        "five"
                },
                new String[]{
                        "un",
                        "deux",
                        "cinq"
                },
                10
        ));

        lessons.add(new Lesson(
                5,
                "A1",
                "Everyday objects",
                "Identifier des objets courants.",
                new String[]{
                        "Book = Livre",
                        "Phone = Téléphone",
                        "House = Maison",
                        "Table = Table",
                        "Chair = Chaise"
                },
                new String[]{
                        "book",
                        "phone",
                        "house"
                },
                new String[]{
                        "livre",
                        "téléphone",
                        "maison"
                },
                10
        ));

        lessons.add(new Lesson(
                6,
                "A1",
                "Basic verbs",
                "Découvrir les verbes anglais les plus utiles.",
                new String[]{
                        "Be = Être",
                        "Have = Avoir",
                        "Go = Aller",
                        "Come = Venir",
                        "Want = Vouloir"
                },
                new String[]{
                        "I am happy.",
                        "I have a book.",
                        "I want water."
                },
                new String[]{
                        "Je suis heureux.",
                        "J'ai un livre.",
                        "Je veux de l'eau."
                },
                10
        ));

        lessons.add(new Lesson(
                7,
                "A1",
                "Food and drinks",
                "Parler de nourriture et de boissons.",
                new String[]{
                        "Water = Eau",
                        "Food = Nourriture",
                        "Bread = Pain",
                        "Rice = Riz",
                        "Milk = Lait"
                },
                new String[]{
                        "water",
                        "bread",
                        "milk"
                },
                new String[]{
                        "eau",
                        "pain",
                        "lait"
                },
                10
        ));

        lessons.add(new Lesson(
                8,
                "A1",
                "Daily routine",
                "Décrire une journée simple.",
                new String[]{
                        "Wake up = Se réveiller",
                        "Eat = Manger",
                        "Work = Travailler",
                        "Study = Étudier",
                        "Sleep = Dormir"
                },
                new String[]{
                        "I study English.",
                        "I work every day.",
                        "I sleep at night."
                },
                new String[]{
                        "J'étudie l'anglais.",
                        "Je travaille chaque jour.",
                        "Je dors la nuit."
                },
                10
        ));

        lessons.add(new Lesson(
                9,
                "A1",
                "Present simple",
                "Comprendre les bases du présent simple.",
                new String[]{
                        "I work.",
                        "You work.",
                        "He works.",
                        "She works.",
                        "They work."
                },
                new String[]{
                        "I study English.",
                        "She works here.",
                        "They live in Congo."
                },
                new String[]{
                        "J'étudie l'anglais.",
                        "Elle travaille ici.",
                        "Ils vivent au Congo."
                },
                15
        ));

        lessons.add(new Lesson(
                10,
                "A1",
                "Questions",
                "Former des questions simples en anglais.",
                new String[]{
                        "What? = Quoi ? / Quel ?",
                        "Where? = Où ?",
                        "When? = Quand ?",
                        "Who? = Qui ?",
                        "Why? = Pourquoi ?"
                },
                new String[]{
                        "Where do you live?",
                        "What is your name?",
                        "Who is he?"
                },
                new String[]{
                        "Où habites-tu ?",
                        "Comment t'appelles-tu ?",
                        "Qui est-il ?"
                },
                15
        ));

        lessons.add(new Lesson(
                11,
                "A1",
                "Travel basics",
                "Apprendre des expressions utiles pour voyager.",
                new String[]{
                        "Airport = Aéroport",
                        "Ticket = Billet",
                        "Hotel = Hôtel",
                        "Train = Train",
                        "Bus = Bus"
                },
                new String[]{
                        "Where is the airport?",
                        "I need a ticket.",
                        "Where is my hotel?"
                },
                new String[]{
                        "Où est l'aéroport ?",
                        "J'ai besoin d'un billet.",
                        "Où est mon hôtel ?"
                },
                15
        ));

        lessons.add(new Lesson(
                12,
                "A1",
                "Review A1",
                "Réviser les notions essentielles du niveau A1.",
                new String[]{
                        "Hello = Bonjour",
                        "Family = Famille",
                        "Food = Nourriture",
                        "Study = Étudier",
                        "Travel = Voyager"
                },
                new String[]{
                        "Hello, my name is Joseph.",
                        "I study English every day.",
                        "I want to travel."
                },
                new String[]{
                        "Bonjour, je m'appelle Joseph.",
                        "J'étudie l'anglais chaque jour.",
                        "Je veux voyager."
                },
                20
        ));

        return lessons;
    }

    public static List<Lesson> getLessonsForLevel(String level) {
        List<Lesson> result = new ArrayList<>();

        for (Lesson lesson : getLessons()) {
            if (lesson.getLevel().equalsIgnoreCase(level)) {
                result.add(lesson);
            }
        }

        return result;
    }

    public static Lesson getLessonById(int id) {
        for (Lesson lesson : getLessons()) {
            if (lesson.getId() == id) {
                return lesson;
            }
        }

        return null;
    }
                  }
