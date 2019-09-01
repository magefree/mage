package mage.cards.w;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.SendOptionUsedEventEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.ApplyToPermanent;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WallOfStolenIdentity extends CardImpl {

    public WallOfStolenIdentity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Wall of Stolen Identity enter the battlefield as a copy of any creature on the battlefield, except it's a wall in addition to its other types and it has defender. When you do, tap the copied creature and it doesn't untap during its controller's untap step for as long as you control Wall of Stolen Identity.
        this.addAbility(new EntersBattlefieldAbility(
                new WallOfStolenIdentityETBEffect(), true
        ), new WallOfStolenIdentityWatcher());
    }

    private WallOfStolenIdentity(final WallOfStolenIdentity card) {
        super(card);
    }

    @Override
    public WallOfStolenIdentity copy() {
        return new WallOfStolenIdentity(this);
    }
}

class WallOfStolenIdentityETBEffect extends OneShotEffect {

    private static final ApplyToPermanent applier = new ApplyToPermanent() {

        @Override
        public boolean apply(Game game, Permanent permanent, Ability source, UUID copyToObjectId) {
            return this.apply(game, (MageObject) permanent, source, copyToObjectId);
        }

        @Override
        public boolean apply(Game game, MageObject mageObject, Ability source, UUID copyToObjectId) {
            mageObject.getAbilities().add(DefenderAbility.getInstance());
            mageObject.getSubtype(game).add(SubType.WALL);
            return true;
        }
    };
    private static final Effect activater = new SendOptionUsedEventEffect();

    WallOfStolenIdentityETBEffect() {
        super(Outcome.Benefit);
        staticText = "as a copy of any creature on the battlefield, except it's a Wall in addition to its other types " +
                "and it has defender. When you do, tap the copied creature and it doesn't untap during " +
                "its controller's untap step for as long as you control {this}.";
    }

    private WallOfStolenIdentityETBEffect(final WallOfStolenIdentityETBEffect effect) {
        super(effect);
    }

    @Override
    public WallOfStolenIdentityETBEffect copy() {
        return new WallOfStolenIdentityETBEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetCreaturePermanent(0, 1);
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        Effect effect = new CopyPermanentEffect(null, applier, true);
        effect.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
        effect.apply(game, source);
        DelayedTriggeredAbility ability = new WallOfStolenIdentityReflexiveTriggeredAbility();
        ability.getEffects()
                .stream()
                .forEach(e -> e.setTargetPointer(new FixedTarget(target.getFirstTarget(), game)));
        game.addDelayedTriggeredAbility(ability, source);
        return activater.apply(game, source);
    }
}


class WallOfStolenIdentityReflexiveTriggeredAbility extends DelayedTriggeredAbility {

    WallOfStolenIdentityReflexiveTriggeredAbility() {
        super(new TapTargetEffect(), Duration.OneUse, true);
        this.addEffect(new WallOfStolenIdentityUntapEffect());
    }

    private WallOfStolenIdentityReflexiveTriggeredAbility(final WallOfStolenIdentityReflexiveTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WallOfStolenIdentityReflexiveTriggeredAbility copy() {
        return new WallOfStolenIdentityReflexiveTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.OPTION_USED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(this.getControllerId())
                && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "When you do, tap the copied creature and it doesn't untap during " +
                "its controller's untap step for as long as you control {this}.";
    }
}

class WallOfStolenIdentityUntapEffect extends ContinuousRuleModifyingEffectImpl {

    WallOfStolenIdentityUntapEffect() {
        super(Duration.Custom, Outcome.Detriment, false, false);
    }

    private WallOfStolenIdentityUntapEffect(final WallOfStolenIdentityUntapEffect effect) {
        super(effect);
    }

    @Override
    public WallOfStolenIdentityUntapEffect copy() {
        return new WallOfStolenIdentityUntapEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP
                || event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.LOST_CONTROL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Source must be on the battlefield (it's neccessary to check here because if as response to the enter
        // the battlefield triggered ability the source dies (or will be exiled), then the ZONE_CHANGE or LOST_CONTROL
        // event will happen before this effect is applied ever)
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (!(sourceObject instanceof Permanent)
                || !((Permanent) sourceObject).isControlledBy(source.getControllerId())) {
            discard();
            return false;
        }
        switch (event.getType()) {
            case ZONE_CHANGE:
                // end effect if source does a zone move
                if (event.getTargetId().equals(source.getSourceId())) {
                    ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                    if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                        discard();
                        return false;
                    }
                }
                break;
            case UNTAP:
                // prevent to untap the target creature
                if (game.getTurn().getStepType() == PhaseStep.UNTAP
                        && event.getTargetId().equals(targetPointer.getFirst(game, source))) {
                    Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
                    if (targetCreature != null) {
                        return targetCreature.isControlledBy(game.getActivePlayerId());
                    } else {
                        discard();
                        return false;
                    }
                }
                break;
            case LOST_CONTROL:
                // end effect if source control is changed
                if (event.getTargetId().equals(source.getSourceId())) {
                    discard();
                    return false;
                }
                break;
        }
        return false;
    }
}

class WallOfStolenIdentityWatcher extends Watcher {

    WallOfStolenIdentityWatcher() {
        super(WatcherScope.CARD);
    }

    private WallOfStolenIdentityWatcher(WallOfStolenIdentityWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL
                && event.getPlayerId().equals(controllerId)
                && event.getTargetId().equals(sourceId)) {
            condition = true;
            game.replaceEvent(event);
            return;
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && event.getTargetId().equals(sourceId)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                condition = true;
                game.replaceEvent(event);
            }
        }
    }

    @Override
    public void reset() {
        //don't reset condition each turn - only when this leaves the battlefield
    }

    @Override
    public WallOfStolenIdentityWatcher copy() {
        return new WallOfStolenIdentityWatcher(this);
    }
}
