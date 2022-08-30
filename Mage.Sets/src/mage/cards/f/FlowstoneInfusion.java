package mage.cards.f;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlowstoneInfusion extends CardImpl {

    public FlowstoneInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Target creature gets +2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, -2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private FlowstoneInfusion(final FlowstoneInfusion card) {
        super(card);
    }

    @Override
    public FlowstoneInfusion copy() {
        return new FlowstoneInfusion(this);
    }
}
