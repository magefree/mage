package org.mage.test.cards.cost.additional;

import mage.abilities.Ability;
import mage.abilities.condition.common.CollectedEvidenceCondition;
import mage.abilities.keyword.CollectEvidenceAbility;
import mage.cards.Card;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.stack.StackObject;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class CollectEvidenceTest extends CardTestPlayerBase {

    private static final String ferox = "Axebane Ferox";
    private static final String murder = "Murder";
    private static final String elemental = "Earth Elemental";
    private static final String giant = "Hill Giant";
    private static final String ogre = "Gray Ogre";
    private static final String piker = "Goblin Piker";
    private static final String raiders = "Mons's Goblin Raiders";
    private static final String effigy = "Fuming Effigy";
    private static final String sprite = "Crimestopper Sprite";
    private static final String monitor = "Surveillance Monitor";
    private static final String unraveler = "Conspiracy Unraveler";
    private static final String bite = "Bite Down on Crime";

    @Test
    public void testNoPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertPermanentCount(playerB, ferox, 1);
    }

    @Test
    public void testPayWith5() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, elemental);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, elemental);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, elemental, 1);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testPayWith411() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, ogre);
        addCard(Zone.GRAVEYARD, playerA, raiders);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, ogre);
        setChoice(playerA, raiders);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, ogre, 1);
        assertExileCount(playerA, raiders, 1);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testPayWith4() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, giant);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, giant, 1);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testPayWith31() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, ogre);
        addCard(Zone.GRAVEYARD, playerA, raiders);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, ogre);
        setChoice(playerA, raiders);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, ogre, 1);
        assertExileCount(playerA, raiders, 1);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testPayWith22() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, piker, 2);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, piker, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, piker, 2);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testPayWith211() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, piker);
        addCard(Zone.GRAVEYARD, playerA, raiders, 2);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, piker);
        setChoice(playerA, raiders, 2);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, piker, 1);
        assertExileCount(playerA, raiders, 2);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testPayWith1111() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, raiders, 4);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, raiders, 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, raiders, 4);
        assertPermanentCount(playerB, ferox, 0);
    }

    @Test
    public void testFumingEffigy() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, effigy);
        addCard(Zone.HAND, playerA, murder);
        addCard(Zone.GRAVEYARD, playerA, raiders, 4);
        addCard(Zone.BATTLEFIELD, playerB, ferox);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, murder, ferox);
        setChoice(playerA, true);
        setChoice(playerA, raiders, 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, murder, 1);
        assertExileCount(playerA, raiders, 4);
        assertPermanentCount(playerB, ferox, 0);
        assertLife(playerB, 20 - 1);
    }

    @Test
    public void testSpriteNoPay() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, sprite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sprite);
        addTarget(playerA, sprite);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(sprite, true);
        assertCounterCount(sprite, CounterType.STUN, 0);
    }

    @Test
    public void testSpritePay() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, sprite);
        addCard(Zone.GRAVEYARD, playerA, ogre, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sprite);
        setChoice(playerA, true);
        setChoice(playerA, ogre, 2);
        addTarget(playerA, sprite);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped(sprite, true);
        assertCounterCount(sprite, CounterType.STUN, 1);
    }

    @Test
    public void testMonitorTrigger() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, monitor);
        addCard(Zone.GRAVEYARD, playerA, giant);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, monitor);
        setChoice(playerA, true);
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thopter Token", 1);
    }

    @Test
    public void testMonitorTriggerTwice() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4 + 3);
        addCard(Zone.HAND, playerA, monitor);
        addCard(Zone.HAND, playerA, sprite);
        addCard(Zone.GRAVEYARD, playerA, giant);
        addCard(Zone.GRAVEYARD, playerA, ogre, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, monitor);
        setChoice(playerA, true);
        setChoice(playerA, giant);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Thopter Token", 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, sprite);
        setChoice(playerA, true);
        setChoice(playerA, ogre, 2);
        addTarget(playerA, sprite);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Thopter Token", 2);
        assertTapped(sprite, true);
        assertCounterCount(sprite, CounterType.STUN, 1);
    }

    @Test
    public void testConspiracyUnraveler() {
        addCard(Zone.BATTLEFIELD, playerA, unraveler);
        addCard(Zone.HAND, playerA, "Colossal Dreadmaw");
        addCard(Zone.GRAVEYARD, playerA, ogre, 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Colossal Dreadmaw");
        setChoice(playerA, true); // use alternative cast from unraveler
        setChoice(playerA, ogre, 4); // pay for collect evidence

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(ogre, 4);
        assertPermanentCount(playerA, "Colossal Dreadmaw", 1);
    }

    @Test
    public void testNonDirectConditionalOnStack() {
        // evidence conditional must work on stack from non spell abilities too
        // cost tags related bug: https://github.com/magefree/mage/issues/12522

        // As an additional cost to cast this spell, you may collect evidence 6. This spell costs {2} less to cast if evidence was collected.
        // Target creature you control gets +2/+0 until end of turn.
        // It deals damage equal to its power to target creature you don't control.
        addCard(Zone.HAND, playerA, bite); // {3}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Augmenting Automaton");
        addCard(Zone.GRAVEYARD, playerA, ogre, 2); // {2}{R}

        // before
        runCode("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Card card = playerA.getHand().getCards(game).stream().filter(c -> c.getName().equals(bite)).findFirst().orElse(null);
            Assert.assertNotNull(card);
            Ability ability = card.getAbilities(game).stream().filter(a -> a instanceof CollectEvidenceAbility).findFirst().orElse(null);
            Assert.assertNotNull(ability);
            Assert.assertFalse("Evidence must not be collected before usage", CollectedEvidenceCondition.instance.apply(game, ability));
        });

        // on cast
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bite);
        setChoice(playerA, true); // use alternative cast from bite
        addTarget(playerA, "Grizzly Bears"); // boost
        addTarget(playerA, "Augmenting Automaton"); // damage target
        setChoice(playerA, ogre, 2); // pay for collect evidence
        runCode("on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            StackObject object = game.getStack().peekFirst();
            Assert.assertNotNull(object);
            Ability ability = object.getAbilities().stream().filter(a -> a instanceof CollectEvidenceAbility).findFirst().orElse(null);
            Assert.assertNotNull(ability);
            Assert.assertTrue("Evidence must be collected on stack", CollectedEvidenceCondition.instance.apply(game, ability));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(ogre, 2);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Augmenting Automaton", 1);
    }
}
