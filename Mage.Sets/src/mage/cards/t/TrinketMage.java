

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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 * @author ayratn
 */
public final class TrinketMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card with converted mana cost 1 or less");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, 2));
    }

    public TrinketMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Trinket Mage enters the battlefield, you may search your library for an artifact card with converted mana cost 1 or less, reveal that card, and put it into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
        SearchEffect effect = new SearchLibraryPutInHandEffect(target, true, true);
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, true));
    }

    public TrinketMage(final TrinketMage card) {
        super(card);
    }

    @Override
    public TrinketMage copy() {
        return new TrinketMage(this);
    }

}
