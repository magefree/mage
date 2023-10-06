package org.mage.test.cards.single.dmu;

import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class DanithaBenaliasHopeTest extends CardTestPlayerBase {

    private static final String danitha = "Danitha, Benalia's Hope"; // {4}{W} 4/4 First strike, vigilance, lifelink
    // When Danitha, Benalia's Hope enters the battlefield, you may put an Aura or Equipment card
    // from your hand or graveyard onto the battlefield attached to Danitha.
    private static final String wings = "Nimbus Wings"; // Enchanted creature gets +1/+2 and has flying.
    private static final String flail = "Gorgon Flail"; // Equipped creature gets +1/+1 and has deathtouch.
    private static final String swords = "Swords to Plowshares"; // Exile target creature. Its controller gains life equal to its power.

    @Test
    public void testAuraFromGraveyard() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.GRAVEYARD, playerA, wings);
        addCard(Zone.HAND, playerA, danitha);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, danitha);
        setChoice(playerA, true); // use ability
        setChoice(playerA, wings);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, danitha, 5, 6);
        assertAbility(playerA, danitha, FlyingAbility.getInstance(), true);
    }

    @Test
    public void testNothingToAttach() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, danitha);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, danitha);
        setChoice(playerA, true); // attempt to use ability
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, danitha, 4, 4);
    }

    @Test
    public void testEquipmentFromHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.HAND, playerA, flail);
        addCard(Zone.HAND, playerA, danitha);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, danitha);
        setChoice(playerA, true); // use ability
        setChoice(playerA, flail);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, danitha, 5, 5);
        assertAbility(playerA, danitha, DeathtouchAbility.getInstance(), true);
    }

    @Test
    public void testEquipmentFromHandButExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.HAND, playerA, flail);
        addCard(Zone.HAND, playerA, danitha);
        addCard(Zone.HAND, playerA, swords);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, danitha);
        setChoice(playerA, true); // use ability
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swords, danitha);
        setChoice(playerA, flail);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, flail, 1);
        assertExileCount(playerA, danitha, 1);
        assertLife(playerA, 24);
    }

    @Test
    public void testAuraFromGraveyardButExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.GRAVEYARD, playerA, wings);
        addCard(Zone.HAND, playerA, danitha);
        addCard(Zone.HAND, playerA, swords);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, danitha);
        setChoice(playerA, true); // use ability
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, swords, danitha);
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // no longer can attach Aura

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, wings, 1);
        assertExileCount(playerA, danitha, 1);
        assertLife(playerA, 24);
    }

}
