package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExperimentalLabStaffRoom extends RoomCard {

    public ExperimentalLabStaffRoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, "{3}{G}", "{2}{G}");

        // Experimental Lab
        // When you unlock this door, manifest dread, then put two +1/+1 counters and a trample counter on that creature.
        this.getLeftHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new ManifestDreadEffect(
                CounterType.P1P1.createInstance(2), CounterType.TRAMPLE.createInstance()
        ), false, true));

        // Staff Room
        // Whenever a creature you control deals combat damage to a player, turn that creature face up or put a +1/+1 counter on it.
        this.getRightHalfCard().addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new StaffRoomEffect(), StaticFilters.FILTER_CONTROLLED_CREATURE,
                false, SetTargetPointer.PERMANENT, true
        ));
    }

    private ExperimentalLabStaffRoom(final ExperimentalLabStaffRoom card) {
        super(card);
    }

    @Override
    public ExperimentalLabStaffRoom copy() {
        return new ExperimentalLabStaffRoom(this);
    }
}

class StaffRoomEffect extends OneShotEffect {

    StaffRoomEffect() {
        super(Outcome.Benefit);
        staticText = "turn that creature face up or put a +1/+1 counter on it";
    }

    private StaffRoomEffect(final StaffRoomEffect effect) {
        super(effect);
    }

    @Override
    public StaffRoomEffect copy() {
        return new StaffRoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        return permanent.isFaceDown(game)
                && player.chooseUse(Outcome.BoostCreature, "Turn " + permanent.getIdName() + " creature face-up?", source, game)
                && permanent.turnFaceUp(source, game, source.getControllerId())
                || permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
    }
}
