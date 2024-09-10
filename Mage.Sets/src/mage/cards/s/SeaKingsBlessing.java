
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public final class SeaKingsBlessing extends CardImpl {

    public SeaKingsBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // One or more target creatures become blue until end of turn.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.BLUE, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, Integer.MAX_VALUE));

    }

    private SeaKingsBlessing(final SeaKingsBlessing card) {
        super(card);
    }

    @Override
    public SeaKingsBlessing copy() {
        return new SeaKingsBlessing(this);
    }

}
