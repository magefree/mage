package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ConstructRedToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokenzanSmelter extends CardImpl {

    public SokenzanSmelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of combat on your turn, you may pay {1} and sacrifice an artifact. If you do, create a 3/1 red Construct artifact creature token with haste.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new ConstructRedToken()),
                new CompositeCost(
                        new GenericManaCost(1),
                        new SacrificeTargetCost(new TargetControlledPermanent(
                                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_AN
                        )), "pay {1} and sacrifice an artifact"
                )
        ), TargetController.YOU, false));
    }

    private SokenzanSmelter(final SokenzanSmelter card) {
        super(card);
    }

    @Override
    public SokenzanSmelter copy() {
        return new SokenzanSmelter(this);
    }
}
