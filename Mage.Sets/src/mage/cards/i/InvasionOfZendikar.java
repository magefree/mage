package mage.cards.i;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfZendikar extends TransformingDoubleFacedCard {

    public InvasionOfZendikar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{3}{G}",
                "Awakened Skyclave",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELEMENTAL}, "G"
        );

        // Invasion of Zendikar
        this.getLeftHalfCard().setStartingDefense(3);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Zendikar enters the battlefield, you may search your library for up to two basic land cards, put them onto the battlefield tapped, then shuffle your library.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(
                0, 2, StaticFilters.FILTER_CARD_BASIC_LANDS
        ), true)));

        // Awakened Skyclave
        this.getRightHalfCard().setPT(4, 4);

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Haste
        this.getRightHalfCard().addAbility(HasteAbility.getInstance());

        // As long as Awakened Skyclave is on the battlefield, it's a land in addition to its other types.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new AddCardTypeSourceEffect(Duration.WhileOnBattlefield, CardType.LAND)
                .setText("as long as {this} is on the battlefield, it's a land in addition to its other types")));

        // {T}: Add one mana of any color.
        this.getRightHalfCard().addAbility(new AnyColorManaAbility());
    }

    private InvasionOfZendikar(final InvasionOfZendikar card) {
        super(card);
    }

    @Override
    public InvasionOfZendikar copy() {
        return new InvasionOfZendikar(this);
    }
}
