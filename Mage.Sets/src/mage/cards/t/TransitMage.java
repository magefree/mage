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
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TransitMage extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard("an artifact card with mana value 4 or 5");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 6));
    }

    public TransitMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, you may search your library for an artifact card with mana value 4 or 5, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true), true
        ));
    }

    private TransitMage(final TransitMage card) {
        super(card);
    }

    @Override
    public TransitMage copy() {
        return new TransitMage(this);
    }
}
