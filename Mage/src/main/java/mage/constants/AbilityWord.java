package mage.constants;

import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public enum AbilityWord {

    ADDENDUM("Addendum"),
    ADAMANT("Adamant"),
    ALLIANCE("Alliance"),
    BATTALION("Battalion"),
    BLOODRUSH("Bloodrush"),
    CHANNEL("Channel"),
    CHROMA("Chroma"),
    COHORT("Cohort"),
    CONSTELLATION("Constellation"),
    CONVERGE("Converge"),
    CORRUPTED("Corrupted"),
    COUNCILS_DILEMMA("Council's dilemma"),
    COVEN("Coven"),
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
    MAGECRAFT("Magecraft"),
    MORBID("Morbid"),
    PACK_TACTICS("Pack tactics"),
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

    public String formatWord() {
        return CardUtil.italicizeWithEmDash(this.toString());
    }

    @Override
    public String toString() {
        return text;
    }

}
