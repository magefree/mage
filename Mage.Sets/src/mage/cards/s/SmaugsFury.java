package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SmaugsFury extends CardImpl {

    public SmaugsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+0 and gains reach and first strike until end of turn.
        Effect effect = new BoostTargetEffect(3, 0, Duration.EndOfTurn);
        effect.setText("Target creature gets +3/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(ReachAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains reach");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and first strike until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private SmaugsFury(final SmaugsFury card) {
        super(card);
    }

    @Override
    public SmaugsFury copy() {
        return new SmaugsFury(this);
    }
}
