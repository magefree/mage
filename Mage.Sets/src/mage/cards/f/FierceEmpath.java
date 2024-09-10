
package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class FierceEmpath extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature card with mana value 6 or greater");
    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }
    public FierceEmpath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Fierce Empath enters the battlefield, you may search your library for a creature card with converted mana cost 6 or greater, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true)
                    .setText("search your library for a creature card with mana value 6 or greater, reveal it, put it into your hand, then shuffle"),
                true));
    }

    private FierceEmpath(final FierceEmpath card) {
        super(card);
    }

    @Override
    public FierceEmpath copy() {
        return new FierceEmpath(this);
    }
}
