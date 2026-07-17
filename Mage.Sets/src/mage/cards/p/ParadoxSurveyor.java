package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class ParadoxSurveyor extends CardImpl {

    private static final FilterCard filter = new FilterCard("a land card or a card with {X} in its mana cost");

    static {
        filter.add(Predicates.or(
            CardType.LAND.getPredicate(),
            VariableManaCostPredicate.instance
        ));
    }

    public ParadoxSurveyor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G/U}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When this creature enters, look at the top five cards of your library.
        // You may reveal a land card or a card with {X} in its mana cost from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
            5, 1, filter,
            PutCards.HAND, PutCards.BOTTOM_RANDOM
        )));
    }

    private ParadoxSurveyor(final ParadoxSurveyor card) {
        super(card);
    }

    @Override
    public ParadoxSurveyor copy() {
        return new ParadoxSurveyor(this);
    }
}
