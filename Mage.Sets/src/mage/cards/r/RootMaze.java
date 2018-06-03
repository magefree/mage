
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public final class RootMaze extends CardImpl {

    public RootMaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // Artifacts and lands enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RootMazeEffect()));
    }

    public RootMaze(final RootMaze card) {
        super(card);
    }

    @Override
    public RootMaze copy() {
        return new RootMaze(this);
    }
}

class RootMazeEffect extends ReplacementEffectImpl {

    RootMazeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Tap);
        staticText = "Artifacts and lands enter the battlefield tapped";
    }

    RootMazeEffect(final RootMazeEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent target = ((EntersTheBattlefieldEvent) event).getTarget();
        if (target != null) {
            target.setTapped(true);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        return permanent != null && (permanent.isLand() || permanent.isArtifact());
    }

    @Override
    public RootMazeEffect copy() {
        return new RootMazeEffect(this);
    }
}
