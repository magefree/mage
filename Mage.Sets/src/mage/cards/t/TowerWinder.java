package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.ReachAbility;
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
public final class TowerWinder extends CardImpl {

    private static final FilterCard filter = new FilterCard("Command Tower");

    static {
        filter.add(new NamePredicate("Command Tower"));
    }

    public TowerWinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Tower Winder enters the battlefield, search your library and/or graveyard for a card named Command Tower, reveal it, and put it into your hand. If you search your library this way, shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter)));
    }

    private TowerWinder(final TowerWinder card) {
        super(card);
    }

    @Override
    public TowerWinder copy() {
        return new TowerWinder(this);
    }
}
