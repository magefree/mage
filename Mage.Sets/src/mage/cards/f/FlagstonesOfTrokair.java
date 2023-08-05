package mage.cards.f;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FlagstonesOfTrokair extends CardImpl {

    private static final FilterLandCard FILTER = new FilterLandCard("Plains card");

    static {
        FILTER.add(SubType.PLAINS.getPredicate());
    }

    public FlagstonesOfTrokair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.supertype.add(SuperType.LEGENDARY);

        // {tap}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // When Flagstones of Trokair is put into a graveyard from the battlefield, you may search your library for a Plains card and put it onto the battlefield tapped. If you do, shuffle your library.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(FILTER), true), true, false));
    }

    private FlagstonesOfTrokair(final FlagstonesOfTrokair card) {
        super(card);
    }

    @Override
    public FlagstonesOfTrokair copy() {
        return new FlagstonesOfTrokair(this);
    }
}
