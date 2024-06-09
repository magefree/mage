
package mage.cards.g;

import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GlimpseTheSunGod extends CardImpl {

    public GlimpseTheSunGod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{W}");

        // Tap X target creatures. Scry 1.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target creatures"));
        this.getSpellAbility().addEffect(new ScryEffect(1));
        this.getSpellAbility().setTargetAdjuster(new XTargetsCountAdjuster());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GlimpseTheSunGod(final GlimpseTheSunGod card) {
        super(card);
    }

    @Override
    public GlimpseTheSunGod copy() {
        return new GlimpseTheSunGod(this);
    }
}
