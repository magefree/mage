

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class TreasureMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card with mana value 6 or more");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }

    public TreasureMage (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Treasure Mage enters the battlefield, you may search your library for an artifact card with converted mana cost 6 or greater,
        // reveal that card, and put it into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        SearchEffect effect = new SearchLibraryPutInHandEffect(target, true);
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, true));
    }

    public TreasureMage (final TreasureMage card) {
        super(card);
    }

    @Override
    public TreasureMage copy() {
        return new TreasureMage(this);
    }

}
