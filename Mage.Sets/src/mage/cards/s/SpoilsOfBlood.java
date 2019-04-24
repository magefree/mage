
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.SpoilsOfBloodHorrorToken;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class SpoilsOfBlood extends CardImpl {

    public SpoilsOfBlood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Create an X/X black Horror creature token, where X is the number of creatures that died this turn.
        this.getSpellAbility().addEffect(new SpoilsOfBloodEffect());
        this.getSpellAbility().addWatcher(new CreaturesDiedThisTurnWatcher());
    }

    public SpoilsOfBlood(final SpoilsOfBlood card) {
        super(card);
    }

    @Override
    public SpoilsOfBlood copy() {
        return new SpoilsOfBlood(this);
    }
}

class SpoilsOfBloodEffect extends OneShotEffect {

    public SpoilsOfBloodEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Create an X/X black Horror creature token, where X is the number of creatures that died this turn";
    }

    public SpoilsOfBloodEffect(SpoilsOfBloodEffect ability) {
        super(ability);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreaturesDiedThisTurnWatcher watcher = (CreaturesDiedThisTurnWatcher) game.getState().getWatchers().get(CreaturesDiedThisTurnWatcher.class.getSimpleName());
            if (watcher != null) {
                new CreateTokenEffect(new SpoilsOfBloodHorrorToken(watcher.creaturesDiedThisTurn)).apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public SpoilsOfBloodEffect copy() {
        return new SpoilsOfBloodEffect(this);
    }

}

class CreaturesDiedThisTurnWatcher extends Watcher {

    public int creaturesDiedThisTurn = 0;

    public CreaturesDiedThisTurnWatcher() {
        super(CreaturesDiedThisTurnWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public CreaturesDiedThisTurnWatcher(final CreaturesDiedThisTurnWatcher watcher) {
        super(watcher);
    }

    @Override
    public CreaturesDiedThisTurnWatcher copy() {
        return new CreaturesDiedThisTurnWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent) event).isDiesEvent()) {
            MageObject mageObject = game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (mageObject != null && mageObject.isCreature()) {
                creaturesDiedThisTurn++;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        creaturesDiedThisTurn = 0;
    }

}
