
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryGraveyardPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author fireshoes
 */
public final class GideonsResolve extends CardImpl {

    private static final FilterCard filter = new FilterCard("Gideon, Martial Paragon");

    static {
        filter.add(new NamePredicate("Gideon, Martial Paragon"));
    }

    public GideonsResolve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");

        // When Gideon's Resolve enters the battlefield, you may search your library and/or graveyard for a card named Gideon, Martial Paragon,
        // reveal it, and put it into your hand. If you search your library this way, shuffle it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryGraveyardPutInHandEffect(filter), true));

        // Creature you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private GideonsResolve(final GideonsResolve card) {
        super(card);
    }

    @Override
    public GideonsResolve copy() {
        return new GideonsResolve(this);
    }
}
