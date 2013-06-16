package mage.constants;

/**
 *
 * @author North
 */
public enum Duration {
    OneUse(""),
    EndOfGame("for the rest of the game"),
    WhileOnBattlefield(""),
    WhileOnStack(""),
    WhileInGraveyard(""),
    EndOfTurn("until end of turn"),
    EndOfCombat("until end of combat"),
    Custom("");

    private String text;

    Duration(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
