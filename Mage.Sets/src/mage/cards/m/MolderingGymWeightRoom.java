package mage.cards.m;

import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MolderingGymWeightRoom extends RoomCard {
    public MolderingGymWeightRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{2}{G}", "{5}{G}");

        // Moldering Gym
        // When you unlock this door, search your library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new SearchLibraryPutInPlayEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND), true
                ), false, true
        ));

        // Weight Room
        // When you unlock this door, manifest dread, then put three +1/+1 counters on that creature.
        this.getRightHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(
                new ManifestDreadEffect(CounterType.P1P1.createInstance(3)), false, false
        ));
    }

    private MolderingGymWeightRoom(final MolderingGymWeightRoom card) {
        super(card);
    }

    @Override
    public MolderingGymWeightRoom copy() {
        return new MolderingGymWeightRoom(this);
    }
}
