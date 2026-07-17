package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaterTribeRallier extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public WaterTribeRallier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Waterbend {5}: Look at the top four cards of your library. You may reveal a creature card with power 3 or less from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), new WaterbendCost(5)));
    }

    private WaterTribeRallier(final WaterTribeRallier card) {
        super(card);
    }

    @Override
    public WaterTribeRallier copy() {
        return new WaterTribeRallier(this);
    }
}
