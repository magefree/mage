package mage.cards.l;

import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SetPlayerLifeSourceEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author muz
 */
public final class LiveLongAndProsper extends CardImpl {

    public LiveLongAndProsper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}{U}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Double your life total. Draw cards equal to the number of cards in your hand. You have no maximum hand size until your next turn.
        this.getSpellAbility().addEffect(new SetPlayerLifeSourceEffect(new MultipliedValue(ControllerLifeCount.instance, 2))
            .setText("double your life total"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(CardsInControllerHandCount.ANY)
            .setText("Draw cards equal to the number of cards in your hand"));
        this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(
            Integer.MAX_VALUE, Duration.UntilYourNextTurn, HandSizeModification.SET
        ));
    }

    private LiveLongAndProsper(final LiveLongAndProsper card) {
        super(card);
    }

    @Override
    public LiveLongAndProsper copy() {
        return new LiveLongAndProsper(this);
    }
}
