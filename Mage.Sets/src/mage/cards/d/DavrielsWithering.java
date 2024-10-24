package mage.cards.d;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesColorTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author karapuzz14
 */
public final class DavrielsWithering extends CardImpl {

    public DavrielsWithering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");
        

        // Target creature an opponent controls perpetually gets -1/-2.
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());

        ContinuousEffect effect = new BoostTargetPerpetuallyEffect(-1, -2);
        effect.setText("Target creature an opponent controls perpetually gets -1/-2");
        this.getSpellAbility().addEffect(effect);
    }

    private DavrielsWithering(final DavrielsWithering card) {
        super(card);
    }

    @Override
    public DavrielsWithering copy() {
        return new DavrielsWithering(this);
    }
}
