package com.josephmarchand.joenglish.progress;

import com.josephmarchand.joenglish.data.AppData;
import com.josephmarchand.joenglish.lessons.LessonBank;
import com.josephmarchand.joenglish.models.Lesson;

import java.util.List;

/**
 * Gestionnaire central de la progression de JoEnglish.
 *
 * Cette classe fait le lien entre :
 * - AppData : sauvegarde locale
 * - LessonBank : contenu des leçons
 * - MainActivity : affichage et progression de l'utilisateur
 */
public class ProgressManager {

    private final AppData appData;
    private final LessonBank lessonBank;

    public ProgressManager(AppData appData) {
        this.appData = appData;
        this.lessonBank = new LessonBank();
    }

    // ============================================================
    // PROGRESSION GENERALE
    // ============================================================

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

    // ============================================================
    // LEÇONS
    // ============================================================

    public Lesson getCurrentLesson() {
        return lessonBank.getLessonById(
                appData.getCurrentLesson()
        );
    }

    public Lesson getNextLesson() {
        return lessonBank.getNextLesson(
                appData.getCompletedLessons()
        );
    }

    public Lesson getLesson(int id) {
        return lessonBank.getLessonById(id);
    }

    public List<Lesson> getAllLessons() {
        return lessonBank.getLessons();
    }

    public List<Lesson> getLessonsByLevel(String level) {
        return lessonBank.getLessonsByLevel(level);
    }

    /**
     * Nombre total de leçons disponibles dans LessonBank.
     */
    public int getTotalLessons() {
        return lessonBank.getLessonCount();
    }

    /**
     * Alias pratique pour le nombre total de leçons.
     */
    public int getTotalLessonCount() {
        return lessonBank.getLessonCount();
    }

    // ============================================================
    // VERROUILLAGE DES LEÇONS
    // ============================================================

    public boolean isLessonUnlocked(int lessonId) {
        return lessonBank.isLessonUnlocked(
                lessonId,
                appData.getCompletedLessons()
        );
    }

    public boolean isLessonCompleted(int lessonId) {
        return lessonId <= appData.getCompletedLessons();
    }

    public boolean isCurrentLesson(int lessonId) {
        return lessonId == appData.getCurrentLesson();
    }

    // ============================================================
    // PROGRESSION D'UNE LEÇON
    // ============================================================

    public int getLessonProgressPercent(int lessonId) {
        return lessonBank.getLessonProgressPercent(
                lessonId,
                appData.getCompletedLessons()
        );
    }

    public int getOverallProgressPercent() {

        int total = lessonBank.getLessonCount();

        if (total <= 0) {
            return 0;
        }

        int completed = appData.getCompletedLessons();

        if (completed <= 0) {
            return 0;
        }

        if (completed >= total) {
            return 100;
        }

        return (completed * 100) / total;
    }

    public boolean isCourseCompleted() {
        return appData.getCompletedLessons()
                >= lessonBank.getLessonCount();
    }

    // ============================================================
    // TERMINER UNE LEÇON
    // ============================================================

    public boolean completeLesson(int lessonId) {

        Lesson lesson = lessonBank.getLessonById(lessonId);

        if (lesson == null) {
            return false;
        }

        // Déjà terminée
        if (isLessonCompleted(lessonId)) {
            return false;
        }

        // Leçon verrouillée
        if (!isLessonUnlocked(lessonId)) {
            return false;
        }

        int xpReward = lesson.getXp();

        if (xpReward < 0) {
            xpReward = 0;
        }

        // Ajouter la leçon terminée
        int completed = appData.getCompletedLessons();

        completed++;

        // Éviter de dépasser le nombre réel de leçons
        if (completed > lessonBank.getLessonCount()) {
            completed = lessonBank.getLessonCount();
        }

        appData.setCompletedLessons(completed);

        // La prochaine leçon devient la leçon actuelle
        int nextLessonId = completed + 1;

        if (nextLessonId <= lessonBank.getLessonCount()) {
            appData.setCurrentLesson(nextLessonId);
        } else {
            appData.setCurrentLesson(
                    lessonBank.getLessonCount()
            );
        }

        // XP total
        appData.setXp(
                appData.getXp() + xpReward
        );

        // XP quotidien
        appData.setDailyXp(
                appData.getDailyXp() + xpReward
        );

        // Sauvegarder immédiatement
        appData.save();

        return true;
    }

    // ============================================================
    // XP
    // ============================================================

    public void addXp(int amount) {

        if (amount <= 0) {
            return;
        }

        appData.setXp(
                appData.getXp() + amount
        );

        appData.setDailyXp(
                appData.getDailyXp() + amount
        );

        appData.save();
    }

    // ============================================================
    // SERIE / STREAK
    // ============================================================

    public void incrementStreak() {

        appData.setStreak(
                appData.getStreak() + 1
        );

        appData.save();
    }

    public void setStreak(int streak) {

        if (streak < 0) {
            streak = 0;
        }

        appData.setStreak(streak);
        appData.save();
    }

    // ============================================================
    // OBJECTIF QUOTIDIEN
    // ============================================================

    public int getDailyGoal() {
        return appData.getDailyGoal();
    }

    public void setDailyGoal(int goal) {

        if (goal < 0) {
            goal = 0;
        }

        appData.setDailyGoal(goal);
        appData.save();
    }

    public boolean isDailyGoalCompleted() {

        return appData.getDailyXp()
                >= appData.getDailyGoal();
    }

    // ============================================================
    // DONNEES
    // ============================================================

    public AppData getAppData() {
        return appData;
    }

    public LessonBank getLessonBank() {
        return lessonBank;
    }

    // ============================================================
    // SAUVEGARDE
    // ============================================================

    public void save() {
        appData.save();
    }

    // ============================================================
    // RESET PROGRESSION
    // ============================================================

    public void resetProgress() {
        appData.resetProgress();
    }

}
