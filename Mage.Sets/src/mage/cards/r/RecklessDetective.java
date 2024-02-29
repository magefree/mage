package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessDetective extends CardImpl {

    public RecklessDetective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DEVIL);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Whenever Reckless Detective attacks, you may sacrifice an artifact or discard a card. If you do, draw a card and Reckless Detective gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new OrCost(
                        "sacrifice an artifact or discard a card",
                        new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN),
                        new DiscardCardCost()
                )
        ).addEffect(new BoostSourceEffect(2, 0, Duration.EndOfTurn).concatBy("and"))));
    }

    private RecklessDetective(final RecklessDetective card) {
        super(card);
    }

    @Override
    public RecklessDetective copy() {
        return new RecklessDetective(this);
    }
}
