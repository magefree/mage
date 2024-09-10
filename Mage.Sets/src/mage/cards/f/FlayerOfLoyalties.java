package mage.cards.f;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FlayerOfLoyalties extends CardImpl {

    public FlayerOfLoyalties(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{8}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When you cast this spell, gain control of target creature until end of turn. Untap that creature. Until end of turn, it has base power and toughness 10/10, and gains trample, annihilator 2, and haste.
        TriggeredAbility trigger = new CastSourceTriggeredAbility(
            new GainControlTargetEffect(Duration.EndOfTurn)
        );
        trigger.addEffect(new UntapTargetEffect().setText("Untap that creature."));
        trigger.addEffect(new SetBasePowerToughnessTargetEffect(
            10, 10, Duration.EndOfTurn
        ).setText("Until end of turn, it has base power and toughness 10/10"));
        trigger.addEffect(new GainAbilityTargetEffect(
            TrampleAbility.getInstance(), Duration.EndOfTurn, "gains trample"
        ).concatBy(", and"));

        TriggeredAbility annihilator = new AnnihilatorAbility(2);
        trigger.addEffect(new GainAbilityTargetEffect(
            annihilator, Duration.EndOfTurn, "annihilator 2"
        ).concatBy(","));
        trigger.addEffect(new GainAbilityTargetEffect(
            HasteAbility.getInstance(), Duration.EndOfTurn, "haste"
        ).concatBy(", and"));

        trigger.addTarget(new TargetCreaturePermanent());
        this.addAbility(trigger);

        // Annihilator 2
        this.addAbility(new AnnihilatorAbility(2));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

    }

    private FlayerOfLoyalties(final FlayerOfLoyalties card) {
        super(card);
    }

    @Override
    public FlayerOfLoyalties copy() {
        return new FlayerOfLoyalties(this);
    }
}
