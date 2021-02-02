package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.JumpStartAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class MaximizeVelocity extends CardImpl {

    public MaximizeVelocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Target creature gets +1/+1 and gains haste until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                1, 1, Duration.EndOfTurn
        ).setText("Target creature gets +1/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains haste until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Jump-start
        this.addAbility(new JumpStartAbility(this));

    }

    private MaximizeVelocity(final MaximizeVelocity card) {
        super(card);
    }

    @Override
    public MaximizeVelocity copy() {
        return new MaximizeVelocity(this);
    }
}
