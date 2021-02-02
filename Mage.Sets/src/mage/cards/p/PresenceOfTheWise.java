
package mage.cards.p;

import java.util.UUID;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class PresenceOfTheWise extends CardImpl {

    public PresenceOfTheWise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}");

        // You gain 2 life for each card in your hand.
        this.getSpellAbility().addEffect(new GainLifeEffect(
                new MultipliedValue(CardsInControllerHandCount.instance, 2),"You gain 2 life for each card in your hand"));
    }

    private PresenceOfTheWise(final PresenceOfTheWise card) {
        super(card);
    }

    @Override
    public PresenceOfTheWise copy() {
        return new PresenceOfTheWise(this);
    }
}
