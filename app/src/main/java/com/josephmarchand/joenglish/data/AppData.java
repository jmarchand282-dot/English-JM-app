package com.josephmarchand.joenglish.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Centralise les données du profil et de la progression de JoEnglish.
 */
public class AppData {

    private static final String PREFS_NAME = "joenglish";

    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_TARGET_LANGUAGE = "targetLanguage";
    private static final String KEY_VARIANT = "variant";
    private static final String KEY_GOAL = "goal";
    private static final String KEY_DAILY_GOAL = "dailyGoal";
    private static final String KEY_PROFILE_PHOTO = "profilePhoto";

    private static final String KEY_XP = "xp";
    private static final String KEY_DAILY_XP = "dailyXp";
    private static final String KEY_STREAK = "streak";
    private static final String KEY_COMPLETED_LESSONS = "completedLessons";
    private static final String KEY_CURRENT_LESSON = "currentLesson";

    private final SharedPreferences prefs;

    private String firstName;
    private String username;
    private String level;
    private String targetLanguage;
    private String variant;
    private String goal;
    private String dailyGoal;
    private String profilePhoto;

    private int xp;
    private int dailyXp;
    private int streak;
    private int completedLessons;
    private int currentLesson;

    public AppData(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        load();
    }

    public void load() {
        firstName = prefs.getString(KEY_FIRST_NAME, "");
        username = prefs.getString(KEY_USERNAME, "");
        level = prefs.getString(KEY_LEVEL, "A1");
        targetLanguage = prefs.getString(KEY_TARGET_LANGUAGE, "English");
        variant = prefs.getString(KEY_VARIANT, "American English");
        goal = prefs.getString(KEY_GOAL, "");
        dailyGoal = prefs.getString(KEY_DAILY_GOAL, "50 XP");
        profilePhoto = prefs.getString(KEY_PROFILE_PHOTO, "");

        xp = prefs.getInt(KEY_XP, 0);
        dailyXp = prefs.getInt(KEY_DAILY_XP, 0);
        streak = prefs.getInt(KEY_STREAK, 0);
        completedLessons = prefs.getInt(KEY_COMPLETED_LESSONS, 0);
        currentLesson = prefs.getInt(KEY_CURRENT_LESSON, 1);
    }

    public void save() {
        prefs.edit()
                .putString(KEY_FIRST_NAME, safe(firstName))
                .putString(KEY_USERNAME, safe(username))
                .putString(KEY_LEVEL, safe(level))
                .putString(KEY_TARGET_LANGUAGE, safe(targetLanguage))
                .putString(KEY_VARIANT, safe(variant))
                .putString(KEY_GOAL, safe(goal))
                .putString(KEY_DAILY_GOAL, safe(dailyGoal))
                .putString(KEY_PROFILE_PHOTO, safe(profilePhoto))
                .putInt(KEY_XP, Math.max(0, xp))
                .putInt(KEY_DAILY_XP, Math.max(0, dailyXp))
                .putInt(KEY_STREAK, Math.max(0, streak))
                .putInt(KEY_COMPLETED_LESSONS, Math.max(0, completedLessons))
                .putInt(KEY_CURRENT_LESSON, Math.max(1, currentLesson))
                .apply();
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    // =========================
    // PROFIL
    // =========================

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = safe(firstName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = safe(username);
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = safe(level);
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = safe(targetLanguage);
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = safe(variant);
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = safe(goal);
    }

    public String getDailyGoal() {
        return dailyGoal;
    }

    public void setDailyGoal(String dailyGoal) {
        this.dailyGoal = safe(dailyGoal);
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = safe(profilePhoto);
    }

    public boolean hasProfile() {
        return firstName != null && !firstName.trim().isEmpty();
    }

    // =========================
    // XP
    // =========================

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = Math.max(0, xp);
    }

    public void addXp(int amount) {
        if (amount > 0) {
            xp += amount;
        }
    }

    // =========================
    // XP QUOTIDIEN
    // =========================

    public int getDailyXp() {
        return dailyXp;
    }

    public void setDailyXp(int dailyXp) {
        this.dailyXp = Math.max(0, dailyXp);
    }

    public void addDailyXp(int amount) {
        if (amount > 0) {
            dailyXp += amount;
        }
    }

    // =========================
    // STREAK
    // =========================

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = Math.max(0, streak);
    }

    public void incrementStreak() {
        streak++;
    }

    // =========================
    // LEÇONS
    // =========================

    public int getCompletedLessons() {
        return completedLessons;
    }

    public void setCompletedLessons(int completedLessons) {
        this.completedLessons = Math.max(0, completedLessons);
    }

    public void completeLesson() {
        completedLessons++;
    }

    public int getCurrentLesson() {
        return currentLesson;
    }

    public void setCurrentLesson(int currentLesson) {
        this.currentLesson = Math.max(1, currentLesson);
    }

    public void advanceToNextLesson() {
        currentLesson++;
    }

    // =========================
    // OBJECTIF QUOTIDIEN
    // =========================

    public int getDailyGoalXp() {
        if (dailyGoal == null || dailyGoal.trim().isEmpty()) {
            return 50;
        }

        String digits = dailyGoal.replaceAll("[^0-9]", "");

        if (digits.isEmpty()) {
            return 50;
        }

        try {
            return Math.max(0, Integer.parseInt(digits));
        } catch (NumberFormatException e) {
            return 50;
        }
    }

    public boolean isDailyGoalCompleted() {
        return dailyXp >= getDailyGoalXp();
    }

    public int getDailyGoalRemaining() {
        return Math.max(0, getDailyGoalXp() - dailyXp);
    }

    public int getDailyGoalProgressPercent() {
        int goalXp = getDailyGoalXp();

        if (goalXp <= 0) {
            return 100;
        }

        int percent = (dailyXp * 100) / goalXp;

        return Math.min(100, Math.max(0, percent));
    }

    // =========================
    // NIVEAU
    // =========================

    public boolean isLevel(String expectedLevel) {
        return level != null
                && level.equalsIgnoreCase(expectedLevel);
    }

    // =========================
    // RÉINITIALISER PROGRESSION
    // =========================

    public void resetProgress() {
        xp = 0;
        dailyXp = 0;
        streak = 0;
        completedLessons = 0;
        currentLesson = 1;

        save();
    }

    // =========================
    // RÉINITIALISER TOUT
    // =========================

    public void resetAll() {
        prefs.edit().clear().apply();

        firstName = "";
        username = "";
        level = "A1";
        targetLanguage = "English";
        variant = "American English";
        goal = "";
        dailyGoal = "50 XP";
        profilePhoto = "";

        xp = 0;
        dailyXp = 0;
        streak = 0;
        completedLessons = 0;
        currentLesson = 1;
    }

    // =========================
    // PRÉFÉRENCES
    // =========================

    public SharedPreferences getPreferences() {
        return prefs;
    }
  }
