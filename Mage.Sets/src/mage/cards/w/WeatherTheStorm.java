package mage.cards.w;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WeatherTheStorm extends CardImpl {

    public WeatherTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // You gain 3 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(3));

        // Storm
        this.addAbility(new StormAbility());
    }

    private WeatherTheStorm(final WeatherTheStorm card) {
        super(card);
    }

    @Override
    public WeatherTheStorm copy() {
        return new WeatherTheStorm(this);
    }
}
