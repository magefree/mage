package org.mage.test.cards.abilities.keywords;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.TurnFaceUpAbility;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import mage.view.CardView;
import mage.view.GameView;
import mage.view.PermanentView;
import mage.view.PlayerView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.List;

/**
 * Most of the face down logic was tested in MorphTest, here are tests for disguise related only
 *
 * @author JayDi85
 */
public class DisguiseTest extends CardTestPlayerBase {

    @Test
    public void test_NormalPlay_ClientData_CostRulesVisible() {
        // it checks rules visible for face down cards, main logic:
        // - real face up abilities uses special cost;
        // - it must be hidden from opponent
        // - so it must be replaced in rules by non-cost versions (e.g. text only)

        String FACE_DOWN_SPELL = "with no text, no name, no subtypes";
        String FACE_DOWN_TRIGGER = "When ";
        String FACE_DOWN_FACE_UP = "down permanent face up";


        // {R}{W}
        // Disguise {R/W}{R/W} (You may cast this card face down for {3} as a 2/2 creature with ward {2}.
        // Turn it face up any time for its disguise cost.)
        // When Dog Walker is turned face up, create two tapped 1/1 white Dog creature tokens.
        // Ward {2}. <i>(Whenever it becomes the target of a spell or ability an opponent controls, counter it unless that player pays {2}.)
        addCard(Zone.HAND, playerA, "Dog Walker@dog");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 2);
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);


        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dog Walker", 0);

        // prepare face down
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dog Walker using Disguise");
        runCode("face up on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertEquals("stack, server - can't find spell", 1, currentGame.getStack().size());
            SpellAbility spellAbility = (SpellAbility) currentGame.getStack().getFirst().getStackAbility();
            Assert.assertEquals("stack, server - can't find spell", "Cast Dog Walker using Disguise", spellAbility.getName());
            CardView spellView = getGameView(playerA).getStack().values().stream().findFirst().orElse(null);
            Assert.assertNotNull("stack, client: can't find spell", spellView);

            // make sure rules visible
            assertRuleExist("client side, stack: face down spell - show", spellView.getRules(), FACE_DOWN_SPELL, true);
            assertRuleExist("client side, stack: face up - hide", spellView.getRules(), FACE_DOWN_FACE_UP, false);
            assertRuleExist("client side, stack: triggered ability - hide", spellView.getRules(), FACE_DOWN_TRIGGER, false);
        });
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after face down", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dog Walker", 0);
        checkPermanentCount("after face down", 1, PhaseStep.PRECOMBAT_MAIN, playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        runCode("after face down", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // server side
            Permanent permanent = currentGame.getBattlefield().getAllPermanents()
                    .stream()
                    .filter(Permanent::isDisguised)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("server side: can't find disguised permanent", permanent);
            Assert.assertEquals("server side: wrong name", EmptyNames.FACE_DOWN_CREATURE.toString(), permanent.getName());
            Assert.assertEquals("server side: wrong color", "", permanent.getColor(currentGame).toString());
            Assert.assertEquals("server side: wrong power", "2", permanent.getPower().toString());
            Assert.assertEquals("server side: wrong toughness", "2", permanent.getToughness().toString());

            // make sure real abilities exists
            // trigger
            Ability ability = permanent.getAbilities(currentGame).stream().filter(a -> a instanceof TurnedFaceUpSourceTriggeredAbility).findFirst().orElse(null);
            Assert.assertNotNull("server side: must have face up triggered ability", ability);
            Assert.assertFalse("server side: face up triggered ability must be hidden", ability.getRuleVisible());
            // face up
            ability = permanent.getAbilities(currentGame).stream().filter(a -> a instanceof TurnFaceUpAbility).findFirst().orElse(null);
            Assert.assertNotNull("server side: must have turn face up ability", ability);
            String foundRule = permanent.getRules(currentGame).stream().filter(r -> r.contains("{R/W}")).findFirst().orElse(null);
            // failed here? search BecomesFaceDownCreatureEffect and additionalAbilities
            Assert.assertNull("server side: turn face up ability with {R/W} cost must be replaced by text only without cost", foundRule);

            // client side - controller
            GameView gameView = getGameView(playerA);
            PermanentView permanentView = gameView.getMyPlayer().getBattlefield().values()
                    .stream()
                    .filter(PermanentView::isDisguised)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("client side - controller: can't find disguised permanent", permanentView);
            Assert.assertEquals("client side - controller: wrong name", "Disguise: Dog Walker", permanentView.getName());
            Assert.assertEquals("client side - controller: wrong color", "", permanentView.getColor().toString());
            Assert.assertEquals("client side - controller: wrong power", "2", permanentView.getPower());
            Assert.assertEquals("client side - controller: wrong toughness", "2", permanentView.getToughness());
            // make sure rules visible
            assertRuleExist("client side, controller: face down spell - show", permanentView.getRules(), FACE_DOWN_SPELL, true);
            assertRuleExist("client side, controller: face up - hide", permanentView.getRules(), FACE_DOWN_FACE_UP, false);
            assertRuleExist("client side, controller: triggered ability - hide", permanentView.getRules(), FACE_DOWN_TRIGGER, false);
            assertRuleExist("client side, controller: {R/W} cost hide", permanentView.getRules(), "{R/W}", false);

            // client side - opponent
            gameView = getGameView(playerB);
            PlayerView playerView = gameView.getPlayers()
                    .stream()
                    .filter(p -> p.getName().equals(playerA.getName()))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(playerView);
            permanentView = playerView.getBattlefield().values()
                    .stream()
                    .filter(PermanentView::isDisguised)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("client side - opponent: can't find disguised permanent", permanentView);
            Assert.assertEquals("client side - opponent: wrong name", "Disguise", permanentView.getName());
            Assert.assertEquals("client side - opponent: wrong color", "", permanentView.getColor().toString());
            Assert.assertEquals("client side - opponent: wrong power", "2", permanentView.getPower());
            Assert.assertEquals("client side - opponent: wrong toughness", "2", permanentView.getToughness());
            // make sure rules visible
            assertRuleExist("client side, opponent: face down spell - show", permanentView.getRules(), FACE_DOWN_SPELL, true);
            assertRuleExist("client side, opponent: face up - hide", permanentView.getRules(), FACE_DOWN_FACE_UP, false);
            assertRuleExist("client side, opponent: triggered ability - hide", permanentView.getRules(), FACE_DOWN_TRIGGER, false);
            assertRuleExist("client side, opponent: {R/W} cost hide", permanentView.getRules(), "{R/W}", false);
        });

        // make sure ward works too
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerB, "Lightning Bolt");
        addTarget(playerB, "@dog"); // target face down card by alias
        setChoice(playerB, true); // try ward pay but no mana, so bolt will be fizzled

        // do face up and generate tokens
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{R/W}{R/W}: Turn");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, playerA);
        checkPermanentCount("after face up", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dog Walker", 1);
        checkPermanentCount("after face up", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 0);
        checkPermanentCount("after face up", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Dog Token", 2);
        runCode("after face up", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent permanent = currentGame.getBattlefield().getAllPermanents()
                    .stream()
                    .filter(p -> p.getName().equals("Dog Walker"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull("server side: can't find normal permanent", permanent);
            Assert.assertEquals("server side: wrong name", "Dog Walker", permanent.getName());
            Assert.assertEquals("server side: wrong color", "WR", permanent.getColor(currentGame).toString());
            Assert.assertEquals("server side: wrong power", "3", permanent.getPower().toString());
            Assert.assertEquals("server side: wrong toughness", "1", permanent.getToughness().toString());
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    private void assertRuleExist(String info, List<String> rules, String searchPart, boolean mustExists) {
        String foundAbility = rules.stream().filter(r -> r.contains(searchPart)).findFirst().orElse(null);
        if (mustExists) {
            Assert.assertTrue(info, foundAbility != null);
        } else {
            Assert.assertFalse(info, foundAbility != null);
        }
    }

    @Test
    public void testCostReduction() {
        String chisel = "Dream Chisel"; // Face-down creature spells you cast cost {1} less to cast.
        String nightdrinker = "Nightdrinker Moroii";
        /* Nightdrinker Moroii {3}{B} Creature — Vampire
         * Flying
         * When Nightdrinker Moroii enters the battlefield, you lose 3 life.
         * Disguise {B}{B}
         */
        addCard(Zone.BATTLEFIELD, playerA, chisel);
        addCard(Zone.HAND, playerA, nightdrinker);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, nightdrinker + " using Disguise");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);
        assertLife(playerA, 20);
    }

}
