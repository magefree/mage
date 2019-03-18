
package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author maxlebedev
 */
public final class DwarvenSong extends CardImpl {

    public DwarvenSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Any number of target creatures become red until end of turn.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.RED, Duration.EndOfTurn);
        effect.setText("Any number of target creatures become red until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE, StaticFilters.FILTER_PERMANENT_CREATURE, false));

    }

    public DwarvenSong(final DwarvenSong card) {
        super(card);
    }

    @Override
    public DwarvenSong copy() {
        return new DwarvenSong(this);
    }

}
