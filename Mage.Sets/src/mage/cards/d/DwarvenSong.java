package mage.cards.d;

import mage.ObjectColor;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author maxlebedev
 */
public final class DwarvenSong extends CardImpl {

    public DwarvenSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // One or more target creatures become red until end of turn.
        Effect effect = new BecomesColorTargetEffect(ObjectColor.RED, Duration.EndOfTurn);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, Integer.MAX_VALUE));
    }

    private DwarvenSong(final DwarvenSong card) {
        super(card);
    }

    @Override
    public DwarvenSong copy() {
        return new DwarvenSong(this);
    }

}
