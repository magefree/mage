
package mage.constants;

/**
 *
 * @author LevelX2
 */

public enum SkillLevel {
    BEGINNER ("Beginner"),
    CASUAL("Casual"),
    SERIOUS ("Serious");

    private final String text;

    SkillLevel(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}