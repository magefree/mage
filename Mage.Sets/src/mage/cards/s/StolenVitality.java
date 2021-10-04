package mage.cards.s;

import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StolenVitality extends CardImpl {

    public StolenVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gets +3/+1 until end of turn. If it's your turn, that creature gains trample until end of turn. Otherwise, it gains first strike until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3, 1));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),
                new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                MyTurnCondition.instance, "If it's your turn, that creature gains trample " +
                "until end of turn. Otherwise, it gains first strike until end of turn"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private StolenVitality(final StolenVitality card) {
        super(card);
    }

    @Override
    public StolenVitality copy() {
        return new StolenVitality(this);
    }
}
