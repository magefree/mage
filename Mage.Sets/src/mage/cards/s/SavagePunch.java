package mage.cards.s;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SavagePunch extends CardImpl {

    public SavagePunch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // <i>Ferocious</i> &mdash; The creature you control gets +2/+2 until end of turn before it fights if you control a creature with power 4 or greater.
        Effect effect = new ConditionalContinuousEffect(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn),
                new LockedInCondition(FerociousCondition.instance),
                "<i>Ferocious</i> &mdash; The creature you control gets +2/+2 until end of turn before it fights if you control a creature with power 4 or greater");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addHint(FerociousHint.instance);

        // Target creature you control fights target creature you don't control.
        effect = new FightTargetsEffect();
        effect.setText("<br/>Target creature you control fights target creature you don't control");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL);
        this.getSpellAbility().addTarget(target);
    }

    private SavagePunch(final SavagePunch card) {
        super(card);
    }

    @Override
    public SavagePunch copy() {
        return new SavagePunch(this);
    }
}
