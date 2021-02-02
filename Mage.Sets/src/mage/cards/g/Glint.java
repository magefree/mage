
package mage.cards.g;

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
public final class Glint extends CardImpl {

    public Glint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");

        // Target creature you control gets +0/+3 and gains hexproof until end of turn.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Effect effect = new BoostTargetEffect(0, 3, Duration.EndOfTurn);
        effect.setText("Target creature you control gets +0/+3");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains hexproof until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private Glint(final Glint card) {
        super(card);
    }

    @Override
    public Glint copy() {
        return new Glint(this);
    }
}
