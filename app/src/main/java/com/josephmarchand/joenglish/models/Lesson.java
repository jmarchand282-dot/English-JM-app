package com.josephmarchand.joenglish.models;

public class Lesson {

    private final int id;
    private final String level;
    private final String title;
    private final String objective;
    private final String[] vocabulary;
    private final String[] examples;
    private final String[] translations;
    private final int xp;

    public Lesson(
            int id,
            String level,
            String title,
            String objective,
            String[] vocabulary,
            String[] examples,
            String[] translations,
            int xp
    ) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.objective = objective;
        this.vocabulary = vocabulary;
        this.examples = examples;
        this.translations = translations;
        this.xp = xp;
    }

    public int getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public String getObjective() {
        return objective;
    }

    public String[] getVocabulary() {
        return vocabulary;
    }

    public String[] getExamples() {
        return examples;
    }

    public String[] getTranslations() {
        return translations;
    }

    public int getXp() {
        return xp;
    }
}
