
package org.mage.test.mulligan;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.Modes;
import mage.abilities.TriggeredAbility;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.mana.ManaCost;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.basiclands.Forest;
import mage.cards.decks.Deck;
import mage.choices.Choice;
import mage.constants.Outcome;
import mage.constants.RangeOfInfluence;
import mage.game.Game;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.game.combat.CombatGroup;
import mage.game.draft.Draft;
import mage.game.match.Match;
import mage.game.mulligan.Mulligan;
import mage.game.mulligan.MulliganType;
import mage.game.permanent.Permanent;
import mage.game.tournament.Tournament;
import mage.players.Player;
import mage.players.PlayerImpl;
import mage.target.Target;
import mage.target.TargetAmount;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import static java.util.stream.Collectors.toList;
import static mage.constants.MultiplayerAttackOption.LEFT;
import static mage.constants.RangeOfInfluence.ONE;
import static mage.constants.Rarity.LAND;
import static org.junit.Assert.*;

public class MulliganTestBase {

    protected static Logger logger = Logger.getLogger(MulliganTestBase.class);

    static class MulliganScenarioTest {

        private final MulliganType mulliganType;
        private final int freeMulligans;
        private final List<Step> steps = new ArrayList<>();

        private PlayerProxy player1;

        public MulliganScenarioTest(MulliganType mulliganType, int freeMulligans) {
            this.mulliganType = mulliganType;
            this.freeMulligans = freeMulligans;
        }

        public void mulligan(MulliganStep step) {
            steps.add(step);
        }

        public void scry(ScryStep step) {
            steps.add(step);
        }

        public void discardBottom(DiscardBottomStep step) {
            steps.add(step);
        }

        public void run(Runnable callback) {
            Mulligan mulligan = mulliganType.getMulligan(freeMulligans);
            Game game = new TwoPlayerDuel(LEFT, ONE, mulligan, 20) {
                @Override
                public void fireStatusEvent(String message, boolean withTime) {
                    super.fireStatusEvent(message, withTime);
                }

                @Override
                protected void play(UUID nextPlayerId) {
                }
            };
            GameOptions options = new GameOptions();
            options.skipInitShuffling = true;
            game.setGameOptions(options);

            this.player1 = new PlayerProxy("p1", ONE);
            player1.setSteps(steps);
            Deck deck1 = generateDeck(player1.getId(), 40);
            game.loadCards(deck1.getCards(), player1.getId());
            game.addPlayer(player1, deck1);

            PlayerProxy player2 = new PlayerProxy("p2", ONE);
            Deck deck2 = generateDeck(player2.getId(), 40);
            game.loadCards(deck2.getCards(), player2.getId());
            game.addPlayer(player2, deck2);

            game.start(player1.getId());

            player1.assertStepsComplete();
            callback.run();
        }

        public Set<UUID> getHand() {
            checkState(player1 != null);
            return unmodifiableSet(player1.getHand());
        }

        public List<UUID> getLibrary() {
            checkState(player1 != null);
            return unmodifiableList(player1.getLibrary().getCardList());
        }

        public List<UUID> getNTopOfLibrary(int n) {
            checkState(player1 != null);
            List<UUID> library = getLibrary();
            checkArgument(n <= library.size());
            return unmodifiableList(library.subList(0, n));
        }

        public List<UUID> getNBottomOfLibrary(int n) {
            checkState(player1 != null);
            List<UUID> library = getLibrary();
            checkArgument(n <= library.size());
            return unmodifiableList(library.subList(library.size() - n, library.size()));
        }


        public List<UUID> getLibraryRangeSize(int start, int n) {
            return getLibraryRangeIndex(start, start + n);
        }

        public List<UUID> getLibraryRangeIndex(int start, int end) {
            checkArgument(end >= start);
            checkState(player1 != null);
            List<UUID> library = getLibrary();
            checkArgument(end <= library.size());
            return unmodifiableList(library.subList(start, end));
        }

        public UUID getLibraryTopCard() {
            return getOnlyElement(getNTopOfLibrary(1));
        }

        public void assertSizes(int handSize, int librarySize) {
            assertEquals("hand size", handSize, getHand().size());
            assertEquals("library size", librarySize, getLibrary().size());
        }

    }

    public static Deck generateDeck(UUID playerId, int count) {
        Deck deck = new Deck();
        Stream.generate(() -> new Forest(playerId, new CardSetInfo("Forest", "TEST", "1", LAND)))
                .limit(count)
                .forEach(deck.getCards()::add);
        return deck;
    }

    interface Step {}

    interface MulliganStep extends Step {
        boolean mulligan();
    }

    interface ScryStep extends Step {
        boolean scry();
    }

    interface DiscardBottomStep extends Step {
        List<UUID> discardBottom(int count);
    }

    static class PlayerProxy extends StubPlayer {

        private List<Step> steps = null;
        private int current = 0;

        public PlayerProxy(String name, RangeOfInfluence range) {
            super(name, range);
        }

        @Override
        public boolean chooseMulligan(Game game) {
            if (steps == null) {
                return super.chooseMulligan(game);
            }
            if (current >= steps.size()) {
                fail("Tried to mulligan without a test step.");
            }
            Step step = steps.get(current++);
            assertTrue("Expected mulligan step.",
                    MulliganStep.class.isAssignableFrom(step.getClass()));
            return ((MulliganStep) step).mulligan();
        }

        @Override
        public boolean chooseScry(Game game, UUID cardId) {
            if (steps == null) {
                return super.chooseScry(game, cardId);
            }
            if (current >= steps.size()) {
                fail("Tried to scry without a test step.");
            }
            Step step = steps.get(current++);
            assertTrue("Expected scry step.",
                    ScryStep.class.isAssignableFrom(step.getClass()));
            return ((ScryStep) step).scry();
        }

        @Override
        public List<UUID> chooseDiscardBottom(Game game, int count, List<UUID> cardIds) {
            if (steps == null) {
                return super.chooseDiscardBottom(game, count, cardIds);
            }
            if (current >= steps.size()) {
                fail("Tried to discard without a test step.");
            }
            Step step = steps.get(current++);
            assertTrue("Expected discard bottom step.",
                    DiscardBottomStep.class.isAssignableFrom(step.getClass()));
            return ((DiscardBottomStep) step).discardBottom(count);
        }

        public void setSteps(List<Step> steps) {
            this.steps = steps;
        }

        public void assertStepsComplete() {
            assertEquals(steps.size(), current);
        }

    }

    static class StubPlayer extends PlayerImpl implements Player {

        public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
            if (target instanceof TargetPlayer) {
                for (Player player : game.getPlayers().values()) {
                    if (player.getId().equals(getId()) && target.canTarget(getId(), game)) {
                        target.add(player.getId(), game);
                        return true;
                    }
                }
            }
            return false;
        }

        public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
            cards.getCards(game).stream().map(MageItem::getId).forEach(cardId -> target.add(cardId, game));
            return true;
        }

        @Override
        public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
            if ("cards to PUT on the BOTTOM of your library (Discard for Mulligan)".equals(target.getFilter().getMessage())) {
                chooseDiscardBottom(game, target.getMinNumberOfTargets(), cards.getCards(game)
                        .stream().map(MageItem::getId).collect(toList())).forEach(cardId -> target.add(cardId, game));
            } else {
                UUID cardId = getOnlyElement(cards.getCards(game)).getId();
                if (chooseScry(game, cardId)) {
                    target.add(cardId, game);
                    return true;
                }
            }
            return false;
        }

        public List<UUID> chooseDiscardBottom(Game game, int count, List<UUID> cardIds) {
            return cardIds.subList(0, count);
        }

        public boolean chooseScry(Game game, UUID cardId) {
            return false;
        }

        @Override
        public void shuffleLibrary(Ability source, Game game) {

        }

        public StubPlayer(String name, RangeOfInfluence range) {
            super(name, range);
        }

        @Override
        public void abort() {

        }

        @Override
        public void skip() {

        }

        @Override
        public Player copy() {
            return null;
        }

        @Override
        public boolean priority(Game game) {
            return false;
        }

        @Override
        public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
            return false;
        }

        @Override
        public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
            return false;
        }

        @Override
        public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
            return false;
        }

        @Override
        public boolean chooseMulligan(Game game) {
            return false;
        }

        @Override
        public boolean chooseUse(Outcome outcome, String message, Ability source, Game game) {
            return false;
        }

        @Override
        public boolean chooseUse(Outcome outcome, String message, String secondMessage, String trueText, String falseText, Ability source, Game game) {
            return false;
        }

        @Override
        public boolean choose(Outcome outcome, Choice choice, Game game) {
            return false;
        }

        @Override
        public boolean choosePile(Outcome outcome, String message, List<? extends Card> pile1, List<? extends Card> pile2, Game game) {
            return false;
        }

        @Override
        public boolean playMana(Ability ability, ManaCost unpaid, String promptText, Game game) {
            return false;
        }

        @Override
        public int announceXMana(int min, int max, String message, Game game, Ability ability) {
            return 0;
        }

        @Override
        public int announceXCost(int min, int max, String message, Game game, Ability ability, VariableCost variableCost) {
            return 0;
        }

        @Override
        public int chooseReplacementEffect(Map<String, String> abilityMap, Game game) {
            return 0;
        }

        @Override
        public TriggeredAbility chooseTriggeredAbility(List<TriggeredAbility> abilities, Game game) {
            return null;
        }

        @Override
        public Mode chooseMode(Modes modes, Ability source, Game game) {
            return null;
        }

        @Override
        public void selectAttackers(Game game, UUID attackingPlayerId) {

        }

        @Override
        public void selectBlockers(Game game, UUID defendingPlayerId) {

        }

        @Override
        public UUID chooseAttackerOrder(List<Permanent> attacker, Game game) {
            return null;
        }

        @Override
        public UUID chooseBlockerOrder(List<Permanent> blockers, CombatGroup combatGroup, List<UUID> blockerOrder, Game game) {
            return null;
        }

        @Override
        public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {

        }

        @Override
        public int getAmount(int min, int max, String message, Game game) {
            return 0;
        }

        @Override
        public void sideboard(Match match, Deck deck) {

        }

        @Override
        public void construct(Tournament tournament, Deck deck) {

        }

        @Override
        public void pickCard(List<Card> cards, Deck deck, Draft draft) {

        }

    }

}