package mage.cards.y;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YanlingsHarbinger extends CardImpl {

    private static final FilterCard filter = new FilterCard("Mu Yanling, Celestial Wind");

    static {
        filter.add(new NamePredicate("Mu Yanling, Celestial Wind"));
    }

    public YanlingsHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Yanling's Harbinger enters the battlefield, you may search your library and/or graveyard for a card named Mu Yanling, Celestial Wind, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private YanlingsHarbinger(final YanlingsHarbinger card) {
        super(card);
    }

    @Override
    public YanlingsHarbinger copy() {
        return new YanlingsHarbinger(this);
    }
}
