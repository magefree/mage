package mage.constants;

/**
 * Created by IGOUDT on 26-3-2017.
 */
public enum SuperType {

    BASIC("Basic"),
    ELITE("Elite"),
    LEGENDARY("Legendary"),
    ONGOING("Ongoing"),
    SNOW("Snow"),
    WORLD("World");

    String text;

    SuperType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
