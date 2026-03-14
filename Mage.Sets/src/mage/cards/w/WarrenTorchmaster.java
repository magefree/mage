package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WarrenTorchmaster extends CardImpl {

    public WarrenTorchmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may blight 1. When you do, target creature gains haste until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new GainAbilityTargetEffect(HasteAbility.getInstance()), false
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new DoWhenCostPaid(ability, new BlightCost(1), "Blight 1?")
        ));
    }

    private WarrenTorchmaster(final WarrenTorchmaster card) {
        super(card);
    }

    @Override
    public WarrenTorchmaster copy() {
        return new WarrenTorchmaster(this);
    }
}
