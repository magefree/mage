
package org.mage.test.cards.triggers;

import mage.cards.Card;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.Objects;
import java.util.Optional;

/**
 * @author LevelX2
 */
public class ReturnToHandEffectsTest extends CardTestPlayerBase {

    /**
     * Enduring Renewal doesn't return creatures to hand put into graveyard from
     * the battlefield It happened with Enduring Renewal in the battlefield
     * while feeding Ornithopter to Grinding Station
     */
    
    /*  jeffwadsworth:  I tested this scenario in the game and it worked perfectly.  The test suite is not reliable in this case.
    @Test
    public void testEnduringRenewal() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Play with your hand revealed.
        // If you would draw a card, reveal the top card of your library instead. If it's a creature card, put it into your graveyard. Otherwise, draw a card.
        // Whenever a creature is put into your graveyard from the battlefield, return it to your hand.
        addCard(Zone.BATTLEFIELD, playerA, "Enduring Renewal");

        // {T}, Sacrifice an artifact: Target player puts the top three cards of their library into their graveyard.
        // Whenever an artifact enters the battlefield, you may untap Grinding Station.
        addCard(Zone.BATTLEFIELD, playerA, "Grinding Station", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);
        
        addCard(Zone.LIBRARY, playerB, "Island", 3);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice an artifact", playerB);
        setChoice(playerA, "Ornithopter");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerB, 3);
        assertHandCount(playerA, "Ornithopter", 1);

    }
*/
    @Test
    public void testStormfrontRidersTriggerForToken() {
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        // Flying
        // When Stormfront Riders enters the battlefield, return two creatures you control to their owner's hand.
        // Whenever Stormfront Riders or another creature is returned to your hand from the battlefield, put a 1/1 white Soldier creature token onto the battlefield.
        addCard(Zone.HAND, playerA, "Stormfront Riders"); // {4}{W}
        // Buyback {4} (You may pay an additional {4} as you cast this spell. If you do, put this card into your hand as it resolves.)
        // Create a 1/1 black Rat creature token.
        addCard(Zone.HAND, playerA, "Lab Rats"); // {B}

        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Boomerang", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stormfront Riders", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lab Rats");
        setChoice(playerA, false);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Boomerang", "Rat Token");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Stormfront Riders", 1);
        assertHandCount(playerA, "Silvercoat Lion", 2);
        assertGraveyardCount(playerA, "Lab Rats", 1);
        assertGraveyardCount(playerB, "Boomerang", 1);

        assertPermanentCount(playerA, "Soldier Token", 3);
        assertPermanentCount(playerA, "Rat Token", 0);

    }

    /**
     * Assumes there is exactly 1 permanent with the cardName, and checks its permanent zcc and card zcc
     */
    private void checkZCCPermanent(String info, Player player, Game game, String cardName, int permanentZCC, int cardZCC, boolean checkMDFC, int leftZCC, int rightZCC) {
        Optional<Permanent> optPermanent = game
                .getBattlefield()
                .getAllActivePermanents()
                .stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getName().equals(cardName))
                .findFirst();
        if (!optPermanent.isPresent()) {
            Assert.fail(info + " — no permanent named \"" + cardName + "\" found on battlefield");
        }
        Permanent permanent = optPermanent.get();
        Assert.assertEquals(info + " — permanent zcc for \"" + cardName + "\"", permanentZCC, permanent.getZoneChangeCounter(game));
        Card card = game.getCard(permanent.getId()).getMainCard();
        if (card == null) {
            Assert.fail(info + " — missing card for permanent \"" + cardName + "\"");
        }
        Assert.assertEquals(info + " — card zcc for \"" + cardName + "\"", cardZCC, card.getZoneChangeCounter(game));
        if (checkMDFC) {
            Assert.assertEquals(info + " — left card zcc for \"" + cardName + "\"", leftZCC, ((ModalDoubleFacedCard) card).getLeftHalfCard().getZoneChangeCounter(game));
            Assert.assertEquals(info + " — right card zcc for \"" + cardName + "\"", rightZCC, ((ModalDoubleFacedCard) card).getRightHalfCard().getZoneChangeCounter(game));
        }
    }

    private void checkZCCNormalPermanent(String info, Player player, Game game, String cardName, int permanentZCC, int cardZCC) {
        checkZCCPermanent(info, player, game, cardName, permanentZCC, cardZCC, false, cardZCC, cardZCC);
    }

    private void checkZCCMDFCPermanent(String info, Player player, Game game, String cardName, int permanentZCC, int mainCardZCC, int leftCardZCC, int rightCardZCC) {
        checkZCCPermanent(info, player, game, cardName, permanentZCC, mainCardZCC, true, leftCardZCC, rightCardZCC);
    }

    /**
     * Assumes there is exactly 1 card in player's graveyard with the cardName, and checks its card zcc
     */
    private void checkZCCCardInGraveyard(String info, Player player, Game game, String cardName, int cardZCC) {
        Optional<Card> optCard = game
                .getPlayer(player.getId())
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getName().equals(cardName))
                .findFirst();
        if (!optCard.isPresent()) {
            Assert.fail(info + " — no card named \"" + cardName + "\" found in graveyard");
        }
        Card card = optCard.get();
        Assert.assertEquals(info + " — card zcc for \"" + cardName + "\"", cardZCC, card.getZoneChangeCounter(game));
    }

    /**
     * Assumes there is exactly 1 card in player's hand with the cardName, and checks its card zcc
     */
    private void checkZCCCardInHand(String info, Player player, Game game, String cardName, int cardZCC, boolean checkMDFC, int leftZCC, int rightZCC) {
        Optional<Card> optCard = game
                .getPlayer(player.getId())
                .getHand()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(p -> p.getName().equals(cardName))
                .findFirst();
        if (!optCard.isPresent()) {
            Assert.fail(info + " — no card named \"" + cardName + "\" found in hand");
        }
        Card card = optCard.get();
        Assert.assertEquals(info + " — card zcc for \"" + cardName + "\"", cardZCC, card.getZoneChangeCounter(game));
        if (checkMDFC) {
            Assert.assertEquals(info + " — left card zcc for \"" + cardName + "\"", leftZCC, ((ModalDoubleFacedCard) card).getLeftHalfCard().getZoneChangeCounter(game));
            Assert.assertEquals(info + " — right card zcc for \"" + cardName + "\"", rightZCC, ((ModalDoubleFacedCard) card).getRightHalfCard().getZoneChangeCounter(game));
        }
    }

    private void checkZCCNormalCardInHand(String info, Player player, Game game, String cardName, int cardZCC) {
        checkZCCCardInHand(info, player, game, cardName, cardZCC, false, cardZCC, cardZCC);
    }

    private void checkZCCMDFCCardInHand(String info, Player player, Game game, String cardName, int mainZCC, int leftZCC, int rightZCC) {
        checkZCCCardInHand(info, player, game, cardName, mainZCC, true, leftZCC, rightZCC);
    }

    @Test
    public void testZendikon() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Mountain");

        runCode("1: check zcc permanent", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCNormalPermanent(info, player, game, "Mountain", 2, 2));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Mountain");

        runCode("2: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCNormalCardInHand(info, player, game, "Mountain", 4));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Mountain", 0);
        assertHandCount(playerA, "Mountain", 1);
    }

    @Test
    public void testZendikonMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Tangled Florahedron");
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Vale");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Tangled Vale");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("1: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCPermanent(info, player, game, "Tangled Vale", 2, 1, 1, 2));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Tangled Vale");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("2: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCCardInHand(info, player, game, "Tangled Florahedron", 2, 2, 4));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Tangled Florahedron", 0);
        assertHandCount(playerA, "Tangled Florahedron", 1);
    }

    @Test
    public void testZendikonPathwayTop() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Riverglide Pathway");
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Riverglide Pathway");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Riverglide Pathway");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("1: check zcc pre disfigure", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCPermanent(info, player, game, "Riverglide Pathway", 2, 1, 2, 1));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Riverglide Pathway");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("2: check zcc post disfigure", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCCardInHand(info, player, game, "Riverglide Pathway", 2, 4, 2));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Riverglide Pathway", 0);
        assertHandCount(playerA, "Riverglide Pathway", 1);
    }

    @Test
    public void testZendikonPathwayBottom() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerA, "Riverglide Pathway");
        addCard(Zone.HAND, playerA, "Wind Zendikon");
        addCard(Zone.HAND, playerA, "Disfigure");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lavaglide Pathway");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wind Zendikon", "Lavaglide Pathway");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("1: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCPermanent(info, player, game, "Lavaglide Pathway", 2, 1, 1, 2));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Lavaglide Pathway");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("2: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCCardInHand(info, player, game, "Riverglide Pathway", 2, 2, 4));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Wind Zendikon", 1);
        assertGraveyardCount(playerA, "Riverglide Pathway", 0);
        assertHandCount(playerA, "Riverglide Pathway", 1);
    }

    @Test
    public void testDemonicVigor() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Dryad Sophisticate");
        addCard(Zone.HAND, playerA, "Demonic Vigor");
        addCard(Zone.HAND, playerA, "Disfigure");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Dryad Sophisticate");

        runCode("1: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCNormalPermanent(info, player, game, "Dryad Sophisticate", 3, 3));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Dryad Sophisticate");

        runCode("2: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCNormalCardInHand(info, player, game, "Dryad Sophisticate", 5));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Demonic Vigor", 1);
        assertGraveyardCount(playerA, "Dryad Sophisticate", 0);
        assertHandCount(playerA, "Dryad Sophisticate", 1);
    }

    @Test
    public void testDemonicVigorMDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.HAND, playerA, "Tangled Florahedron");
        addCard(Zone.HAND, playerA, "Demonic Vigor");
        addCard(Zone.HAND, playerA, "Disfigure");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Tangled Florahedron");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("1: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCPermanent(info, player, game, "Tangled Florahedron", 3, 2, 3, 2));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Tangled Florahedron");

        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("2: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCCardInHand(info, player, game, "Tangled Florahedron", 3, 5, 3));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Demonic Vigor", 1);
        assertGraveyardCount(playerA, "Tangled Florahedron", 0);
        assertHandCount(playerA, "Tangled Florahedron", 1);
    }

    @Test
    public void testDemonicVigorAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Dryad Sophisticate");
        addCard(Zone.HAND, playerA, "Demonic Vigor", 2);
        addCard(Zone.HAND, playerA, "Disfigure", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Dryad Sophisticate");

        runCode("1: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCNormalPermanent(info, player, game, "Dryad Sophisticate", 3, 3));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Dryad Sophisticate", true);
        runCode("2: check zcc card", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCNormalCardInHand(info, player, game, "Dryad Sophisticate", 5));

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Demonic Vigor", "Dryad Sophisticate");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        runCode("3: check zcc", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCNormalPermanent(info, player, game, "Dryad Sophisticate", 7, 7));
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Dryad Sophisticate", true);
        runCode("4: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCNormalCardInHand(info, player, game, "Dryad Sophisticate", 9));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 2);
        assertGraveyardCount(playerA, "Demonic Vigor", 2);
        assertGraveyardCount(playerA, "Dryad Sophisticate", 0);
        assertHandCount(playerA, "Dryad Sophisticate", 1);
    }

    @Test
    public void testDemonicVigorMDFCAgain() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, "Tangled Florahedron");
        addCard(Zone.HAND, playerA, "Demonic Vigor", 2);
        addCard(Zone.HAND, playerA, "Disfigure", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Tangled Florahedron");
        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("1: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCPermanent(info, player, game, "Tangled Florahedron", 3, 2, 3, 2));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Tangled Florahedron", true);
        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("2: check zcc card", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCCardInHand(info, player, game, "Tangled Florahedron", 3, 5, 3));

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Tangled Florahedron");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Demonic Vigor", "Tangled Florahedron", true);
        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("3: check zcc", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCPermanent(info, player, game, "Tangled Florahedron", 7, 4, 7, 4));
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disfigure", "Tangled Florahedron", true);
        // TODO: investigate why MDFC zcc moves separatedly.
        runCode("4: check zcc card", 1, PhaseStep.POSTCOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCMDFCCardInHand(info, player, game, "Tangled Florahedron", 5, 9, 5));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Disfigure", 2);
        assertGraveyardCount(playerA, "Demonic Vigor", 2);
        assertGraveyardCount(playerA, "Tangled Florahedron", 0);
        assertHandCount(playerA, "Tangled Florahedron", 1);
    }

    @Test
    public void testDemonicVigorZoneChange() {

        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);
        addCard(Zone.HAND, playerA, "Carrion Feeder");
        addCard(Zone.HAND, playerA, "Demonic Vigor");
        addCard(Zone.HAND, playerA, "Disfigure");
        addCard(Zone.HAND, playerA, "Makeshift Mannequin");
        addCard(Zone.HAND, playerA, "Coat with Venom"); // target for triggering the Makeshift Mannequin's sacrifice
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Carrion Feeder");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Demonic Vigor", "Carrion Feeder", true);
        runCode("1: check zcc", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                (String info, Player player, Game game) -> checkZCCNormalPermanent(info, player, game, "Carrion Feeder", 3, 3));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Disfigure", "Carrion Feeder");
        waitStackResolved(1, PhaseStep.BEGIN_COMBAT, 1);
        runCode("2: check graveyard zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCCardInGraveyard(info, player, game, "Carrion Feeder", 4));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Makeshift Mannequin", "Carrion Feeder");
        waitStackResolved(1, PhaseStep.BEGIN_COMBAT, 1);
        runCode("3: check zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCNormalPermanent(info, player, game, "Carrion Feeder", 5, 5));
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Coat with Venom", "Carrion Feeder", true);
        runCode("4: check graveyard zcc", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (String info, Player player, Game game) -> checkZCCCardInGraveyard(info, player, game, "Carrion Feeder", 6));

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        // Vigor tries to return the Carrion Feeder card with zcc 4, so 6 doesn't return.

        assertGraveyardCount(playerA, "Disfigure", 1);
        assertGraveyardCount(playerA, "Demonic Vigor", 1);
        assertGraveyardCount(playerA, "Makeshift Mannequin", 1);
        assertGraveyardCount(playerA, "Carrion Feeder", 1);
        assertPermanentCount(playerA, "Carrion Feeder", 0);
        assertHandCount(playerA, "Carrion Feeder", 0);
    }

}
