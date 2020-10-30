package mage.cards.b;

import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeyeenVeil extends CardImpl {

    public BeyeenVeil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.b.BeyeenCoast.class;

        // Creatures your opponents control get -2/-0 until end of turn.
        this.getSpellAbility().addEffect(new BoostOpponentsEffect(-2, 0, Duration.EndOfTurn));
    }

    private BeyeenVeil(final BeyeenVeil card) {
        super(card);
    }

    @Override
    public BeyeenVeil copy() {
        return new BeyeenVeil(this);
    }
}
