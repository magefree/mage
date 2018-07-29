package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.GoatToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

public final class ManifestationOfDestiny extends CardImpl {

    private static final FilterCard filter = new FilterCard("Plains card");
    private static final FilterControlledPermanent plains = new FilterControlledPermanent("Plains");

    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
        plains.add(new SubtypePredicate(SubType.PLAINS));
    }

    public ManifestationOfDestiny(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(7);

        //When Manifestation of Destiny enters the battlefield, search your library for a Plains card, put it onto the battlefield, then shuffle your library.
        // Then create a 0/1 white Goat creature token for each Plains you control.

        Ability ability = new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter)), false);
        ability.addEffect(new CreateTokenEffect(new GoatToken(), new PermanentsOnBattlefieldCount(plains)));
        this.addAbility(ability);
    }
    public ManifestationOfDestiny(final ManifestationOfDestiny card) {
        super(card);
    }

    @Override
    public ManifestationOfDestiny copy() {
        return new ManifestationOfDestiny(this);
    }
}
