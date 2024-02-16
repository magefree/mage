
package mage.cards.e;

import java.util.UUID;
import mage.abilities.condition.common.RenownedTargetCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EnshroudingMist extends CardImpl {

    public EnshroudingMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Target creature gets +1/+1 until end of turn. Prevent all damage that would dealt to it this turn. If it's renowned, untap it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 1, Duration.EndOfTurn));
        Effect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE);
        effect.setText("Prevent all damage that would be dealt to it this turn");
        this.getSpellAbility().addEffect(effect);
        OneShotEffect effect2 = new UntapTargetEffect();
        effect2.setText("untap it");
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(effect2, RenownedTargetCondition.instance));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private EnshroudingMist(final EnshroudingMist card) {
        super(card);
    }

    @Override
    public EnshroudingMist copy() {
        return new EnshroudingMist(this);
    }
}
