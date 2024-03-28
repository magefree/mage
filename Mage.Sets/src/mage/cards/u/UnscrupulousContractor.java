package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.keyword.PlotAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnscrupulousContractor extends CardImpl {

    public UnscrupulousContractor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Unscrupulous Contractor enters the battlefield, you may sacrifice a creature. When you do, target player draws two cards and loses 2 life.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DrawCardTargetEffect(2), false);
        ability.addEffect(new LoseLifeTargetEffect(2).setText("and loses 2 life"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE),
                "Sacrifice a creature?"
        )));

        // Plot {2}{B}
        this.addAbility(new PlotAbility(this, "{2}{B}"));
    }

    private UnscrupulousContractor(final UnscrupulousContractor card) {
        super(card);
    }

    @Override
    public UnscrupulousContractor copy() {
        return new UnscrupulousContractor(this);
    }
}
