package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;

/**
 *
 * @author grimreap124
 */
public final class HideousTaskmaster extends CardImpl {

    public HideousTaskmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{6}{R}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, for each opponent, gain control of up to one target creature that player controls until end of turn.
        // Untap those creatures. They gain trample, haste, and annihilator 1 until end of turn.
        Ability ability = new CastSourceTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn).setTargetPointer(new EachTargetPointer()).setText(
                        "for each opponent, gain control of up to one target creature that player controls until end of turn"));
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addEffect(
                new UntapTargetEffect().setTargetPointer(new EachTargetPointer()).setText("Untap those creatures"));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(new EachTargetPointer()).setText("They gain trample"));
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                .setTargetPointer(new EachTargetPointer()).setText("haste"));
        ability.addEffect(new GainAbilityTargetEffect(new AnnihilatorAbility(1), Duration.EndOfTurn)
                .setTargetPointer(new EachTargetPointer()).setText("and annihilator 1 until end of turn"));
        this.addAbility(ability);
    }

    private HideousTaskmaster(final HideousTaskmaster card) {
        super(card);
    }

    @Override
    public HideousTaskmaster copy() {
        return new HideousTaskmaster(this);
    }
}
