package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class CalmwaterMentor extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public CalmwaterMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.SEAL);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature enters, you may search your library for up to two creature cards with power 2 or less, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
            new TargetCardInLibrary(0, 2, filter), true
        ), true));
    }

    private CalmwaterMentor(final CalmwaterMentor card) {
        super(card);
    }

    @Override
    public CalmwaterMentor copy() {
        return new CalmwaterMentor(this);
    }
}
