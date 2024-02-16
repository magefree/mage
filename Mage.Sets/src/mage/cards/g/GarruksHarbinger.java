package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.HexproofFromBlackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class GarruksHarbinger extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card or Garruk planeswalker card");
    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                Predicates.and(
                        CardType.PLANESWALKER.getPredicate(),
                        SubType.GARRUK.getPredicate()
                )
        ));
    }

    public GarruksHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Hexproof from Black
        this.addAbility(HexproofFromBlackAbility.getInstance());

        // Whenever Garruk's Harbinger deals combat damage to a player or planeswalker, look at that many cards from the top of your library.
        // You may reveal a creature card or Garruk planeswalker card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerOrPlaneswalkerTriggeredAbility(
                new LookLibraryAndPickControllerEffect(SavedDamageValue.MANY, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM),
                false));
    }

    private GarruksHarbinger(final GarruksHarbinger card) {
        super(card);
    }

    @Override
    public GarruksHarbinger copy() {
        return new GarruksHarbinger(this);
    }
}
