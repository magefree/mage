package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Weatherlight extends CardImpl {

    private static final FilterCard filter = new FilterCard("a historic card");

    static {
        filter.add(HistoricPredicate.instance);
    }

    public Weatherlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Weatherlight deals combat damage to a player, look at the top five cards of your library.
        // You may reveal a historic card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new LookLibraryAndPickControllerEffect(5, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                false));

        // Crew 3
        this.addAbility(new CrewAbility(3));
    }

    private Weatherlight(final Weatherlight card) {
        super(card);
    }

    @Override
    public Weatherlight copy() {
        return new Weatherlight(this);
    }
}
