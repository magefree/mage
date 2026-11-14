package org.mage.test.cards.cost.additional;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ExileFromGraveCostTest extends CardTestPlayerBase {

    @Test
    public void test_Spell_FromGraveyard_NotEnoughOtherCards_NotPlayable() {
        // As an additional cost to cast this spell, exile three creature cards from your graveyard.
        // Flying
        // You may cast Skaab Ruinator from your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Skaab Ruinator"); // {1}{U}{U}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Elite Vanguard");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // the spell is on the stack when its costs are paid, so it can't exile itself
        checkPlayableAbility("not playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Skaab Ruinator", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Skaab Ruinator", 1);
    }

    @Test
    public void test_Spell_FromGraveyard_EnoughOtherCards_ExilesThem() {
        // As an additional cost to cast this spell, exile three creature cards from your graveyard.
        // Flying
        // You may cast Skaab Ruinator from your graveyard.
        addCard(Zone.GRAVEYARD, playerA, "Skaab Ruinator"); // {1}{U}{U}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Elite Vanguard");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Skaab Ruinator", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Skaab Ruinator");
        setChoice(playerA, "Grizzly Bears^Elite Vanguard^Silvercoat Lion"); // exile cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Skaab Ruinator", 1);
        assertExileCount(playerA, 3);
    }

    @Test
    public void test_Spell_FromHand_CountsAllCardsInGraveyard() {
        // As an additional cost to cast this spell, exile three creature cards from your graveyard.
        // Flying
        // You may cast Skaab Ruinator from your graveyard.
        addCard(Zone.HAND, playerA, "Skaab Ruinator"); // {1}{U}{U}
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Elite Vanguard");
        addCard(Zone.GRAVEYARD, playerA, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Skaab Ruinator", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Skaab Ruinator");
        setChoice(playerA, "Grizzly Bears^Elite Vanguard^Silvercoat Lion"); // exile cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Skaab Ruinator", 1);
        assertExileCount(playerA, 3);
    }

    @Test
    public void test_ActivatedAbility_FromGraveyard_CanExileItself() {
        // You may sacrifice three artifacts rather than pay this spell's mana cost.
        // Exile three artifact cards from your graveyard: Return Salvage Titan from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Salvage Titan");
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter", 2);

        // an ability's costs are paid with its source still in the graveyard
        checkPlayableAbility("playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile three artifact cards", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile three artifact cards");
        setChoice(playerA, "Salvage Titan^Ornithopter^Ornithopter"); // exile cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, 3);
        assertHandCount(playerA, "Salvage Titan", 0);
    }
}
