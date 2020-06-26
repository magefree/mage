package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class ChandrasFiremaw extends CardImpl {

    private static final FilterCard filter = new FilterCard("Chandra, Flame's Catalyst");

    static {
        filter.add(new NamePredicate("Chandra, Flame's Catalyst"));
    }

    public ChandrasFiremaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Chandra's Firemaw enters the battlefield, you may search your library and/or graveyard for a card named Chandra, Flame's Catalyst, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter),true));
    }

    private ChandrasFiremaw(final ChandrasFiremaw card) {
        super(card);
    }

    @Override
    public ChandrasFiremaw copy() {
        return new ChandrasFiremaw(this);
    }
}
