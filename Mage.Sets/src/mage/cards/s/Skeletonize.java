
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.SkeletonRegenerateToken;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author North
 */
public final class Skeletonize extends CardImpl {

    public Skeletonize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");

        // Skeletonize deals 3 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // When a creature dealt damage this way dies this turn, create a 1/1 black Skeleton creature token with "{B}: Regenerate this creature."
        this.getSpellAbility().addEffect(new SkeletonizeEffect());
        this.getSpellAbility().addWatcher(new DamagedByWatcher(false));
    }

    private Skeletonize(final Skeletonize card) {
        super(card);
    }

    @Override
    public Skeletonize copy() {
        return new Skeletonize(this);
    }
}

class SkeletonizeEffect extends OneShotEffect {

    public SkeletonizeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "When a creature dealt damage this way dies this turn, create a 1/1 black Skeleton creature token with \"{B}: Regenerate this creature.\"";
    }

    public SkeletonizeEffect(final SkeletonizeEffect effect) {
        super(effect);
    }

    @Override
    public SkeletonizeEffect copy() {
        return new SkeletonizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SkeletonizeDelayedTriggeredAbility();
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class SkeletonizeDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public SkeletonizeDelayedTriggeredAbility() {
        super(new CreateTokenEffect(new SkeletonRegenerateToken()), Duration.EndOfTurn);
        setTriggerPhrase("When a creature dealt damage this way dies this turn, ");
    }

    public SkeletonizeDelayedTriggeredAbility(final SkeletonizeDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SkeletonizeDelayedTriggeredAbility copy() {
        return new SkeletonizeDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, this.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }
}
