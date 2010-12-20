package org.mage.test.bdd;

/**
 * Controls steps of bdd calls.
 * Uses step for the same "And" calls depending on previous calls.
 * So "And." can be either "Given." or "Then." depending on "Given" or "Then" was called previously.
 *
 * Example:
 *
 *   Given.I.have.a.card("Island");   // remember step here
 *   And.battlefield.has("Plains");   // "And" replaced and Given.battlefield.has("Plains"); is called
 *
 *   Then.graveyards.empty();         // remember step here
 *   And.battlefield.has("Plains");   // "And" replaced and Then.battlefield.has("Plains"); is called
 *
 */
public class StepController {
    public static StepState currentState = StepState.UNKNOWN;
}
