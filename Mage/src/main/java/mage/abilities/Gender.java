package mage.abilities;

/**
 * Created by IGOUDT on 5-3-2017.
 */
public enum Gender {
    MALE("his", "him"), FEMALE("her", "her"), NEUTRAL("its", "it");

    String personalPronoun;
    String possesivePronoun;

    Gender(String possessive, String personal) {
        personalPronoun = personal;
        possesivePronoun = possessive;
    }

    public String getPersonalPronoun() {
        return personalPronoun;
    }

    public String getPossesivePronoun() {
        return possesivePronoun;
    }

}
