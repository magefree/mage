package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;

/**
 *
 * @author muz
 */
public final class WinterTormentedLoner extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or planeswalker cards in your graveyard");

    static {
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(filter);

    public WinterTormentedLoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // When Winter enters, you may sacrifice a creature or planeswalker. When you do, each opponent sacrifices a creature of their choice.
        ReflexiveTriggeredAbility sacrificeAbility = new ReflexiveTriggeredAbility(
            new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE), false,
            "each opponent sacrifices a creature of their choice"
        );
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
            sacrificeAbility,
            new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER),
            "Sacrifice a creature or planeswalker?"
        )));

        // Winter gets +1/+0 for each creature and planeswalker card in your graveyard.
        this.addAbility(new SimpleStaticAbility(
            new BoostSourceEffect(xValue, StaticValue.get(0), Duration.WhileOnBattlefield)
        ));
    }

    private WinterTormentedLoner(final WinterTormentedLoner card) {
        super(card);
    }

    @Override
    public WinterTormentedLoner copy() {
        return new WinterTormentedLoner(this);
    }
}
