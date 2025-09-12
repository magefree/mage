package org.mage.test.cards.single.lci;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AbuelosAwakeningTest extends CardTestPlayerBase {

    /*
    Abuelo's Awakening
    {X}{3}{W}
    Sorcery
    Return target artifact or non-Aura enchantment card from your graveyard to the battlefield with X additional +1/+1 counters on it.
    It’s a 1/1 Spirit creature with flying in addition to its other types.
     */
    public static final String abuelosAwakening = "Abuelo's Awakening";
    /*
    Talisman of Progress
    {2}
    Artifact
    {T}: Add {C}.
    {T}: Add {W} or {U}. This artifact deals 1 damage to you.
     */
    public static final String talisman = "Talisman of Progress";
    /*
    Lightning Bolt
    {R}
    Instant
    Lightning Bolt deals 3 damage to any target.
     */
    public static final String bolt = "Lightning Bolt";

    @Test
    public void testAbuelosAwakening() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, talisman);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, abuelosAwakening);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, abuelosAwakening, talisman);
        setChoiceAmount(playerA, 2);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, talisman, CounterType.P1P1, 2);
        assertType(talisman, CardType.CREATURE, true);
        assertSubtype(talisman, SubType.SPIRIT);
        assertBasePowerToughness(playerA, talisman, 1, 1);
    }

    @Test
    public void testAbuelosAwakeningDies() {
        setStrictChooseMode(true);

        addCard(Zone.GRAVEYARD, playerA, talisman);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, abuelosAwakening);
        addCard(Zone.HAND, playerB, bolt);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, abuelosAwakening, talisman);
        setChoiceAmount(playerA, 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, bolt, talisman);

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, talisman, 0);
        assertGraveyardCount(playerA, talisman, 1);
    }
}
