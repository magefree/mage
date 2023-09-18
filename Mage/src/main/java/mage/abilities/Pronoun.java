package mage.abilities;

/**
 * Created by IGOUDT on 5-3-2017.
 */
public enum Pronoun {
    HE("he", "him", "his"),
    SHE("she", "her", "her"),
    THEY("they", "them", "their"),
    IT("it", "it", "its");

    private final String subjective;
    private final String objective;
    private final String possessive;

    Pronoun(String subjective, String objective, String possessive) {
        this.subjective = subjective;
        this.objective = objective;
        this.possessive = possessive;
    }

    public String getSubjective() {
        return subjective;
    }

    public String getObjective() {
        return objective;
    }

    public String getPossessive() {
        return possessive;
    }
}
