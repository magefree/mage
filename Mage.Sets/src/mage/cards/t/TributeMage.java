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
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TributeMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact card with converted mana cost 2");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, 2));
    }

    public TributeMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Tribute Mage enters the battlefield, you may search your library for an artifact card with converted mana cost 2, reveal that card, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, 1, filter), true, true
        ), true));
    }

    private TributeMage(final TributeMage card) {
        super(card);
    }

    @Override
    public TributeMage copy() {
        return new TributeMage(this);
    }
}
