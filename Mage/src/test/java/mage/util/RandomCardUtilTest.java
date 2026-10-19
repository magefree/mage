package mage.util;

import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.repository.CardInfo;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class RandomCardUtilTest {

    @Test
    public void testNoCandidatesProducesNoCard() {
        assertNull(RandomCardUtil.createRandomCreature(Collections.emptyList()));
    }

    @Test
    public void testFailedConstructorsAreNotRetriedOrRemovedFromPool() {
        Info first = new Info(null);
        Info second = new Info(null);
        List<CardInfo> candidates = Collections.unmodifiableList(Arrays.asList(first, second));
        assertNull(RandomCardUtil.createRandomCreature(candidates));
        assertEquals(1, first.attempts);
        assertEquals(1, second.attempts);
        assertEquals(2, candidates.size());
    }

    @Test
    public void testFailedConstructorDoesNotPreventSelectingWorkingCard() {
        Info broken = new Info(null);
        Card expected = new TestCard();
        Info working = new Info(expected);
        List<CardInfo> candidates = Collections.unmodifiableList(Arrays.asList(working, broken));
        RandomUtil.setSeed(0); // The first nextInt(2) selects the broken entry.
        assertSame(expected, RandomCardUtil.createRandomCreature(candidates));
        assertEquals(1, working.attempts);
        assertEquals(1, broken.attempts);
        assertEquals(2, candidates.size());
    }

    private static class Info extends CardInfo {

        private final Card card;
        private int attempts;

        private Info(Card card) {
            this.card = card;
        }

        @Override
        public Card createCard() {
            attempts++;
            return card;
        }
    }

    private static class TestCard extends CardImpl {

        private TestCard() {
            super(UUID.randomUUID(), "Test creature");
        }

        private TestCard(TestCard card) {
            super(card);
        }

        @Override
        public TestCard copy() {
            return new TestCard(this);
        }
    }
}
