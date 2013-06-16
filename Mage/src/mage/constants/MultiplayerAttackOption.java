package mage.constants;

/**
 *
 * @author North
 */
public enum MultiplayerAttackOption {
    MULTIPLE("Attack Multiple Players"),
    LEFT("Attack Left"),
    RIGHT("Attack Right");

    private String text;

    MultiplayerAttackOption(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
