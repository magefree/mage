package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author LevelX2
 */

public final class LyevDecree extends CardImpl {

    public LyevDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Detain up to two target creatures your opponents control.
        this.getSpellAbility().addEffect(new DetainTargetEffect());
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent(0, 2));
    }

    private LyevDecree(final LyevDecree card) {
        super(card);
    }

    @Override
    public LyevDecree copy() {
        return new LyevDecree(this);
    }
}
