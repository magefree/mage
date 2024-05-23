package org.mage.test.cards.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class ExcessDamageTest extends CardTestPlayerBase {

    private static final String spill = "Flame Spill";
    private static final String bear = "Grizzly Bears";
    private static final String envoy = "Expedition Envoy";

    //Bonded Construct can’t attack alone.
    private static final String bondedConstruct = "Bonded Construct";

    // Spend only mana produced by creatures to cast this spell.
    private static final String myrSuperion = "Myr Superion";

    private static final String jab = "Flame Jab";
    private static final String spirit = "Pestilent Spirit";
    private static final String myr = "Darksteel Myr";
    private static final String gideon = "Gideon Jura";
    private static final String leyline = "Leyline of Punishment";
    private static final String bolt = "Lightning Bolt";
    private static final String aegar = "Aegar, the Freezing Flame";

    @Test
    public void testExcessDamageRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, spill);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spill, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
        assertLife(playerA, 20 - 2);
    }

    @Test
    public void testExcessDamageAlreadyDamaged() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.HAND, playerA, spill);
        addCard(Zone.HAND, playerA, jab);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, jab, bear);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spill, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testExcessDamageDeathtouch() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, spirit);
        addCard(Zone.HAND, playerA, spill);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spill, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, bear, 1);
        assertPermanentCount(playerA, bear, 0);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testExcessDamageIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, myr);
        addCard(Zone.HAND, playerA, spill);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, spill, myr);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, myr, 0);
        assertPermanentCount(playerA, myr, 1);
        assertLife(playerA, 20 - 3);
    }

    @Test
    public void testExcessDamagePlaneswalker() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, gideon);
        addCard(Zone.BATTLEFIELD, playerA, leyline);
        addCard(Zone.HAND, playerA, spill);
        addCard(Zone.HAND, playerA, bolt);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gideon, true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "0:");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, spill, gideon);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, gideon, 1);
        assertPermanentCount(playerA, gideon, 0);
        assertLife(playerA, 20 - 1);
    }

    @Test
    public void testAegarTheFreezingFlame() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.BATTLEFIELD, playerA, aegar);
        addCard(Zone.HAND, playerA, bolt);
        addCard(Zone.BATTLEFIELD, playerB, bear);

        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, bear);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, bolt, 1);
        assertGraveyardCount(playerB, bear, 1);
    }

    @Test
    public void testAegarTheFreezingFlameMultiDamage() {

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerA, aegar);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerA, envoy);
        addCard(Zone.BATTLEFIELD, playerA, bondedConstruct);
        addCard(Zone.HAND, playerA, bolt);

        addCard(Zone.BATTLEFIELD, playerB, myrSuperion);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, myrSuperion, true);

        attack(2, playerB, myrSuperion, playerA);
        block(2, playerA, bear, myrSuperion);
        block(2, playerA, envoy, myrSuperion);
        block(2, playerA, bondedConstruct, myrSuperion);

        //Assign this much damage to the first blocking creature
        setChoice(playerB, "X=2");

        //Assign this much damage to the second blocking creature
        setChoice(playerB, "X=1");

        //Assign this much damage to the third blocking creature
        setChoice(playerB, "X=1");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // Excess damage was dealt by the total of 3 blocking creatures, 2 of which each dealt more than lethal damage,
        // but Aegar's ability should only trigger once
        assertHandCount(playerA, 1);
    }

    @Test
    public void testMagmaticGalleon() {
        String mg = "Magmatic Galleon";
        // Whenever one or more creatures your opponents control are dealt excess noncombat damage, create a Treasure token.

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, mg);
        addCard(Zone.HAND, playerA, "Storm's Wrath"); // 4 damage to each creature and each planeswalker
        addCard(Zone.BATTLEFIELD, playerB, bear, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Storm's Wrath");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Treasure Token", 1);
        assertGraveyardCount(playerB, bear, 2);
    }

}
