package mage.cards.u;

import java.util.UUID;

import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author weirddan455
 */
public final class UnionOfTheThirdPath extends CardImpl {

    public UnionOfTheThirdPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Draw a card, then you gain life equal to the number of cards in your hand.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addEffect(new GainLifeEffect(CardsInControllerHandCount.instance).concatBy(", then"));
    }

    private UnionOfTheThirdPath(final UnionOfTheThirdPath card) {
        super(card);
    }

    @Override
    public UnionOfTheThirdPath copy() {
        return new UnionOfTheThirdPath(this);
    }
}
