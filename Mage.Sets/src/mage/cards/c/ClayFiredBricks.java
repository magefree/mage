package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.GnomeToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClayFiredBricks extends TransformingDoubleFacedCard {

    public ClayFiredBricks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{1}{W}",
                "Cosmium Kiln",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "W");

        // Clay-Fired Bricks

        // When Clay-Fired Bricks enters the battlefield, search your library for a basic Plains card, reveal it, put it into your hand, then shuffle. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_PLAINS), true)
        );
        ability.addEffect(new GainLifeEffect(2));
        this.getLeftHalfCard().addAbility(ability);

        // Craft with artifact {5}{W}{W}
        this.getLeftHalfCard().addAbility(new CraftAbility("{5}{W}{W}"));

        // Cosmium Kiln

        // When Cosmium Kiln enters the battlefield, create two 1/1 colorless Gnome artifact creature tokens.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GnomeToken(), 2)));

        // Creatures you control get +1/+1.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private ClayFiredBricks(final ClayFiredBricks card) {
        super(card);
    }

    @Override
    public ClayFiredBricks copy() {
        return new ClayFiredBricks(this);
    }
}
