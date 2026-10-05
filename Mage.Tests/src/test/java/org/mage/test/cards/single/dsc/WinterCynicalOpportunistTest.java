package org.mage.test.cards.single.dsc;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.TargetCard;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestComputerPlayer;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author brahle
 */
public class WinterCynicalOpportunistTest extends CardTestPlayerBase {

    static class WinterTestPlayer extends TestPlayer {
        private final AtomicInteger permanentPromptCount = new AtomicInteger(0);

        public WinterTestPlayer(TestComputerPlayer computerPlayer) {
            super(computerPlayer);
        }

        public int getPermanentPromptCount() {
            return permanentPromptCount.get();
        }

        @Override
        public boolean choose(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
            if (target.getZone() == Zone.EXILED
                    && target.getFilter() != null
                    && target.getFilter().getMessage().contains("permanent card")) {
                permanentPromptCount.incrementAndGet();
                if (!getChoices().isEmpty()) {
                    String nextChoice = getChoices().get(0);
                    if (TestPlayer.CHOICE_SKIP.equals(nextChoice) || "[cancel]".equals(nextChoice)) {
                        getChoices().remove(0);
                        boolean isReq = target.isRequiredExplicitlySet()
                                ? target.isRequired()
                                : target.isRequired(source != null ? source.getSourceId() : null, game);
                        if (isReq) {
                            throw new AssertionError("Cannot cancel mandatory choice: target is required");
                        }
                        return false;
                    }
                }
            }
            return super.choose(outcome, cards, target, source, game);
        }
    }

    @Override
    protected TestPlayer createPlayer(String name, RangeOfInfluence rangeOfInfluence) {
        return new WinterTestPlayer(new TestComputerPlayer(name, rangeOfInfluence));
    }

    @Test
    public void testAttackMillsAndEndStepReanimatesWithFinalityCounter() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Swamp", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        // Graveyard has Creature, Artifact, Enchantment, Land = 4 types
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");   // Creature (2/2)
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");        // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");          // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Forest");          // Land

        attack(1, playerA, "Winter, Cynical Opportunist", playerB);

        setChoice(playerA, true); // Yes, exile cards with 4+ card types
        setChoice(playerA, "Grizzly Bears^Sol Ring^Rancor^Forest");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Finish selecting cards to exile
        setChoice(playerA, "Grizzly Bears"); // Choose permanent card to put onto battlefield

        checkPermanentCount("Grizzly Bears on battlefield at upkeep", 2, PhaseStep.UPKEEP, playerA, "Grizzly Bears", 1);
        checkPermanentCounters("Grizzly Bears enters with finality counter", 2, PhaseStep.UPKEEP, playerA, "Grizzly Bears", CounterType.FINALITY, 1);

        // Kill Grizzly Bears on turn 2: finality counter should exile it instead of putting it into graveyard
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        // Attacked -> milled 3 Swamps
        assertGraveyardCount(playerA, "Swamp", 3);
        // Other 3 exiled cards remain in exile, plus Grizzly Bears exiled when it died with a finality counter
        assertExileCount(playerA, "Sol Ring", 1);
        assertExileCount(playerA, "Rancor", 1);
        assertExileCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testReanimatedPermanentEntersWithFinalityCounter() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Graveyard has Creature, Artifact, Enchantment, Land = 4 types
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");   // Creature
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");        // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");          // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Forest");          // Land

        setChoice(playerA, true); // Yes, exile cards with 4+ card types
        setChoice(playerA, "Grizzly Bears^Sol Ring^Rancor^Forest");
        setChoice(playerA, "Grizzly Bears"); // Mandatory choice: permanent card to put onto battlefield

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.FINALITY, 1);
        assertExileCount(playerA, "Sol Ring", 1);
        assertExileCount(playerA, "Rancor", 1);
        assertExileCount(playerA, "Forest", 1);
    }

    @Test
    public void testChoosePermanentIsMandatory() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Graveyard has Creature, Artifact, Enchantment, Instant = 4 types, plus 1 extra card
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");   // Creature
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");        // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");          // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Lightning Bolt");  // Instant
        addCard(Zone.GRAVEYARD, playerA, "Swamp");           // Land (extra card left in graveyard)

        setChoice(playerA, true); // Yes, exile cards with 4+ card types
        setChoice(playerA, "Grizzly Bears^Sol Ring^Rancor^Lightning Bolt");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Finish selecting cards to exile
        setChoice(playerA, "Sol Ring"); // Mandatory choice between Grizzly Bears, Sol Ring, Rancor

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Sol Ring", 1);
        assertCounterCount(playerA, "Sol Ring", CounterType.FINALITY, 1);
        assertExileCount(playerA, "Grizzly Bears", 1);
        assertExileCount(playerA, "Rancor", 1);
        assertExileCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Swamp", 1);
    }

    @Test
    public void testCannotCancelMandatoryPermanentChoice() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Graveyard has 4 types (Creature, Artifact, Enchantment, Land)
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");   // Creature
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");        // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");          // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Forest");          // Land

        setChoice(playerA, true);
        setChoice(playerA, "Grizzly Bears^Sol Ring^Rancor^Forest");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Attempt to cancel mandatory permanent choice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);

        try {
            execute();
            Assert.fail("Expected cancellation of mandatory permanent choice to fail");
        } catch (AssertionError e) {
            Assert.assertTrue("Error should be due to mandatory choice: " + e.getMessage(),
                    e.getMessage().contains("Cannot cancel mandatory choice"));
        }
        Assert.assertEquals("Expected permanent choice prompt to have occurred",
                1, ((WinterTestPlayer) playerA).getPermanentPromptCount());
    }

    @Test
    public void testDivertedExileCannotBeReanimated() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Divert Grizzly Bears from exile to hand
        Ability ability = new SimpleStaticAbility(new DivertFromExileReplacementEffect("Grizzly Bears"));
        addCustomCardWithAbility("DivertEffect", playerA, ability);

        // Graveyard has Creature (permanent), Kindred + Instant (non-permanent), Sorcery (non-permanent) = 4 types
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Tarfire");        // Kindred Instant
        addCard(Zone.GRAVEYARD, playerA, "Divination");     // Sorcery

        setChoice(playerA, true); // Yes, exile cards with 4+ card types
        setChoice(playerA, "Grizzly Bears^Tarfire^Divination");
        // No permanent choice prompt because Grizzly Bears was diverted to hand, leaving 0 permanents in exile

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerA, "Tarfire", 1);
        assertExileCount(playerA, "Divination", 1);
        Assert.assertEquals("No permanent-choice prompt should occur when no permanent reached exile",
                0, ((WinterTestPlayer) playerA).getPermanentPromptCount());
    }

    @Test
    public void testMultipleTypesPerCardSatisfiesDelirium() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Seat of the Synod is Artifact Land (2 types)
        // Courser of Kruphix is Enchantment Creature (2 types)
        // Together they have 4 card types among only 2 cards
        addCard(Zone.GRAVEYARD, playerA, "Seat of the Synod");
        addCard(Zone.GRAVEYARD, playerA, "Courser of Kruphix");

        setChoice(playerA, true); // Yes, exile cards with 4+ card types
        setChoice(playerA, "Seat of the Synod^Courser of Kruphix");
        setChoice(playerA, "Courser of Kruphix"); // Choose permanent to return

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Courser of Kruphix", 1);
        assertCounterCount(playerA, "Courser of Kruphix", CounterType.FINALITY, 1);
        assertExileCount(playerA, "Seat of the Synod", 1);
        assertGraveyardCount(playerA, "Courser of Kruphix", 0);
        assertGraveyardCount(playerA, "Seat of the Synod", 0);
    }

    @Test
    public void testInsufficientSelectedTypesDoesNotExileOrReanimate() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Graveyard contains 4 types (Creature, Artifact, Enchantment, Land)
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");      // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");        // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Forest");        // Land

        setChoice(playerA, true); // Yes, choose to exile
        // Select only 3 cards (3 types) instead of 4
        setChoice(playerA, "Grizzly Bears^Sol Ring^Rancor");
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Stop selecting with only 3 types

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        // Condition not met: nothing should be exiled or reanimated
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Rancor", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testDecliningAbilityLeavesGraveyardUnchanged() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");

        // Graveyard contains 4 types (delirium available)
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears"); // Creature
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");      // Artifact
        addCard(Zone.GRAVEYARD, playerA, "Rancor");        // Enchantment
        addCard(Zone.GRAVEYARD, playerA, "Forest");        // Land

        setChoice(playerA, false); // Choose "No" to the exile prompt

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Sol Ring", 1);
        assertGraveyardCount(playerA, "Rancor", 1);
        assertGraveyardCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertExileCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testFewerThanFourCardTypesInGraveyardDoesNotReanimate() {
        addCard(Zone.BATTLEFIELD, playerA, "Winter, Cynical Opportunist");
        // Only 3 card types in graveyard (Creature, Artifact, Land)
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Sol Ring");
        addCard(Zone.GRAVEYARD, playerA, "Forest");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 0);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
    }
}

class DivertFromExileReplacementEffect extends ReplacementEffectImpl {

    private final String cardName;

    DivertFromExileReplacementEffect(String cardName) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.cardName = cardName;
    }

    private DivertFromExileReplacementEffect(final DivertFromExileReplacementEffect effect) {
        super(effect);
        this.cardName = effect.cardName;
    }

    @Override
    public DivertFromExileReplacementEffect copy() {
        return new DivertFromExileReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() != Zone.EXILED) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        return card != null && card.getName().equals(cardName);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.HAND);
        return false;
    }
}
