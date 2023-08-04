
package mage.cards.mock;

import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.cards.SplitCardHalf;
import mage.cards.repository.CardInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LevelX2
 */
public class MockSplitCardHalf extends MockCard implements SplitCardHalf {

    private SplitCard splitCardParent;
    private ManaCosts<ManaCost> manaCosts;
    private List<String> manaCostsSymbols;

    public MockSplitCardHalf(CardInfo card) {
        super(card);
        this.manaCostsSymbols = card.getManaCosts(CardInfo.ManaCostSide.ALL);
        this.manaCosts = new ManaCostsImpl<>(String.join("", this.manaCostsSymbols));
    }

    protected MockSplitCardHalf(final MockSplitCardHalf card) {
        super(card);
        this.splitCardParent = card.splitCardParent;
        this.manaCosts = card.manaCosts.copy();
        this.manaCostsSymbols = new ArrayList<>(card.manaCostsSymbols);
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

    @Override
    public ManaCosts<ManaCost> getManaCost() {
        // only split half cards can store mana cost in objects list instead strings (memory optimization)
        return manaCosts;
    }

    @Override
    public List<String> getManaCostSymbols() {
        // only split half cards can store mana cost in objects list instead strings (memory optimization)
        return manaCostsSymbols;
    }
}
