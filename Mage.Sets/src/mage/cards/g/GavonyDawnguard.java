package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BecomeDayAsEntersAbility;
import mage.abilities.common.BecomesDayOrNightTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GavonyDawnguard extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("a creature card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public GavonyDawnguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ward {1}
        this.addAbility(new WardAbility(new GenericManaCost(1)));

        // If it's neither day nor night, it becomes day as Gavony Dawnguard enters the battlefield.
        this.addAbility(new BecomeDayAsEntersAbility());

        // Whenever day becomes night or night becomes day, look at the top four cards of your library. You may reveal a creature card with mana value 3 or less from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new BecomesDayOrNightTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY)));
    }

    private GavonyDawnguard(final GavonyDawnguard card) {
        super(card);
    }

    @Override
    public GavonyDawnguard copy() {
        return new GavonyDawnguard(this);
    }
}
