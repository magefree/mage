package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class TrophyMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card with mana value 3");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, 3));
    }

    public TrophyMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Trophy Mage enters the battlefield, you may search your library for an artifact card with converted mana cost 3, reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true
        ), true));
    }

    private TrophyMage(final TrophyMage card) {
        super(card);
    }

    @Override
    public TrophyMage copy() {
        return new TrophyMage(this);
    }
}
