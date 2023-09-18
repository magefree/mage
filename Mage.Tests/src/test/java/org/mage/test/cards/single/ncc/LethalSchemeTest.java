package org.mage.test.cards.single.ncc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Susucre
 */
public class LethalSchemeTest extends CardTestPlayerBase {

    /*
     * Lethal Scheme {2}{B}{B}
     * Instant
     *
     * Convoke
     * Destroy target creature or planeswalker. Each creature that convoked Lethal Scheme connives.
     */
    private static final String scheme = "Lethal Scheme";

    private static final String vanguard = "Elite Vanguard"; // vanilla 2/1
    private static final String bear = "Grizzly Bears"; // vanilla 2/2
    private static final String ogre = "Gray Ogre"; // vanilla 2/2
    private static final String mino = "Felhide Minotaur"; // vanilla 2/3

    private static final String blade = "Doom Blade"; // instant {1}{B} destroy target non-black creature.
    /*
     * Act of Aggression {3}{R/P}{R/P}
     * Instant
     *
     * Gain control of target creature an opponent controls until end of turn.
     * Untap that creature. It gains haste until end of turn.
     */
    private static final String aggression = "Act of Aggression";

    private static final String swamp = "Swamp";
    private static final String island = "Island";
    private static final String mountain = "Mountain";

    @Test
    public void LethalSchemeNoConvoke() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Black"); // pay 3
        setChoice(playerA, "Black"); // pay 4

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, 0);
        assertGraveyardCount(playerA, 1);
    }

    @Test
    public void LethalSchemeConvokeOneConniveLandFromHand() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, mountain,1);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, island,1);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);

        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Black"); // pay 3
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 4 as convoke

        // Choose to discard the Island for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, island);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 0);
        assertHandCount(playerA, 1);

        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, island, 1);
        assertHandCount(playerA, island, 0);
        assertGraveyardCount(playerA, mountain, 0);
        assertHandCount(playerA, mountain, 1);

        assertPowerToughness(playerA, bear, 2, 2);
    }

    @Test
    public void LethalSchemeConvokeOneConniveLandFromLib() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, mountain,1);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, island);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1, true);

        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent("Grizzly Bears", playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Black"); // pay 3
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 4 as convoke

        // Choose to discard the Mountain for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, mountain);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 0);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, island, 0);
        assertHandCount(playerA, island, 1);
        assertGraveyardCount(playerA, mountain, 1);
        assertHandCount(playerA, mountain, 0);
        assertPowerToughness(playerA, bear, 2, 2);
    }


    @Test
    public void LethalSchemeConvokeOneConniveNonLandFromHand() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, ogre,1);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, mino,1);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);

        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1); // unfortunate target.

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Black"); // pay 3
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 4 as convoke

        // Choose to discard the "Gray Ogre" for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, ogre);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 0);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, ogre, 1);
        assertHandCount(playerA, ogre, 0);
        assertGraveyardCount(playerA, mino, 0);
        assertHandCount(playerA, mino, 1);
        assertPowerToughness(playerA,bear, 3, 3);
    }

    @Test
    public void LethalSchemeConvokeOneConniveNonLandFromLib() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, ogre,1);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, mino,1);
        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);

        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Black"); // pay 3
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 4 as convoke

        // Choose to discard the "Felhide Minotaur" for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, mino);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 0);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, ogre, 0);
        assertHandCount(playerA, ogre, 1);
        assertGraveyardCount(playerA, mino, 1);
        assertHandCount(playerA, mino, 0);
        assertPowerToughness(playerA, bear, 3, 3);
    }

    @Test
    public void LethalSchemeConvokeTwoConniveMixed() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, ogre,1);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, blade,1);
        addCard(Zone.LIBRARY, playerA, island,1);

        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.BATTLEFIELD, playerA, mino, 1);

        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1); // unfortunate target.

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear, playerA);
        Permanent pMino = getPermanent(mino, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 3 as convoke
        setChoice(playerA, "Convoke");
        addTarget(playerA, mino); // pay 4 as convoke

        // Choose to discard the "Gray Ogre" for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, ogre);
        // Choose to discard the "Island" for the "Felhide Minotaur" connive choice.
        setChoice(playerA, pMino.getIdName());
        setChoice(playerA, island);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 0);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, 3);
        assertGraveyardCount(playerA, ogre, 1);
        assertHandCount(playerA, ogre, 0);
        assertGraveyardCount(playerA, blade, 0);
        assertHandCount(playerA, blade, 1);
        assertGraveyardCount(playerA, island, 1);
        assertHandCount(playerA, island, 0);

        assertPowerToughness(playerA, bear, 3, 3);
        assertPowerToughness(playerA, mino, 2, 3);
    }

    @Test
    public void LethalSchemeConvokeTwoConniveMixedOtherOrder() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, ogre,1);
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, blade,1);
        addCard(Zone.LIBRARY, playerA, island,1);

        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.BATTLEFIELD, playerA, mino, 1);

        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear, playerA);
        Permanent pMino = getPermanent(mino, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 3 as convoke
        setChoice(playerA, "Convoke");
        addTarget(playerA, mino); // pay 4 as convoke

        // Choose to discard the "Gray Ogre" for the "Felhide Minotaur" connive choice.
        setChoice(playerA, pMino.getIdName());
        setChoice(playerA, ogre);
        // Choose to discard the "Island" for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, island);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, 0);

        assertHandCount(playerA, 1);
        assertGraveyardCount(playerA, 3);
        assertGraveyardCount(playerA, ogre, 1);
        assertHandCount(playerA, ogre, 0);
        assertGraveyardCount(playerA, blade, 0);
        assertHandCount(playerA, blade, 1);
        assertGraveyardCount(playerA, island, 1);
        assertHandCount(playerA, island, 0);

        assertPowerToughness(playerA, bear, 2, 2);
        assertPowerToughness(playerA, mino, 3, 4);
    }

    /**
     * This test's purpose is to check the behavior if the control
     * of one of the convoking creature change between the cast
     * and the resolve of "Lethal Scheme".
     *
     * It is assumed that the correct behavior is for each player
     * in APNAP order, to connive for each of their controlled
     * creatures.
     */
    @Test
    public void LethalSchemeConvokeTwoWithControlChange() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, island, 1);
        addCard(Zone.HAND, playerB, aggression, 1);
        addCard(Zone.HAND, playerB, island, 1);

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, blade,1);
        addCard(Zone.LIBRARY, playerB, island,1);

        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);
        addCard(Zone.BATTLEFIELD, playerA, mino, 1);

        addCard(Zone.BATTLEFIELD, playerB, mountain, 5);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear);
        Permanent pMino = getPermanent(mino, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 3 as convoke
        setChoice(playerA, "Convoke");
        addTarget(playerA, mino); // pay 4 as convoke

        // player B takes control of the "Grizzly Bears" in response
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, aggression, bear, scheme, StackClause.WHILE_ON_STACK);
        setChoice(playerB, false); // not paying phyrexian mana
        setChoice(playerB, false); // not paying phyrexian mana

        // Player A choose to discard the "Doom Blade" for the "Felhide Minotaur" connive choice.
        setChoice(playerA, pMino.getIdName());
        setChoice(playerA, blade);
        // Player B choose to discard the "Island" for the "Grizzly Bears" connive choice.
        setChoice(playerB, pBear.getIdName());
        setChoice(playerB, island);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 2); // Lethal Scheme + Doom Blade
        assertGraveyardCount(playerB, 3); // Act of Aggression + Elite Vanguard + Island

        assertGraveyardCount(playerA, blade, 1);
        assertGraveyardCount(playerB, island, 1);

        assertPowerToughness(playerB, bear, 2, 2);
        assertPowerToughness(playerA, mino, 3, 4);
    }


    /**
     * This test's purpose is to check the behavior a convoking creature
     * leaves the battlefield before "Lethal Scheme"'s resolution.
     *
     * The player last controlling the creature should still be able to connive.
     */
    @Test
    public void LethalSchemeConvokeOneThatGetsKilled() {
        addCard(Zone.HAND, playerA, scheme, 1);
        addCard(Zone.HAND, playerA, island, 1);
        addCard(Zone.HAND, playerB, blade, 1);

        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);
        addCard(Zone.LIBRARY, playerA, ogre,1);

        addCard(Zone.BATTLEFIELD, playerA, swamp, 4);
        addCard(Zone.BATTLEFIELD, playerA, bear, 1);

        addCard(Zone.BATTLEFIELD, playerB, swamp, 2);
        addCard(Zone.BATTLEFIELD, playerB, vanguard, 1);

        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();
        Permanent pBear = getPermanent(bear, playerA);

        // use special action to pay (need disabled auto-payment and prepared mana pool)
        disableManaAutoPayment(playerA);
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}: Add {B}", 4);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, scheme, vanguard);
        setChoice(playerA, "Black"); // pay 1
        setChoice(playerA, "Black"); // pay 2
        setChoice(playerA, "Black"); // pay 3
        setChoice(playerA, "Convoke");
        addTarget(playerA, bear); // pay 4 as convoke

        // player B destroys the "Grizzly Bears" in response
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, blade, bear, scheme, StackClause.WHILE_ON_STACK);

        // Player A choose to discard the "Gray Ogre" for the "Grizzly Bears" connive choice.
        setChoice(playerA, pBear.getIdName());
        setChoice(playerA, ogre);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, 3); // Grizzly Bears + Lethal Scheme + Gray Ogre

        assertGraveyardCount(playerA, ogre, 1);
        assertGraveyardCount(playerA, bear, 1);
    }
}
