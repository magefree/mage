package mage.cards.mock;

import mage.cards.CardGraphicInfo;
import mage.cards.CardSetInfo;
import mage.cards.FrameStyle;
import mage.cards.RoomCard;
import mage.cards.SplitCard;
import mage.cards.repository.CardInfo;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import org.junit.Test;

import static org.junit.Assert.*;

public class MockSplitCardTest {

    @Test
    public void roomsKeepPrintingImageMetadata() {
        for (String number : new String[]{"118", "339"}) {
            assertImageMetadata(new TestRoomCard(new CardSetInfo(
                    "Test Room Left // Test Room Right", "DSK", number, Rarity.RARE,
                    new CardGraphicInfo(FrameStyle.M15_NORMAL, true))));
        }
    }

    @Test
    public void ordinarySplitCardsKeepBothImageNamingModes() {
        for (boolean variousArt : new boolean[]{false, true}) {
            assertImageMetadata(new TestSplitCard(new CardSetInfo(
                    "Test Split Left // Test Split Right", "TST", "123", Rarity.UNCOMMON,
                    new CardGraphicInfo(FrameStyle.RETRO, variousArt))));
        }
    }

    private void assertImageMetadata(SplitCard original) {
        CardInfo info = new CardInfo(original);
        MockSplitCard mock = (MockSplitCard) info.createMockCard();
        for (SplitCard card : new SplitCard[]{mock, mock.copy()}) {
            assertEquals(original.getExpansionSetCode(), card.getExpansionSetCode());
            assertEquals(original.getCardNumber(), card.getCardNumber());
            assertEquals(original.getUsesVariousArt(), card.getUsesVariousArt());
            assertEquals(original.getFrameStyle(), card.getFrameStyle());
            assertEquals(original.getUsesVariousArt(), card.getLeftHalfCard().getUsesVariousArt());
            assertEquals(original.getUsesVariousArt(), card.getRightHalfCard().getUsesVariousArt());
        }
    }

    private static class TestRoomCard extends RoomCard {
        TestRoomCard(CardSetInfo setInfo) {
            super(null, setInfo, "{2}{B}", "{3}{B}{B}");
        }

        private TestRoomCard(TestRoomCard card) {
            super(card);
        }

        @Override
        public TestRoomCard copy() {
            return new TestRoomCard(this);
        }
    }

    private static class TestSplitCard extends SplitCard {
        TestSplitCard(CardSetInfo setInfo) {
            super(null, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}", "{1}{U}", SpellAbilityType.SPLIT);
        }

        private TestSplitCard(TestSplitCard card) {
            super(card);
        }

        @Override
        public TestSplitCard copy() {
            return new TestSplitCard(this);
        }
    }
}
