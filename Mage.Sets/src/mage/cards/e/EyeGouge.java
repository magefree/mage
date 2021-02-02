
package mage.cards.e;

import java.util.UUID;
import mage.abilities.condition.common.TargetHasSubtypeCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class EyeGouge extends CardImpl {

    public EyeGouge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Target creature gets -1/-1 until end of turn. If it's a Cyclops, destroy it.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1,-1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new ConditionalOneShotEffect(new DestroyTargetEffect(), new TargetHasSubtypeCondition(SubType.CYCLOPS),
                "If it's a Cyclops, destroy it");
        this.getSpellAbility().addEffect(effect);
    }

    private EyeGouge(final EyeGouge card) {
        super(card);
    }

    @Override
    public EyeGouge copy() {
        return new EyeGouge(this);
    }
}
