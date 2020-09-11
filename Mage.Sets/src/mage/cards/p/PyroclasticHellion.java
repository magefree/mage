package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PyroclasticHellion extends CardImpl {

    public PyroclasticHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Pyroclastic Hellion enters the battlefield, you may return a land you control to its owner's hand. When you do, Pyroclastic Hellion deals 2 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoWhenCostPaid(new ReflexiveTriggeredAbility(
                        new DamagePlayersEffect(2, TargetController.OPPONENT),
                        false, "{this} deals 2 damage to each opponent"
                ), new ReturnToHandChosenControlledPermanentCost(
                        new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND)
                ), "Return a land you control to its owner's hand?")
        ));
    }

    private PyroclasticHellion(final PyroclasticHellion card) {
        super(card);
    }

    @Override
    public PyroclasticHellion copy() {
        return new PyroclasticHellion(this);
    }
}
