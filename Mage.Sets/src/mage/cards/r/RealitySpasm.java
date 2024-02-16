package mage.cards.r;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsAdjuster;

/**
 *
 * @author awjackson
 */
public final class RealitySpasm extends CardImpl {
    
    public RealitySpasm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{X}{U}{U}");

        // Choose one - Tap X target permanents; or untap X target permanents.
        this.getSpellAbility().addEffect(new TapTargetEffect("tap X target permanents"));
        this.getSpellAbility().addTarget(new TargetPermanent());
        Mode mode = new Mode(new UntapTargetEffect("untap X target permanents"));
        mode.addTarget(new TargetPermanent());
        this.getSpellAbility().addMode(mode);
        this.getSpellAbility().setTargetAdjuster(XTargetsAdjuster.instance);
    }

    private RealitySpasm(final RealitySpasm card) {
        super(card);
    }

    @Override
    public RealitySpasm copy() {
        return new RealitySpasm(this);
    }
}
