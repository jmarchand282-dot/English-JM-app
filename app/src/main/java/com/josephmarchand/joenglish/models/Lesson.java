package com.josephmarchand.joenglish.models;

import java.util.ArrayList;
import java.util.List;

public class Lesson {

    private int id;
    private String level;
    private String title;
    private String description;
    private String category;
    private String difficulty;
    private int xp;
    private int estimatedMinutes;

    private List<String> vocabulary;
    private List<String> translations;
    private List<String> examples;
    private List<String> audioTexts;
    private List<String> expressions;

    private List<String> questions;
    private List<String> answers;
    private List<String> explanations;

    public Lesson(
            int id,
            String level,
            String title,
            String description,
            String category,
            String difficulty,
            int xp,
            int estimatedMinutes,
            List<String> vocabulary,
            List<String> translations,
            List<String> examples,
            List<String> audioTexts,
            List<String> expressions,
            List<String> questions,
            List<String> answers,
            List<String> explanations
    ) {
        this.id = id;
        this.level = level;
        this.title = title;
        this.description = description;
        this.category = category;
        this.difficulty = difficulty;
        this.xp = xp;
        this.estimatedMinutes = estimatedMinutes;

        this.vocabulary = vocabulary != null
                ? vocabulary
                : new ArrayList<>();

        this.translations = translations != null
                ? translations
                : new ArrayList<>();

        this.examples = examples != null
                ? examples
                : new ArrayList<>();

        this.audioTexts = audioTexts != null
                ? audioTexts
                : new ArrayList<>();

        this.expressions = expressions != null
                ? expressions
                : new ArrayList<>();

        this.questions = questions != null
                ? questions
                : new ArrayList<>();

        this.answers = answers != null
                ? answers
                : new ArrayList<>();

        this.explanations = explanations != null
                ? explanations
                : new ArrayList<>();
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

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getXp() {
        return xp;
    }

    public int getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public List<String> getVocabulary() {
        return vocabulary;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public List<String> getExamples() {
        return examples;
    }

    public List<String> getAudioTexts() {
        return audioTexts;
    }

    public List<String> getExpressions() {
        return expressions;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public List<String> getExplanations() {
        return explanations;
    }

    public int getVocabularyCount() {
        return vocabulary.size();
    }

    public int getQuestionCount() {
        return questions.size();
    }

    public int getExpressionCount() {
        return expressions.size();
    }

    public boolean isValid() {
        return title != null
                && !title.trim().isEmpty()
                && level != null
                && !level.trim().isEmpty();
    }
}
