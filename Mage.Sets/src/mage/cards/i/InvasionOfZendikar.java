package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfZendikar extends CardImpl {

    public InvasionOfZendikar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{3}{G}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(3);
        this.secondSideCardClazz = mage.cards.a.AwakenedSkyclave.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Zendikar enters the battlefield, search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS
        ), true)));
    }

    private InvasionOfZendikar(final InvasionOfZendikar card) {
        super(card);
    }

    @Override
    public InvasionOfZendikar copy() {
        return new InvasionOfZendikar(this);
    }
}
