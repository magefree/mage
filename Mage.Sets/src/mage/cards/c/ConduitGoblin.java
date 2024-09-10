package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConduitGoblin extends CardImpl {

    public ConduitGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Conduit Goblin enters the battlefield, you get {E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(2)));

        // At the beginning of combat on your turn, you may pay {E}. If you do, another target creature you control gets +1/+0 and gains haste until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(new BoostTargetEffect(1, 0), new PayEnergyCost(1))
                        .addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance()))
                        .setText("you may pay {E}. If you do, another target creature you control gets +1/+0 and gains haste until end of turn"),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability);
    }

    private ConduitGoblin(final ConduitGoblin card) {
        super(card);
    }

    @Override
    public ConduitGoblin copy() {
        return new ConduitGoblin(this);
    }
}
