
package mage.cards.mock;

import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;
import mage.cards.repository.CardInfo;

/**
 *
 * @author LevelX2
 */
public class MockSplitCardHalf extends MockCard implements SplitCardHalf {

    private SplitCard splitCardParent;

    public MockSplitCardHalf(CardInfo card) {
        super(card);
    }

    public MockSplitCardHalf(final MockSplitCardHalf card) {
        super(card);
    }

    @Override
    public MockSplitCardHalf copy() {
        return new MockSplitCardHalf(this);
    }

    @Override
    public void setParentCard(SplitCard card) {
        this.splitCardParent = card;
    }

    @Override
    public SplitCard getParentCard() {
        return splitCardParent;
    }

}
