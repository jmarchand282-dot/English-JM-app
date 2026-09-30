package com.josephmarchand.joenglish.progress;

import com.josephmarchand.joenglish.data.AppData;
import com.josephmarchand.joenglish.lessons.LessonBank;
import com.josephmarchand.joenglish.models.Lesson;

import java.util.List;

/**
 * Gestionnaire central de la progression de JoEnglish.
 *
 * Il fait le lien entre AppData et LessonBank.
 *
 * Règle actuelle :
 * - La première leçon est toujours disponible.
 * - Une leçon terminée déverrouille la suivante.
 * - Les leçons déjà terminées restent accessibles.
 * - La progression est conservée dans AppData.
 */
public class ProgressManager {

    private final AppData appData;

    public ProgressManager(AppData appData) {
        this.appData = appData;
    }

    // =========================================================
    // DONNÉES DE PROGRESSION
    // =========================================================

    public int getXp() {
        return appData.getXp();
    }

    public int getDailyXp() {
        return appData.getDailyXp();
    }

    public int getStreak() {
        return appData.getStreak();
    }

    public int getCompletedLessons() {
        return appData.getCompletedLessons();
    }

    public int getCurrentLessonId() {
        return appData.getCurrentLesson();
    }

    // =========================================================
    // LEÇONS
    // =========================================================

    public Lesson getCurrentLesson() {
        return LessonBank.getLessonById(
                appData.getCurrentLesson()
        );
    }

    public Lesson getNextLesson() {
        return LessonBank.getNextLesson(
                appData.getCompletedLessons()
        );
    }

    public Lesson getLesson(int lessonId) {
        return LessonBank.getLessonById(lessonId);
    }

    public List<Lesson> getAllLessons() {
        return LessonBank.getLessons();
    }

    public List<Lesson> getLessonsByLevel(String level) {
        return LessonBank.getLessonsByLevel(level);
    }

    // =========================================================
    // DÉVERROUILLAGE
    // =========================================================

    public boolean isLessonUnlocked(int lessonId) {

        if (lessonId <= 0) {
            return false;
        }

        return LessonBank.isLessonUnlocked(
                lessonId,
                appData.getCompletedLessons()
        );
    }

    public boolean isLessonCompleted(int lessonId) {

        if (lessonId <= 0) {
            return false;
        }

        return lessonId <= appData.getCompletedLessons();
    }

    public boolean isCurrentLesson(int lessonId) {
        return lessonId == appData.getCurrentLesson();
    }

    // =========================================================
    // TERMINER UNE LEÇON
    // =========================================================

    public boolean completeLesson(int lessonId) {

        Lesson lesson = LessonBank.getLessonById(lessonId);

        if (lesson == null) {
            return false;
        }

        /*
         * Une leçon déjà terminée ne doit pas augmenter
         * automatiquement le compteur une deuxième fois.
         */
        if (isLessonCompleted(lessonId)) {
            return false;
        }

        /*
         * On exige que ce soit la leçon actuellement accessible.
         */
        if (!isLessonUnlocked(lessonId)) {
            return false;
        }

        appData.setCompletedLessons(
                appData.getCompletedLessons() + 1
        );

        appData.setCurrentLesson(
                lessonId + 1
        );

        appData.addXp(lesson.getXp());
        appData.addDailyXp(lesson.getXp());

        appData.save();

        return true;
    }

    // =========================================================
    // XP
    // =========================================================

    public void addXp(int amount) {

        if (amount <= 0) {
            return;
        }

        appData.addXp(amount);
        appData.addDailyXp(amount);
        appData.save();
    }

    // =========================================================
    // STREAK
    // =========================================================

    public void incrementStreak() {

        appData.incrementStreak();
        appData.save();
    }

    public void setStreak(int streak) {

        appData.setStreak(streak);
        appData.save();
    }

    // =========================================================
    // OBJECTIF QUOTIDIEN
    // =========================================================

    public int getDailyGoalXp() {
        return appData.getDailyGoalXp();
    }

    public int getDailyGoalRemaining() {
        return appData.getDailyGoalRemaining();
    }

    public int getDailyGoalProgressPercent() {
        return appData.getDailyGoalProgressPercent();
    }

    public boolean isDailyGoalCompleted() {
        return appData.isDailyGoalCompleted();
    }

    // =========================================================
    // PROGRESSION GÉNÉRALE
    // =========================================================

    public int getTotalLessonCount() {
        return LessonBank.getLessonCount();
    }

    public int getProgressPercent() {

        int total = getTotalLessonCount();

        if (total <= 0) {
            return 0;
        }

        int completed = appData.getCompletedLessons();

        if (completed >= total) {
            return 100;
        }

        return (completed * 100) / total;
    }

    public boolean isCourseCompleted() {
        return appData.getCompletedLessons()
                >= LessonBank.getLessonCount();
    }

    // =========================================================
    // RESET
    // =========================================================

    public void resetProgress() {
        appData.resetProgress();
    }

    // =========================================================
    // SAUVEGARDE
    // =========================================================

    public void save() {
        appData.save();
    }
                                            }
