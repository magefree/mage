package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KeldonOverseer extends CardImpl {

    public KeldonOverseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Kicker {3}{R}
        this.addAbility(new KickerAbility("{3}{R}"));

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Keldon Overseer enters the battlefield, if it was kicked, gain control of target creature until end of turn. Untap that creature. It gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainControlTargetEffect(Duration.EndOfTurn)
        ).withInterveningIf(KickedCondition.ONCE);
        ability.addEffect(new UntapTargetEffect("Untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KeldonOverseer(final KeldonOverseer card) {
        super(card);
    }

    @Override
    public KeldonOverseer copy() {
        return new KeldonOverseer(this);
    }
}
