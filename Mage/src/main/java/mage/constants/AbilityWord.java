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
    CELEBRATION("Celebration"),
    CHANNEL("Channel"),
    CHROMA("Chroma"),
    COHORT("Cohort"),
    CONSTELLATION("Constellation"),
    CONVERGE("Converge"),
    CORRUPTED("Corrupted"),
    COUNCILS_DILEMMA("Council's dilemma"),
    COVEN("Coven"),
    DELIRIUM("Delirium"),
    DESCEND_4("Descend 4"),
    DESCEND_8("Descend 8"),
    DOMAIN("Domain"),
    EMINENCE("Eminence"),
    ENRAGE("Enrage"),
    FATEFUL_HOUR("Fateful hour"),
    FATHOMLESS_DESCENT("Fathomless descent"),
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
    PARADOX("Paradox"),
    PARLEY("Parley"),
    RADIANCE("Radiance"),
    RAID("Raid"),
    RALLY("Rally"),
    REVOLT("Revolt"),
    SECRET_COUNCIL("Secret council"),
    SPELL_MASTERY("Spell mastery"),
    STRIVE("Strive"),
    SWEEP("Sweep"),
    TEMPTING_OFFER("Tempting offer"),
    THRESHOLD("Threshold"),
    UNDERGROWTH("Undergrowth"),
    WILL_OF_THE_COUNCIL("Will of the council"),
    WILL_OF_THE_PLANESWALKERS("Will of the planeswalkers");

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
