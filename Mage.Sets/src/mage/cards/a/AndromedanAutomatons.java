package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author muz
 */
public final class AndromedanAutomatons extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Andromedan Automatons");

    static {
        filter.add(new NamePredicate("Andromedan Automatons"));
    }

    public AndromedanAutomatons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANDROID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, you may search your library for any number of cards named Andromedan Automatons, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
            new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), true
        ), true));
    }

    private AndromedanAutomatons(final AndromedanAutomatons card) {
        super(card);
    }

    @Override
    public AndromedanAutomatons copy() {
        return new AndromedanAutomatons(this);
    }
}
