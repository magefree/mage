
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UnearthTest extends CardTestPlayerBase {

    /**
     * Hellspark Elemental (and probably other cards with the unearth ability) -
     * If I unearth the elemental, attack, and then go to the end of my turn
     * both the "sacrifice" and "exile" clauses will trigger and the game will
     * ask me which one I want to put on the stack first. If I choose
     * "sacrifice" first and "exile" second, all good, the exile part resolves
     * first and the elemental is exiled, the sacrifice part does nothing
     * afterwards. But if I choose "exile" first and "sacrifice" second then the
     * elemental will be sacrificed and placed on my graveyard and after that
     * the "exile" resolves but does nothing, as I'm guessing it can't "find"
     * the elemental anymore and so it stays in my graveyard, despite the fact
     * that because I use its unearth ability it should always be exiled once
     * leaving the battlefield no matter what. The bug should be easy to
     * reproduce if following the order I mention above (click the exile part,
     * so the sacrifice goes on the top of the stack).
     */
    @Test
    public void testUnearthAttackExile() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // 3/1 - Trample, haste
        // At the beginning of the end step, sacrifice Hellspark Elemental.
        // Unearth {1}{R} ({1}{R}: Return this card from your graveyard to the battlefield.
        // It gains haste. Exile it at the beginning of the next end step or if it would
        // leave the battlefield. Unearth only as a sorcery.)
        addCard(Zone.GRAVEYARD, playerA, "Hellspark Elemental", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");

        attack(1, playerA, "Hellspark Elemental");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertGraveyardCount(playerA, "Hellspark Elemental", 0);
        assertLife(playerB, 17);

        assertPermanentCount(playerA, "Hellspark Elemental", 0);
        assertExileCount("Hellspark Elemental", 1);
    }

    /**
     * Reported bug: Cards with unearth (e.g. Undead Leotau) are currently
     * bugged. When you bring a creature back from the graveyard with unearth,
     * it [should] get exiled at end of turn normally, [but instead] a copy of
     * the card stays on the battlefield under your control permanently.
     */
    @Test
    public void testUndeadLeotau() {

        //{R}: Undead Leotau gets +1/-1 until end of turn.
        // Unearth {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Undead Leotau", 1); // 3/4
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        attack(1, playerA, "Undead Leotau");

        setStopAt(2, PhaseStep.UNTAP);
        execute();

        assertGraveyardCount(playerA, "Undead Leotau", 0);
        assertLife(playerB, 17);

        assertPermanentCount(playerA, "Undead Leotau", 0);
        assertExileCount("Undead Leotau", 1);
    }

    /**
     * At start of the end phase, creatures phased out by Teferi's Veil still
     * exiled by unearth if they were put to battlefield by unearth.<br>
     *
     * http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/294911-teferis-veil-and-unearth
     */
    @Test
    public void testUnearthWithPhasing() {
        // Whenever a creature you control attacks, it phases out at end of combat.
        addCard(Zone.BATTLEFIELD, playerA, "Teferi's Veil", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        // Unearth {B} ({B}: Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)
        addCard(Zone.GRAVEYARD, playerA, "Dregscape Zombie", 1); // 2/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        attack(1, playerA, "Dregscape Zombie");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Dregscape Zombie", 0);
        assertLife(playerB, 18);
        assertExileCount("Dregscape Zombie", 0);

        assertPermanentCount(playerA, "Dregscape Zombie", 1);

    }

    /**
     * "If it would leave the battlefield, exile it instead of putting it anywhere else."
     */
    @Test
    public void testUnearthedCreatureThatDiesIsExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 2);
        addCard(Zone.GRAVEYARD, playerA, "Dregscape Zombie"); // 2/1, Unearth {B}
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dregscape Zombie");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dregscape Zombie", 0);
        assertExileCount(playerA, "Dregscape Zombie", 1);
    }

    /**
     * An unearth that returns nothing exiles nothing, not even the same card put onto the battlefield
     * later by something else.
     */
    @Test
    public void testUnearthThatReturnsNothing() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 4);
        addCard(Zone.BATTLEFIELD, playerB, "Grafdigger's Cage"); // Creature cards in graveyards can't enter the battlefield.
        addCard(Zone.GRAVEYARD, playerA, "Dregscape Zombie"); // 2/1, Unearth {B}
        addCard(Zone.HAND, playerA, "Shatter");
        addCard(Zone.HAND, playerA, "Reanimate");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkGraveyardCount("Cage keeps it in the graveyard", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dregscape Zombie", 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Shatter", "Grafdigger's Cage", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Dregscape Zombie", true);

        checkPermanentCount("not exiled at the end step", 3, PhaseStep.UPKEEP, playerA, "Dregscape Zombie", 1);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dregscape Zombie");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dregscape Zombie", 1);
        assertExileCount(playerA, "Dregscape Zombie", 0);
    }

    /**
     * 400.7: a card that leaves the graveyard and comes back in response is a new object, so unearth has
     * nothing to return.
     */
    @Test
    public void testUnearthedCardLeftAndReturnedInResponse() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 6);
        addCard(Zone.GRAVEYARD, playerA, "Dregscape Zombie"); // 2/1, Unearth {B}
        addCard(Zone.HAND, playerA, "Makeshift Mannequin"); // {3}{B} instant: return target creature card from your graveyard to the battlefield
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Makeshift Mannequin", "Dregscape Zombie", "Unearth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dregscape Zombie", "Unearth");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Dregscape Zombie", 0);
        assertGraveyardCount(playerA, "Dregscape Zombie", 1);
    }

    /**
     * 400.7j: "it" is only the permanent this unearth returned. When something else returns the card in
     * response, unearth returns nothing, so neither the end step exile nor the leave-the-battlefield
     * replacement touches that creature.
     */
    @Test
    public void testCardReturnedByOtherEffectInResponseIsNotExiled() {
        addCard(Zone.BATTLEFIELD, playerA, "Badlands", 6);
        addCard(Zone.GRAVEYARD, playerA, "Dregscape Zombie"); // 2/1, Unearth {B}
        addCard(Zone.HAND, playerA, "Makeshift Mannequin"); // {3}{B} instant: return target creature card from your graveyard to the battlefield
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unearth");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Makeshift Mannequin", "Dregscape Zombie", "Unearth");

        checkPermanentCount("not exiled at the end step", 3, PhaseStep.UPKEEP, playerA, "Dregscape Zombie", 1);
        // targeting it makes the Mannequin'd creature sacrifice itself
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Dregscape Zombie");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dregscape Zombie", 1);
        assertExileCount(playerA, "Dregscape Zombie", 0);
    }

}
