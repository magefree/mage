package org.mage.test.cards.mana;

import mage.abilities.keyword.FlyingAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ConditionalManaTest extends CardTestPlayerBase {

    @Test
    public void testNormalUse() {
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);
        // Instant {G}{W}
        // Target player gains 7 life.
        addCard(Zone.HAND, playerA, "Heroes' Reunion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Heroes' Reunion", playerA);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Heroes' Reunion", 1);
        assertHandCount(playerA, "Heroes' Reunion", 0); // player A could not cast it
        assertLife(playerA, 27);
    }

    @Test
    public void testNotAllowedUse() {
        // {T}: Add one mana of any color. Spend this mana only to cast a multicolored spell.
        addCard(Zone.BATTLEFIELD, playerA, "Pillar of the Paruns", 2);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");
        playerA.addChoice("White");
        playerA.addChoice("White");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1); // player A could not cast Silvercoat Lion because the conditional mana is not available
    }

    @Test
    @Ignore
    public void testWorkingWithReflectingPool() {
        addCard(Zone.BATTLEFIELD, playerA, "Cavern of Souls", 1); // can give {C] or {any} mana ({any} with restrictions)
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1); // must give {C} or {any} mana from the Cavern, but without restrictions
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1); // white bear
        addCard(Zone.BATTLEFIELD, playerA, "Upwelling", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void testWorkingWithReflectingPool2() {
        addCard(Zone.BATTLEFIELD, playerA, "Reflecting Pool", 1); // can create white mana without restriction from the Hive
        // {T}: Add {C}.
        // {T}: Add one mana of any color. Spend this mana only to cast a Sliver spell.
        // {5}, {T}: Create a 1/1 colorless Sliver creature token. Activate this ability only if you control a Sliver.
        addCard(Zone.BATTLEFIELD, playerA, "Sliver Hive", 1);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add one mana of any type");

        setChoice(playerA, "White");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }

    /**
     * I wasunable to use "Rosheen Meanderer" ability to pay for "Candelabra of
     * Tawnos" ability even thought it has "X" on its cost
     */
    @Test
    public void testRosheenMeandererUsingAbility() {
        // Flying
        addCard(Zone.HAND, playerB, "Snapping Drake", 2); // {3}{U}
        // {T}: Add {C}{C}{C}{C}. Spend this mana only on costs that contain {X}.
        addCard(Zone.BATTLEFIELD, playerB, "Rosheen Meanderer", 1);
        // {X}, {T}: Untap X target lands.
        addCard(Zone.BATTLEFIELD, playerB, "Candelabra of Tawnos", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Snapping Drake");

        activateManaAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{T}: Add {C}{C}{C}{C}");

        activateAbility(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "{X}, {T}: Untap");
        setChoice(playerB, "X=4");
        addTarget(playerB, "Island");
        addTarget(playerB, "Island");
        addTarget(playerB, "Island");
        addTarget(playerB, "Island");

        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Snapping Drake");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTappedCount("Island", true, 4);
        assertTappedCount("Rosheen Meanderer", true, 1);
        assertTappedCount("Candelabra of Tawnos", true, 1);

        assertPermanentCount(playerB, "Snapping Drake", 2);
    }

    /**
     * I've found a bit of a problem with colorless costs. I've been unable to
     * pay colorless costs with lands conditionally tapping for 2 colorless i.e
     * shrine of forsaken gods and eldrazi temple ,including if I float the
     * mana. Seperately but on a related note, if you float at least one
     * colorless mana you can pay all colorless costs with floated generic mana.
     */
    @Test
    public void testPayColorlessWithConditionalMana() {
        // {T}: Add {C}.
        // {T}: Add {C}{C}. Spend this mana only to cast colorless spells. Activate this ability only if you control seven or more lands.
        addCard(Zone.BATTLEFIELD, playerA, "Shrine of the Forsaken Gods", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 8);
        // When you cast Kozilek, the Great Distortion, if you have fewer than seven cards in hand, draw cards equal to the difference.
        // Menace
        // Discard a card with converted mana cost X: Counter target spell with converted mana cost X.
        addCard(Zone.HAND, playerA, "Kozilek, the Great Distortion", 1); // {8}{C}{C}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}{C}");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kozilek, the Great Distortion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Kozilek, the Great Distortion", 1);
    }

    @Test
    public void CultivatorDroneColorlessSpell() {
        // Devoid
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator Drone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Target creature gets +3/-3 until end of turn.
        addCard(Zone.HAND, playerA, "Spatial Contortion", 1); // {1}{C}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spatial Contortion", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Spatial Contortion", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void CultivatorDroneColorlessAbility() {
        // Devoid
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator Drone", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 1);
        // Untap Endbringer during each other player's untap step.
        // {T}: Endbringer deals 1 damage to any target.
        // {C}, {T}: Target creature can't attack or block this turn.
        // {C}{C}, {T}: Draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Endbringer", 1); // {1}{C}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{C}{C},");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, 1);
    }

    @Test
    public void CultivatorDroneColorlessCost() {
        // Devoid
        // {T}: Add {C}. Spend this mana only to cast a colorless spell, activate an ability of a colorless permanent, or pay a cost that contains {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Cultivator Drone", 1);
        // Devoid (This card has no color.)
        // Flying
        // When Gravity Negator attacks, you may pay {C}. If you do, another target creature gains flying until end of turn. ({C} represents colorless mana)
        addCard(Zone.BATTLEFIELD, playerA, "Gravity Negator", 1); // 2/3

        attack(1, playerA, "Gravity Negator");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Gravity Negator", true);
        assertAbility(playerA, "Cultivator Drone", FlyingAbility.getInstance(), true);

        assertLife(playerB, 18);
    }
}
