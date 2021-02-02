
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GrotesqueMutation extends CardImpl {

    public GrotesqueMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Target creature gets +3/+1 and gains lifelink until end of turn.
        Effect effect = new BoostTargetEffect(3, 1, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+1");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains lifelink until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GrotesqueMutation(final GrotesqueMutation card) {
        super(card);
    }

    @Override
    public GrotesqueMutation copy() {
        return new GrotesqueMutation(this);
    }
}
