package mage.constants;

/**
 * rules:
 * Cards that reference "your commander" instead reference "your Oathbreaker."
 * <p>
 * So in card rules text contains "commander" then you must use COMMANDER_OR_OATHBREAKER.
 * If your card must look to command zone (e.g. target any card) then you must use ANY
 *
 * @author JayDi85
 */
public enum CommanderCardType {
    ANY,
    COMMANDER_OR_OATHBREAKER,
    SIGNATURE_SPELL
}
