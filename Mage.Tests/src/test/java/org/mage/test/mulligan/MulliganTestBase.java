package org.mage.test.mulligan;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.getOnlyElement;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;
import mage.cards.CardSetInfo;
import mage.cards.basiclands.Forest;
import mage.cards.decks.Deck;
import mage.cards.s.Squire;
import static mage.constants.MultiplayerAttackOption.LEFT;
import mage.constants.RangeOfInfluence;
import static mage.constants.RangeOfInfluence.ONE;
import static mage.constants.Rarity.LAND;
import mage.game.Game;
import mage.game.GameOptions;
import mage.game.TwoPlayerDuel;
import mage.game.mulligan.Mulligan;
import mage.game.mulligan.MulliganType;
import mage.players.StubPlayer;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

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
            Game game = new TwoPlayerDuel(LEFT, ONE, mulligan, 60, 20, 7) {
                @Override
                public void fireStatusEvent(String message, boolean withTime, boolean withTurnInfo) {
                    super.fireStatusEvent(message, withTime, withTurnInfo);
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
                .limit(count / 2 + (count & 1)) //If odd number of cards, add one extra forest
                .forEach(deck.getCards()::add);
        Stream.generate(() -> new Squire(playerId, new CardSetInfo("Squire", "TEST", "2", LAND)))
                .limit(count / 2)
                .forEach(deck.getCards()::add);
        return deck;
    }

    interface Step {
    }

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

}