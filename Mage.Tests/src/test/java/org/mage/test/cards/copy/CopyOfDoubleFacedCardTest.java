package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * A permanent that becomes a copy of a double-faced card (in a graveyard, in exile, in a library) copies its front
 * face
 * <p>
 * 712.8a
 * While a double-faced card is outside the game or in a zone other than the battlefield or stack, it has only the
 * characteristics of its front face.
 *
 * @author JayDi85
 */
public class CopyOfDoubleFacedCardTest extends CardTestPlayerBase {

    @Test
    public void test_EntersAsCopyOfCardInGraveyard_SuperiorSpiderMan() {
        // Mind Swap — You may have Superior Spider-Man enter as a copy of any creature card in a graveyard, except
        // his name is Superior Spider-Man and he's a 4/4 Spider Human Hero in addition to his other types. When you
        // do, exile that card.
        addCard(Zone.HAND, playerA, "Superior Spider-Man"); // {2}{U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Superior Spider-Man");
        setChoice(playerA, true); // use Mind Swap
        setChoice(playerA, "Delver of Secrets");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Superior Spider-Man", 4, 4);
        assertSubtype("Superior Spider-Man", SubType.WIZARD);
        assertExileCount(playerA, "Delver of Secrets", 1);
    }

    @Test
    public void test_EntersAsCopyOfCardPutIntoGraveyard_ActivatedSleeper() {
        // You may have this creature enter as a copy of any creature card in a graveyard that was put there from the
        // battlefield this turn, except it's a Phyrexian in addition to its other types.
        addCard(Zone.HAND, playerA, "Activated Sleeper"); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Delver of Secrets");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Activated Sleeper");
        setChoice(playerA, true); // use copy
        setChoice(playerA, "Delver of Secrets");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
        assertSubtype("Delver of Secrets", SubType.PHYREXIAN);
    }

    @Test
    public void test_BecomesCopyOfCardInGraveyard_DimirDoppelganger() {
        // {1}{U}{B}: Exile target creature card from a graveyard. This creature becomes a copy of that card, except
        // it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Dimir Doppelganger");
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 3);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{U}{B}: Exile", "Delver of Secrets");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
    }

    @Test
    public void test_BecomesCopyOfCardPutIntoGraveyard_LazavDimirMastermind() {
        // Whenever a creature card is put into an opponent's graveyard from anywhere, you may have Lazav become a copy
        // of that card, except its name is Lazav, Dimir Mastermind, it's legendary in addition to its other types,
        // and it has hexproof and this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, Dimir Mastermind");
        addCard(Zone.HAND, playerA, "Thought Scour"); // {U}, target player mills two cards, draw a card
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.LIBRARY, playerB, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard
        addCard(Zone.LIBRARY, playerB, "Island");
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Thought Scour", playerB);
        setChoice(playerA, true); // become a copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Lazav, Dimir Mastermind", 1, 1);
        assertSubtype("Lazav, Dimir Mastermind", SubType.WIZARD);
    }

    @Test
    public void test_BecomesCopyOfCardInGraveyard_LazavTheMultifarious() {
        // {X}: Lazav becomes a copy of target creature card in your graveyard with mana value X, except its name is
        // Lazav, the Multifarious, it's legendary in addition to its other types, and it has this ability.
        addCard(Zone.BATTLEFIELD, playerA, "Lazav, the Multifarious");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard, {U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}: ");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Delver of Secrets"); // target is chosen after X (mana value X)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Lazav, the Multifarious", 1, 1);
        assertSubtype("Lazav, the Multifarious", SubType.WIZARD);
    }

    @Test
    public void test_BecomesCopyOfCardInGraveyard_LikenessLooter() {
        // {X}: This creature becomes a copy of target creature card in your graveyard with mana value X, except it
        // has flying and this ability. Activate only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Likeness Looter");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard, {U}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}: ");
        setChoice(playerA, "X=1");
        addTarget(playerA, "Delver of Secrets"); // target is chosen after X (mana value X)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
    }

    @Test
    public void test_TokenBecomesCopyOfExiledCard_KayaSpiritsJustice() {
        // Whenever one or more creatures you control and/or creature cards in your graveyard are put into exile, you
        // may choose a creature card from among them. Until end of turn, target token you control becomes a copy of
        // it, except it has flying.
        addCard(Zone.BATTLEFIELD, playerA, "Kaya, Spirits' Justice");
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}, two 1/1 Soldier tokens
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Bojuka Bog"); // land, when enters, exile target player's graveyard
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerA); // exile own graveyard
        addTarget(playerA, "Soldier Token"); // Kaya: token to become a copy
        addTarget(playerA, "Delver of Secrets"); // Kaya: card to copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Soldier Token", 1);
        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
    }

    @Test
    public void test_TransformsIntoCopyOfExiledCard_LudevicNecrogenius() {
        // As this creature transforms into Olag, Ludevic's Hubris, it becomes a copy of a creature card exiled with
        // it, except its name is Olag, Ludevic's Hubris, it's 4/4, and it's a legendary blue and black Zombie in
        // addition to its other colors and types. Put a number of +1/+1 counters on Olag equal to the number of
        // creature cards exiled with it.
        addCard(Zone.BATTLEFIELD, playerA, "Ludevic, Necrogenius"); // {X}{U}{U}{B}{B}, exile X creature cards: transform
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 1 + 4);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}{U}{U}{B}{B}");
        setChoice(playerA, "X=1"); // mana
        setChoice(playerA, "X=1"); // creature cards to exile
        setChoice(playerA, "Delver of Secrets"); // exile for the cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Olag, Ludevic's Hubris", 4 + 1, 4 + 1);
        assertSubtype("Olag, Ludevic's Hubris", SubType.WIZARD);
    }

    @Test
    public void test_BecomesCopyOfImprintedCard_Dermotaxi() {
        // Imprint — As this Vehicle enters, exile a creature card from a graveyard.
        // Tap two untapped creatures you control: Until end of turn, this Vehicle becomes a copy of the exiled card,
        // except it's a Vehicle artifact in addition to its other types.
        addCard(Zone.HAND, playerA, "Dermotaxi"); // {2}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 2); // bear 2/2, to tap

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dermotaxi");
        setChoice(playerA, "Delver of Secrets"); // imprint
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tap two untapped creatures you control");
        setChoice(playerA, "Grizzly Bears", 2); // tap for the cost

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
        assertSubtype("Delver of Secrets", SubType.VEHICLE);
    }

    @Test
    public void test_CreaturesBecomeCopiesOfRevealedCard_DeceiverOfForm() {
        // At the beginning of combat on your turn, reveal the top card of your library. If a creature card is
        // revealed this way, you may have creatures you control other than this creature become copies of that card
        // until end of turn. You may put that card on the bottom of your library.
        addCard(Zone.BATTLEFIELD, playerA, "Deceiver of Form");
        addCard(Zone.LIBRARY, playerA, "Delver of Secrets"); // double-faced, front 1/1 Human Wizard
        skipInitShuffling();
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2

        // turn 1 beginning of combat
        setChoice(playerA, true); // creatures become copies
        setChoice(playerA, false); // keep the card on the top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertPowerToughness(playerA, "Delver of Secrets", 1, 1);
        assertSubtype("Delver of Secrets", SubType.WIZARD);
    }
}
