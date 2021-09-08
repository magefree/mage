package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormChargedSlasher extends CardImpl {

    public StormChargedSlasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setRed(true);
        this.transformable = true;
        this.nightCard = true;

        // At the beginning of combat on your turn, target creature you control gets +2/+0 and gains trample and haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new BoostTargetEffect(2, 0)
                        .setText("target creature you control gets +2/+0"),
                TargetController.YOU, false
        );
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains trample"));
        ability.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setText("and haste until end of turn"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Nightbound
        this.addAbility(NightboundAbility.getInstance());
    }

    private StormChargedSlasher(final StormChargedSlasher card) {
        super(card);
    }

    @Override
    public StormChargedSlasher copy() {
        return new StormChargedSlasher(this);
    }
}
