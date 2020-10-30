package mage.cards.s;

import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeaGateRestoration extends CardImpl {

    private static final DynamicValue xValue = new AdditiveDynamicValue(CardsInControllerHandCount.instance, StaticValue.get(1));

    public SeaGateRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}{U}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.s.SeaGateReborn.class;

        // Draw cards equal to the number of cards in your hand plus one. You have no maximum hand size for the rest of the game.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText("Draw cards equal to the number of cards in your hand plus one."));
        this.getSpellAbility().addEffect(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE, Duration.EndOfGame,
                MaximumHandSizeControllerEffect.HandSizeModification.SET
        ));
    }

    private SeaGateRestoration(final SeaGateRestoration card) {
        super(card);
    }

    @Override
    public SeaGateRestoration copy() {
        return new SeaGateRestoration(this);
    }
}
