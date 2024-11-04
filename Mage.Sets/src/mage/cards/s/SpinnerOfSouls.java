package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.RevealCardsFromLibraryUntilEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class SpinnerOfSouls extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SpinnerOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever another nontoken creature you control dies, you may reveal cards from the top of your library until you reveal a creature card. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.addAbility(new DiesCreatureTriggeredAbility(Zone.BATTLEFIELD,
                new RevealCardsFromLibraryUntilEffect(
                        StaticFilters.FILTER_CARD_CREATURE, PutCards.HAND, PutCards.BOTTOM_RANDOM
                ), true, filter, false
        ));
    }

    private SpinnerOfSouls(final SpinnerOfSouls card) {
        super(card);
    }

    @Override
    public SpinnerOfSouls copy() {
        return new SpinnerOfSouls(this);
    }
}
