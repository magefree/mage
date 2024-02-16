package mage.cards.r;

import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Radstorm extends CardImpl {

    public Radstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Storm
        this.addAbility(new StormAbility());

        // Proliferate
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private Radstorm(final Radstorm card) {
        super(card);
    }

    @Override
    public Radstorm copy() {
        return new Radstorm(this);
    }
}
