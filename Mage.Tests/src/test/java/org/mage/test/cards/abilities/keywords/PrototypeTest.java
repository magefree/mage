package org.mage.test.cards.abilities.keywords;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.ComparisonType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.*;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author TheElk801
 */
public class PrototypeTest extends CardTestPlayerBase {

    private static final String automaton = "Blitz Automaton";
    private static final String automatonWithPrototype = "Blitz Automaton using Prototype";
    private static final String bolt = "Lightning Bolt";
    private static final String cloudshift = "Cloudshift";
    private static final String clone = "Clone";
    private static final String counterpart = "Cackling Counterpart";
    private static final String epiphany = "Sublime Epiphany";
    private static final String denied = "Access Denied";

    private void checkAutomaton(boolean prototyped) {
        checkAutomaton(prototyped, 1);
    }

    private void checkAutomaton(boolean prototyped, int count) {
        assertPermanentCount(playerA, automaton, count);
        for (Permanent permanent : currentGame.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT, playerA.getId(), currentGame
        )) {
            if (!permanent.getName().equals(automaton)) {
                continue;
            }
            Assert.assertTrue("Needs haste", permanent.getAbilities(currentGame).contains(HasteAbility.getInstance()));
            Assert.assertEquals("Power is wrong", prototyped ? 3 : 6, permanent.getPower().getValue());
            Assert.assertEquals("Toughness is wrong", prototyped ? 2 : 4, permanent.getToughness().getValue());
            Assert.assertTrue("Color is wrong", prototyped
                    ? permanent.getColor(currentGame).isRed()
                    : permanent.getColor(currentGame).isColorless()
            );
            Assert.assertEquals("Mana cost is wrong", prototyped ? "{2}{R}" : "{7}", permanent.getManaCost().getText());
            Assert.assertEquals("Mana value is wrong", prototyped ? 3 : 7, permanent.getManaValue());
        }
    }

    private void makeTester(Predicate<? super MageObject>... predicates) {
        FilterSpell filter = new FilterSpell();
        for (Predicate<? super MageObject> predicate : predicates) {
            filter.add(predicate);
        }
        addCustomCardWithAbility(
                "tester", playerA,
                new SpellCastControllerTriggeredAbility(
                        new GainLifeEffect(1), filter, false
                )
        );
    }

    @Test
    public void testNormal() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 7);
        addCard(Zone.HAND, playerA, automaton);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(false);
    }

    @Test
    public void testPrototype() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(true);
    }

    @Test
    public void testLeavesBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3 + 1);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, bolt);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bolt, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, automaton, 1);
        Card card = playerA
                .getGraveyard()
                .getCards(currentGame)
                .stream()
                .filter(c -> c.getName().equals(automaton))
                .findFirst()
                .orElse(null);
        Assert.assertTrue("Card should be colorless", card.getColor(currentGame).isColorless());
        Assert.assertEquals("Card should have 6 power", 6, card.getPower().getValue());
        Assert.assertEquals("Card should have 4 toughness", 4, card.getToughness().getValue());
    }

    @Test
    public void testBlink() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 3 + 1);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, cloudshift);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, cloudshift, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(false);
    }

    @Test
    public void testTriggerColorlessSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 7);
        addCard(Zone.HAND, playerA, automaton);

        makeTester(ColorlessPredicate.instance);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testTriggerRedSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);

        makeTester(new ColorPredicate(ObjectColor.RED));
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testTrigger64Spell() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 7);
        addCard(Zone.HAND, playerA, automaton);

        makeTester(
                new PowerPredicate(ComparisonType.EQUAL_TO, 6),
                new ToughnessPredicate(ComparisonType.EQUAL_TO, 4)
        );
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testTrigger32Spell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);

        makeTester(
                new PowerPredicate(ComparisonType.EQUAL_TO, 3),
                new ToughnessPredicate(ComparisonType.EQUAL_TO, 2)
        );
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testTrigger7MVSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 7);
        addCard(Zone.HAND, playerA, automaton);

        makeTester(new ManaValuePredicate(ComparisonType.EQUAL_TO, 7));
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testTrigger3MVSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);

        makeTester(new ManaValuePredicate(ComparisonType.EQUAL_TO, 3));
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
    }

    @Test
    public void testCloneRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 7 + 4);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, clone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, clone);
        setChoice(playerA, true); // yes to clone
        setChoice(playerA, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(false, 2);
    }

    @Test
    public void testClonePrototype() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 3 + 4);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, clone);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, clone);
        setChoice(playerA, true); // yes to clone
        setChoice(playerA, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(true, 2);
    }

    @Test
    public void testTokenCopyRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 7 + 3);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, counterpart);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, counterpart, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(false, 2);
    }

    @Test
    public void testTokenCopyPrototype() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 3 + 3);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, counterpart);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, counterpart, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(true, 2);
    }

    @Test
    public void testTokenCopyRegularLKI() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7 + 6);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, epiphany);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, epiphany);
        setModeChoice(playerA, "3"); // Return target nonland permanent to its owner's hand.
        setModeChoice(playerA, "4"); // Create a token that's a copy of target creature you control.
        addTarget(playerA, automaton);
        addTarget(playerA, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, automaton, 1);
        checkAutomaton(false, 1);
    }

    @Test
    public void testTokenCopyPrototypeLKI() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 3 + 6);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, epiphany);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, epiphany);
        setModeChoice(playerA, "3"); // Return target nonland permanent to its owner's hand.
        setModeChoice(playerA, "4"); // Create a token that's a copy of target creature you control.
        addTarget(playerA, automaton);
        addTarget(playerA, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertHandCount(playerA, automaton, 1);
        checkAutomaton(true, 1);
    }

    @Test
    public void testStackToughnessPrototyped() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Stern Scolding");
        // Counter target creature spell with power or toughness 2 or less.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Stern Scolding");
        addTarget(playerB, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, automaton, 1);
        checkAutomaton(true, 0);
    }

    @Test
    public void testStackColorPrototyped() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Douse");
        // {1}{U}: Counter target red spell.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{U}: Counter target red spell");
        addTarget(playerB, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, automaton, 1);
        checkAutomaton(true, 0);
    }

    @Test
    public void testStackManaValueRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 7);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerB, denied);
        // Counter target spell. Create X 1/1 colorless Thopter artifact creature tokens with flying, where X is that spell’s mana value.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, denied, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Thopter Token", 7);
        assertGraveyardCount(playerA, automaton, 1);
        checkAutomaton(false, 0);
    }

    @Test
    public void testStackManaValuePrototype() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 5);
        addCard(Zone.HAND, playerB, denied);
        // Counter target spell. Create X 1/1 colorless Thopter artifact creature tokens with flying, where X is that spell’s mana value.

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, denied, automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerB, "Thopter Token", 3);
        assertGraveyardCount(playerA, automaton, 1);
        checkAutomaton(true, 0);
    }

    @Test
    public void testManaValueWhenCasting() {
        String winnower = "Void Winnower"; // Your opponents can't cast spells with even mana values.
        String evenRegOddProto = "Fallaji Dragon Engine"; // {8} 5/5; {2}{R} 1/3
        String oddRegEvenProto = "Boulderbranch Golem"; // {7} 6/5; {3}{G} 3/3, ETB gain life equal to its power

        addCard(Zone.BATTLEFIELD, playerB, winnower);
        addCard(Zone.HAND, playerA, evenRegOddProto);
        addCard(Zone.HAND, playerA, oddRegEvenProto);
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 8);
        addCard(Zone.HAND, playerA, "Taiga");

        // checkPlayableAbility doesn't seem to detect Void Winnower's restriction in time (probably because it checks CAST_SPELL_LATE?)
        // but if you try to actually cast a spell with even mana value, it will correctly fail

        checkPlayableAbility("cast odd reg", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Boulderbranch", true);
        //checkPlayableAbility("cast even reg", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Fallaji", false);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Taiga");

        //checkPlayableAbility("cast even proto", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Boulderbranch Golem using Prototype", false);
        checkPlayableAbility("cast odd proto", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Cast Fallaji Dragon Engine using Prototype", true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, evenRegOddProto + " using Prototype");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, evenRegOddProto, 1, 3);
    }
    @Test
    public void testCopyOnStack() {
        addCard(Zone.BATTLEFIELD, playerA, "Volcanic Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 2);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, "Double Major");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Double Major", automaton);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(true, 2);
    }
    @Test
    public void testHumility() {
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 4+3+2);
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, "Humility");
        addCard(Zone.HAND, playerA, "Disenchant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Humility");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automatonWithPrototype);

        checkPT("Humility with Prototype", 1, PhaseStep.BEGIN_COMBAT, playerA, automaton, 1, 1);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Disenchant", "Humility");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        checkAutomaton(true);
    }

    /*
     * More tests suggested by Zerris:
     * DONE 1) Copy permanent on the stack: See Double
     * 2) Gain control of spell on the stack: Aethersnatch
     * 3) Check LKI if card immediately leaves the battlefield due to state-based actions:
     *      4x Flowstone Surge, Absolute Law, Electropotence, Drizzt Do'Urden, Flayer of the Hatebound in play.
     *      (Cast both Prototype and Normal, assert both have expected P/T on entering and leaving battlefield for all triggers;
     *      Absolute Law protects as expected against colors.
     *      Reanimate the card after having prototyped it, assert the reanimated copy has correct P/T and Absolute Law fails to protect.)
     * 4) Ensure Copy effects layer properly: Essence of the Wild in play
     * 5) Check other things becoming copies of it, particularly other prototype cards:
     *      Infinite Reflection on it, followed by casting a copy of a different prototype-able card (both Prototyped and Normal for each)
     * 6) Ensure Prototype is not treated as an ability while in play, but does remove the textbox: Dress Down with it in play
     * 7) Phasing: Slip Out the Back
     * 8) Alternate Cost: Fires of Invention (Cannot cast at all with fires on 3 lands, cannot cast prototyped even on 7)
     *      NOTE: This test is probably wrong, Prototype is apparently NOT an alternate cost! https://magic.wizards.com/en/news/feature/comprehensive-rules-changes
     * 9) Delayed copies of a clone copy: Progenitor Mimic
     * 14) Cast from zones other than the hand: Ensure that if you cast a card from exile (Gonti, Lord of Luxury) you can Prototype it
     *      (and use mana of any color) as expected, and the same for Graveyards (Chainer, Nightmare Adept)
     * 15) Yixlid Jailer + Chainer, Nightmare Adept - I believe you should be able to cast your card, but not Prototype it,
     *      because that decision is made before it goes on the stack (and thus leaves the graveyard).
     * 19) Ensure Prototype is preserved through type changes - Swift Reconfiguration + Bludgeon Brawl on a prototyped card
     *      (and attempt to equip to Master of Waves)
     * 20) Ensure colored mana in a Prototype cost is treated properly - can be paid for by Jegantha and Somberwald Sage,
     *      reduced by Morophon but not Ugin, the Ineffable
     * DONE 22) Cast it Prototyped while Humility is in play (it's still a 1/1)
     * 23) Jegantha can still be your companion with Depth Charge Colossus in your deck
     */

}
