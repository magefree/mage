package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.FlashAbility;
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
public final class AshioksForerunner extends CardImpl {

    private static final FilterCard filter = new FilterCard("Ashiok, Sculptor of Fears");

    static {
        filter.add(new NamePredicate("Ashiok, Sculptor of Fears"));
    }

    public AshioksForerunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Ashiok's Forerunner enters the battlefield, you may search your library and/or graveyard for a card named Ashiok, Sculptor of Fears, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private AshioksForerunner(final AshioksForerunner card) {
        super(card);
    }

    @Override
    public AshioksForerunner copy() {
        return new AshioksForerunner(this);
    }
}
