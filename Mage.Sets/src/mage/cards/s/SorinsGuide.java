package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
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
public final class SorinsGuide extends CardImpl {

    private static final FilterCard filter = new FilterCard("Sorin, Vampire Lord");

    static {
        filter.add(new NamePredicate("Sorin, Vampire Lord"));
    }

    public SorinsGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Sorin's Guide enters the battlefield, you may search your library and/or graveyard for a card named Sorin, Vampire Lord, reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryGraveyardPutInHandEffect(filter, false, true)
        ));
    }

    private SorinsGuide(final SorinsGuide card) {
        super(card);
    }

    @Override
    public SorinsGuide copy() {
        return new SorinsGuide(this);
    }
}
