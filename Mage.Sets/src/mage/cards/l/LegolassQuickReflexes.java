package mage.cards.l;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class LegolassQuickReflexes extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();

    public LegolassQuickReflexes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Split second
        this.addAbility(new SplitSecondAbility());

        // Untap target creature. Until end of turn, it gains hexproof, reach, and "Whenever this creature becomes tapped, it deals damage equal to its power to up to one target creature."
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn
        ).setText("Until end of turn, it gains hexproof"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                ReachAbility.getInstance(), Duration.EndOfTurn
        ).setText(", reach"));

        TriggeredAbility trigger = new BecomesTappedSourceTriggeredAbility(
                new DamageTargetEffect(xValue)
                        .setText("it deals damage equal to its power to up to one target creature"),
                false
        );
        trigger.addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                trigger, Duration.EndOfTurn
        ).setText(", and \"Whenever this creature becomes tapped, "
                + "it deals damage equal to its power to up to one target creature.\""));
    }

    private LegolassQuickReflexes(final LegolassQuickReflexes card) {
        super(card);
    }

    @Override
    public LegolassQuickReflexes copy() {
        return new LegolassQuickReflexes(this);
    }
}
