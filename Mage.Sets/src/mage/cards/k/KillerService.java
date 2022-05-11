package mage.cards.k;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.RhinoWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KillerService extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public KillerService(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // When Killer Service enters the battlefield, create a number of Food tokens equal to the number of opponents you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new FoodToken(), OpponentsCount.instance
        ).setText("create a number of Food tokens equal to the number of opponents you have")));

        // At the beginning of your end step, you may pay {2} and sacrifice a token. If you do, create a 4/4 green Rhino Warrior creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DoIfCostPaid(
                        new CreateTokenEffect(new RhinoWarriorToken()),
                        new CompositeCost(
                                new GenericManaCost(2), new SacrificeTargetCost(filter),
                                "pay {2} and sacrifice a token"
                        )
                ), TargetController.YOU, false
        ));
    }

    private KillerService(final KillerService card) {
        super(card);
    }

    @Override
    public KillerService copy() {
        return new KillerService(this);
    }
}
