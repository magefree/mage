
package mage.cards.b;

import java.util.UUID;
import mage.abilities.condition.common.TargetHasSuperTypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class BlessingOfBelzenlok extends CardImpl {

    public BlessingOfBelzenlok(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target creature gets +2/+1 until end of turn. If it's legendary it also gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 1, Duration.EndOfTurn));
        Effect effect = new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn),
                new TargetHasSuperTypeCondition(SuperType.LEGENDARY),
                "If it's legendary, it also gains lifelink until end of turn."
        );
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BlessingOfBelzenlok(final BlessingOfBelzenlok card) {
        super(card);
    }

    @Override
    public BlessingOfBelzenlok copy() {
        return new BlessingOfBelzenlok(this);
    }
}
