
package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author spjspj
 */
public final class VisageOfBolas extends CardImpl {

    private static final FilterCard filter = new FilterCard("Nicol Bolas, the Deceiver");

    static {
        filter.add(new NamePredicate("Nicol Bolas, the Deceiver"));
    }

    public VisageOfBolas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // When Visage of Bolas enters the battlefield, you may search your library and/or graveyard for a card named Nicol Bolas, the Deceiver, reveal it, and put it into your hand.  If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));

        // {t}: Add {U}, {B}, or {R}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private VisageOfBolas(final VisageOfBolas card) {
        super(card);
    }

    @Override
    public VisageOfBolas copy() {
        return new VisageOfBolas(this);
    }
}
