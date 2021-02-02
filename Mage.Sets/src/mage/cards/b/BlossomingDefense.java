
package mage.cards.b;

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
 * @author fireshoes
 */
public final class BlossomingDefense extends CardImpl {

    public BlossomingDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");

        // Target creature you control gets +2/+2 and gains hexproof until end of turn.
        Effect effect = new BoostTargetEffect(2, 2, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +2/+2");
        getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains hexproof until end of turn");
        getSpellAbility().addEffect(effect);
        getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private BlossomingDefense(final BlossomingDefense card) {
        super(card);
    }

    @Override
    public BlossomingDefense copy() {
        return new BlossomingDefense(this);
    }
}
