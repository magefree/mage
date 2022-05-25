package org.mage.test.cards.abilities.keywords;

import mage.abilities.keyword.HasteAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author BetaSteward
 */
public class MadnessTest extends CardTestPlayerBase {

    /**
     * 702.34. Madness 702.34a Madness is a keyword that represents two
     * abilities. The first is a static ability that functions while the card
     * with madness is in a player's hand. The second is a triggered ability
     * that functions when the first ability is applied. “Madness [cost]” means
     * “If a player would discard this card, that player discards it, but may
     * exile it instead of putting it into their graveyard” and “When this
     * card is exiled this way, its owner may cast it by paying [cost] rather
     * than paying its mana cost. If that player doesn't, they put this
     * card into their graveyard.” 702.34b Casting a spell using its
     * madness ability follows the rules for paying alternative costs in rules
     * 601.2b and 601.2e–g.
     * <p>
     * Arrogant Wurm 3GG Creature -- Wurm 4/4 Trample Madness {2}{G} (If you
     * discard this card, you may cast it for its madness cost instead of
     * putting it into your graveyard.)
     * <p>
     * Raven's Crime B Sorcery Target player discards a card. Retrace (You may
     * cast this card from your graveyard by discarding a land card in addition
     * to paying its other costs.)
     */
    @Test
    public void testMadness() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // Madness {2}{G} (If you discard this card, discard it into exile. When you do, cast it for its madness cost or put it into your graveyard.)
        addCard(Zone.HAND, playerA, "Arrogant Wurm");
        //
        // Target player discards a card.
        addCard(Zone.HAND, playerA, "Raven's Crime"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, true); // use madness triggered ability

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Arrogant Wurm", 1);
        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertHandCount(playerA, 0);

    }

    @Test
    public void testNoMadness() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Arrogant Wurm");
        addCard(Zone.HAND, playerA, "Raven's Crime");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raven's Crime", playerA);
        setChoice(playerA, false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Arrogant Wurm", 0);
        assertGraveyardCount(playerA, "Raven's Crime", 1);
        assertGraveyardCount(playerA, "Arrogant Wurm", 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void testFalkenrathGorger() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        // Each Vampire creature card you own that isn't on the battlefield has madness. Its madness cost is equal to its mana cost.
        addCard(Zone.BATTLEFIELD, playerA, "Falkenrath Gorger", 1);

        // Sacrifice a creature: Vampire Aristocrat gets +2/+2 until end of turn.
        addCard(Zone.HAND, playerA, "Vampire Aristocrat"); // {2}{B}

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        // Target player discards two cards. If you cast this spell during your main phase, that player discards four cards instead.
        addCard(Zone.HAND, playerB, "Haunting Hymn");

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Haunting Hymn", playerA);
        setChoice(playerA, true); // Can't Vampirefor madness cost

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Vampire Aristocrat", 1);
        assertGraveyardCount(playerA, 0);

        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Haunting Hymn", 1);
    }

    @Test
    public void testAvacynsJudgment() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);

        // Madness {X}{R}
        // Avacyn's Judgment deals 2 damage divided as you choose among any number of target creatures and/or players.
        // If Avacyn's Judgment's madness cost was paid, it deals X damage divided as you choose among those creatures and/or players instead.
        addCard(Zone.HAND, playerA, "Avacyn's Judgment", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 6);
        // Target player discards two cards. If you cast this spell during your main phase, that player discards four cards instead.
        addCard(Zone.HAND, playerB, "Haunting Hymn");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Haunting Hymn", playerA);
        setChoice(playerA, true); // use madness triggered ability
        setChoice(playerA, "X=4");
        addTargetAmount(playerA, "Pillarfield Ox", 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerB, "Haunting Hymn", 1);
        assertGraveyardCount(playerA, "Avacyn's Judgment", 1);
        assertGraveyardCount(playerB, "Pillarfield Ox", 1);
    }

    /**
     * Falkenrath Gorger + Asylum Visitor (& probably any Vampire with printed
     * Madness) + Olivia, Mobilized for War - Haste part of the triggered effect
     * may not affect entering Vampire properly, please read further for more
     * details.
     * <p>
     * When I cast Falkenrath Gorger and then discarded Asylum Visitor with
     * Olivia, Mobilized for War 's triggered ability, two Madness pop-ups
     * appeared, I have used the first one, Asylum Visitor entered the
     * battlefield, Olivia triggered again and I used the ability to give Asylum
     * Visitor Haste and +1/+1. Then the second Madness trigger resolved for the
     * Visitor and I have cancelled it (by choosing "No" at first pop-up, but
     * from what I have tested, the choice at this point does not matter).
     * Asylum Visitor lost Haste and was both visually and functionally affected
     * by Summoning Sickness.
     * <p>
     * I was able to avoid this issue by cancelling the first Madness pop-up and
     * then using only the second one.
     */
    @Test
    public void testFalkenrathGorgerWithAsylumVisitor() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // Flying
        // Whenever another creature enters the battlefield under your control, you may discard a card. If you do, put a +1/+1 counter on that creature,
        // it gains haste until end of turn, and it becomes a Vampire in addition to its other types.
        addCard(Zone.BATTLEFIELD, playerA, "Olivia, Mobilized for War", 1);

        // Each Vampire creature card you own that isn't on the battlefield has madness. Its madness cost is equal to its mana cost.
        addCard(Zone.HAND, playerA, "Falkenrath Gorger", 1); // Creature Vampire 2/1 {R}
        // At the beginning of each player's upkeep, if that player has no cards in hand, you draw a card and you lose 1 life.
        // Madness {1}{B}
        addCard(Zone.HAND, playerA, "Asylum Visitor"); // Creature Vampire 3/1 {1}{B}
        addCard(Zone.HAND, playerA, "Forest");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Falkenrath Gorger");

        setChoice(playerA, true); // Discard a card and put a +1/+1 counter on that creature, it gains haste until end of turn, and it becomes a Vampire in addition to its other types?
        setChoice(playerA, "Asylum Visitor"); // Card to discard from Falkenrath entering by Olivia effect
        setChoice(playerA, "Asylum Visito"); // Madness {1}{B}
        setChoice(playerA, true); // use madness triggered ability
        setChoice(playerA, true); // Discard a card and put a +1/+1 counter on that creature, it gains haste until end of turn, and it becomes a Vampire in addition to its other types?
        setChoice(playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Falkenrath Gorger", 1);
        assertPermanentCount(playerA, "Asylum Visitor", 1);

        assertPowerToughness(playerA, "Falkenrath Gorger", 3, 2);
        assertAbility(playerA, "Falkenrath Gorger", HasteAbility.getInstance(), true);

        assertPowerToughness(playerA, "Asylum Visitor", 4, 2);
        assertAbility(playerA, "Asylum Visitor", HasteAbility.getInstance(), true);

        assertGraveyardCount(playerA, "Forest", 1);
    }
}
