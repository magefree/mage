package mage.cards.t;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TemurBattleRage extends CardImpl {

    private static final String rule = "<br><i>Ferocious</i> &mdash; That creature also gains trample until end of turn if you control a creature with power 4 or greater";

    public TemurBattleRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gains double strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // <i>Ferocious</i> That creature also gains trample until end of turn if you control a creature with power 4 or greater.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn),
                new LockedInCondition(FerociousCondition.instance),
                rule));
        this.getSpellAbility().addHint(FerociousHint.instance);
    }

    private TemurBattleRage(final TemurBattleRage card) {
        super(card);
    }

    @Override
    public TemurBattleRage copy() {
        return new TemurBattleRage(this);
    }
}
