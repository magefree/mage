package mage.constants;

/**
 *
 * @author North
 */
public enum EffectType {

    ONESHOT("One Shot Effect"),
    CONTINUOUS("Continuous Effect"),
    REPLACEMENT("Replacement Effect"),
    PREVENTION("Prevention Effect"),
    REDIRECTION("Redirection Effect"),
    ASTHOUGH("As Though Effect"),
    RESTRICTION("Restriction Effect"),
    REQUIREMENT("Requirement Effect"),
    COSTMODIFICATION("Cost Modification Effect"),
    SPLICE("Splice Card  Effect");

    private String text;

    EffectType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
