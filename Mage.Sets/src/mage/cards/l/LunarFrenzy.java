package mage.cards.l;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LunarFrenzy extends CardImpl {

    public LunarFrenzy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // Target creature you control gets +X/+0 and gains first strike and trample until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn
        ).setText("target creature you control gets +X/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and trample until end of turn"));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private LunarFrenzy(final LunarFrenzy card) {
        super(card);
    }

    @Override
    public LunarFrenzy copy() {
        return new LunarFrenzy(this);
    }
}
