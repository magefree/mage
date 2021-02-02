
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class RangersGuile extends CardImpl {

    public RangersGuile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Target creature you control gets +1/+1 and gains hexproof until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +1/+1");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains hexproof until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private RangersGuile(final RangersGuile card) {
        super(card);
    }

    @Override
    public RangersGuile copy() {
        return new RangersGuile(this);
    }
}
