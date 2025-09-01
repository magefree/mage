package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author paasar
 */
public final class IrreverentGremlin extends CardImpl {
    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another creature you control with power 2 or less");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public IrreverentGremlin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GREMLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());

        // Whenever another creature you control with power 2 or less enters, you may discard a card. If you do, draw a card. Do this only once each turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()), filter
        ).setDoOnlyOnceEachTurn(true));
    }

    private IrreverentGremlin(final IrreverentGremlin card) {
        super(card);
    }

    @Override
    public IrreverentGremlin copy() {
        return new IrreverentGremlin(this);
    }
}
