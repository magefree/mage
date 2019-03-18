
package mage.constants;

/**
 *
 * @author LevelX2
 */
public enum AbilityWord {

    ADDENDUM("Addendum"),
    BATTALION("Battalion"),
    BLOODRUSH("Bloodrush"),
    CHANNEL("Channel"),
    CHROMA("Chroma"),
    COHORT("Cohort"),
    CONSTELLATION("Constellation"),
    CONVERGE("Converge"),
    DELIRIUM("Delirium"),
    DOMAIN("Domain"),
    EMINENCE("Eminence"),
    ENRAGE("Enrage"),
    FATEFUL_HOUR("Fateful hour"),
    FEROCIOUS("Ferocious"),
    FORMIDABLE("Formidable"),
    GRANDEUR("Grandeur"),
    HATE("Hate"),
    HELLBENT("Hellbent"),
    HEROIC("Heroic"),
    IMPRINT("Imprint"),
    INSPIRED("Inspired"),
    JOIN_FORCES("Join forces"),
    KINSHIP("Kinship"),
    LANDFALL("Landfall"),
    LIEUTENANT("Lieutenant"),
    METALCRAFT("Metalcraft"),
    MORBID("Morbid"),
    PARLEY("Parley"),
    RADIANCE("Radiance"),
    RAID("Raid"),
    RALLY("Rally"),
    REVOLT("Revolt"),
    SPELL_MASTERY("Spell mastery"),
    STRIVE("Strive"),
    SWEEP("Sweep"),
    TEMPTING_OFFER("Tempting offer"),
    THRESHOLD("Threshold"),
    UNDERGROWTH("Undergrowth"),
    WILL_OF_THE_COUNCIL("Will of the council");

    private final String text;

    AbilityWord(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
