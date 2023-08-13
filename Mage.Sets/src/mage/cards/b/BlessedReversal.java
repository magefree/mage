package mage.cards.b;

import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CreaturesAttackingYouCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author North
 */
public final class BlessedReversal extends CardImpl {

    public BlessedReversal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");


        // You gain 3 life for each creature attacking you.
        this.getSpellAbility().addEffect(new GainLifeEffect(new MultipliedValue(CreaturesAttackingYouCount.instance, 3)));
        this.getSpellAbility().addHint(CreaturesAttackingYouCount.getHint());
    }

    private BlessedReversal(final BlessedReversal card) {
        super(card);
    }

    @Override
    public BlessedReversal copy() {
        return new BlessedReversal(this);
    }
}
