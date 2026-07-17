package mage.cards.p;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PaintersStudioDefacedGallery extends RoomCard {

    public PaintersStudioDefacedGallery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{R}", "{1}{R}");

        // Painter's Studio
        // When you unlock this door, exile the top two cards of your library. You may play them until the end of your next turn.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new ExileTopXMayPlayUntilEffect(2, Duration.UntilEndOfYourNextTurn), false, true
        ));

        // Defaced Gallery
        // Whenever you attack, attacking creatures you control get +1/+0 until end of turn.
        this.getRightHalfCard().addAbility(new AttacksWithCreaturesTriggeredAbility(new BoostControlledEffect(
                1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES
        ), 1));
    }

    private PaintersStudioDefacedGallery(final PaintersStudioDefacedGallery card) {
        super(card);
    }

    @Override
    public PaintersStudioDefacedGallery copy() {
        return new PaintersStudioDefacedGallery(this);
    }
}
