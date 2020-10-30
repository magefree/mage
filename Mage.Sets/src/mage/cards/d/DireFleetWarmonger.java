package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DireFleetWarmonger extends CardImpl {

    public DireFleetWarmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, you may sacrifice another creature. If you do, Dire Fleet Warmonger gets +2/+2 and gains trample until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoIfCostPaid(
                new BoostSourceEffect(2, 2, Duration.EndOfTurn).setText("{this} gets +2/+2"),
                new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE))
        ).addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .concatBy("and").setText("gains trample until end of turn")),
                TargetController.YOU, false));
    }

    private DireFleetWarmonger(final DireFleetWarmonger card) {
        super(card);
    }

    @Override
    public DireFleetWarmonger copy() {
        return new DireFleetWarmonger(this);
    }
}
