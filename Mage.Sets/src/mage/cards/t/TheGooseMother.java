package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.HalfValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class TheGooseMother extends CardImpl {

    private static final DynamicValue halfX = new HalfValue(ManacostVariableValue.ETB, true);

    public TheGooseMother(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // The Goose Mother enters the battlefield with X +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new EntersBattlefieldWithXCountersEffect(CounterType.P1P1.createInstance())
        ));

        // When The Goose Mother enters the battlefield, create half X Food tokens, rounded up.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new FoodToken(), halfX)
                        .setText("create half X Food tokens, rounded up.")
        ));

        // Whenever The Goose Mother attacks, you may sacrifice a Food. If you do, draw a card.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD)
        )));
    }

    private TheGooseMother(final TheGooseMother card) {
        super(card);
    }

    @Override
    public TheGooseMother copy() {
        return new TheGooseMother(this);
    }
}
