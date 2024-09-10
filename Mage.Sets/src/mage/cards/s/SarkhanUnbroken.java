package mage.cards.s;

import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.game.permanent.token.DragonToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public final class SarkhanUnbroken extends CardImpl {

    private static final FilterCard dragonFilter = new FilterCard("Dragon creature cards");

    static {
        dragonFilter.add(SubType.DRAGON.getPredicate());
    }

    public SarkhanUnbroken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SARKHAN);

        this.setStartingLoyalty(4);

        // +1: Draw a card, then add one mana of any color.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DrawCardSourceControllerEffect(1), 1);
        loyaltyAbility.addEffect(new AddManaOfAnyColorEffect().concatBy(", then"));
        this.addAbility(loyaltyAbility);
        // -2: Create a 4/4 red Dragon creature token with flying.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new DragonToken(), 1), -2));
        // -8: Search your library for any number of Dragon creature cards and put them onto the battlefield. Then shuffle your library.
        this.addAbility(new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, Integer.MAX_VALUE, dragonFilter)), -8));
    }

    private SarkhanUnbroken(final SarkhanUnbroken card) {
        super(card);
    }

    @Override
    public SarkhanUnbroken copy() {
        return new SarkhanUnbroken(this);
    }
}
