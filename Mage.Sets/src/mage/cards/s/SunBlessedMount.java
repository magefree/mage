
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author TheElk801
 */
public final class SunBlessedMount extends CardImpl {

    private static final FilterCard filter = new FilterCard("Huatli, Dinosaur Knight");

    static {
        filter.add(new NamePredicate("Huatli, Dinosaur Knight"));
    }

    public SunBlessedMount(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Sun-Blessed Mount enters the battlefield, you may search your library and/or graveyard for a card named Huatli, Dinosaur Knight, reveal it, then put it into your hand. If you searched your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));
    }

    private SunBlessedMount(final SunBlessedMount card) {
        super(card);
    }

    @Override
    public SunBlessedMount copy() {
        return new SunBlessedMount(this);
    }
}
