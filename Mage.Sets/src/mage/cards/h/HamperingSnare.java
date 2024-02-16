package mage.cards.h;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HamperingSnare extends CardImpl {

    public HamperingSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Creatures your opponents control get -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-2, 0, Duration.EndOfTurn));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private HamperingSnare(final HamperingSnare card) {
        super(card);
    }

    @Override
    public HamperingSnare copy() {
        return new HamperingSnare(this);
    }
}
