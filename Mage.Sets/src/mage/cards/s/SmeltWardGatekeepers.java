package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.YouControlTwoOrMoreGatesCondition;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.common.GatesYouControlHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SmeltWardGatekeepers extends CardImpl {

    public SmeltWardGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Smelt-Ward Gatekeepers enters the battlefield, if you control two or more Gates, gain control of target creature an opponent controls until end of turn. Untap that creature. That creature gains haste until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.EndOfTurn))
                .withInterveningIf(YouControlTwoOrMoreGatesCondition.instance);
        ability.addEffect(new UntapTargetEffect().setText("untap that creature"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains haste until end of turn"));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(GatesYouControlHint.instance));
    }

    private SmeltWardGatekeepers(final SmeltWardGatekeepers card) {
        super(card);
    }

    @Override
    public SmeltWardGatekeepers copy() {
        return new SmeltWardGatekeepers(this);
    }

}
