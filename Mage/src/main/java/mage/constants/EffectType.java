package mage.constants;

/**
 *
 * @author North
 */
public enum EffectType {

    ONESHOT("One Shot Effect"),
    CONTINUOUS("Continuous Effect"),
    CONTINUOUS_RULE_MODIFICATION("Layered rule modification"),
    REPLACEMENT("Replacement Effect"),
    PREVENTION("Prevention Effect"),
    REDIRECTION("Redirection Effect"),
    ASTHOUGH("As Though Effect"),
    RESTRICTION("Restriction Effect"),
    RESTRICTION_UNTAP_NOT_MORE_THAN("Restriction untap not more than Effect"),
    REQUIREMENT("Requirement Effect"),
    COSTMODIFICATION("Cost Modification Effect"),
    SPLICE("Splice Card  Effect");

    private final String text;

    EffectType(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
