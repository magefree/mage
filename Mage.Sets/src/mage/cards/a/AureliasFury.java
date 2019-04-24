
package mage.cards.a;

import java.util.*;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTargetAmount;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 * GATECRASH FAQ 11.01.2013
 * <p>
 * You announce the value of X and how the damage will be divided as part of
 * casting Aurelia's Fury. Each chosen target must receive at least 1 damage.
 * <p>
 * Aurelia's Fury can't deal damage to both a planeswalker and that
 * planeswalker's controller. If damage dealt by Aurelia's Fury is redirected
 * from a player to a planeswalker he or she controls, that player will be able
 * to cast noncreature spells that turn. If you want to stop a player from
 * casting noncreature spells this turn, you can't choose to redirect the
 * damage to a planeswalker he or she controls.
 * <p>
 * If Aurelia's Fury has multiple targets, and some but not all of them are
 * illegal targets when Aurelia's Fury resolves, Aurelia's Fury will still
 * deal damage to the remaining legal targets according to the original damage
 * division.
 * <p>
 * If all of the targets are illegal when Aurelia's Fury tries to resolve,
 * it will be countered and none of its effects will happen. No creature or
 * player will be dealt damage.
 *
 * @author LevelX2
 */
public final class AureliasFury extends CardImpl {

    public AureliasFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{W}");


        // Aurelia's Fury deals X damage divided as you choose among any number of target creatures and/or players.
        // Tap each creature dealt damage this way. Players dealt damage this way can't cast noncreature spells this turn.
        DynamicValue xValue = new ManacostVariableValue();
        this.getSpellAbility().addEffect(new DamageMultiEffect(xValue));
        this.getSpellAbility().addEffect(new AureliasFuryEffect());
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(xValue));
        this.getSpellAbility().addWatcher(new AureliasFuryDamagedByWatcher());

    }

    public AureliasFury(final AureliasFury card) {
        super(card);
    }

    @Override
    public AureliasFury copy() {
        return new AureliasFury(this);
    }
}

class AureliasFuryEffect extends OneShotEffect {

    public AureliasFuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Tap each creature dealt damage this way. Players dealt damage this way can't cast noncreature spells this turn";
    }

    public AureliasFuryEffect(final AureliasFuryEffect effect) {
        super(effect);
    }

    @Override
    public AureliasFuryEffect copy() {
        return new AureliasFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        AureliasFuryDamagedByWatcher watcher = (AureliasFuryDamagedByWatcher) game.getState().getWatchers().get(AureliasFuryDamagedByWatcher.class.getSimpleName(), source.getSourceId());
        if (watcher != null) {
            for (UUID creatureId : watcher.damagedCreatures) {
                Permanent permanent = game.getPermanent(creatureId);
                if (permanent != null) {
                    permanent.tap(game);
                }
            }
            for (UUID playerId : watcher.damagedPlayers) {
                ContinuousEffect effect = new AureliasFuryCantCastEffect();
                effect.setTargetPointer(new FixedTarget(playerId));
                game.addEffect(effect, source);
            }
            watcher.reset();
        }

        return false;
    }
}

class AureliasFuryCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public AureliasFuryCantCastEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Players dealt damage this way can't cast noncreature spells this turn";
    }

    public AureliasFuryCantCastEffect(final AureliasFuryCantCastEffect effect) {
        super(effect);
    }

    @Override
    public AureliasFuryCantCastEffect copy() {
        return new AureliasFuryCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast noncreature spells this turn (you were dealt damage by " + mageObject.getLogName() + ')';
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null && player.getId().equals(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && !card.isCreature()) {
                return true;
            }
        }
        return false;
    }
}

class AureliasFuryDamagedByWatcher extends Watcher {

    public Set<UUID> damagedCreatures = new HashSet<>();
    public Set<UUID> damagedPlayers = new HashSet<>();

    public AureliasFuryDamagedByWatcher() {
        super(AureliasFuryDamagedByWatcher.class.getSimpleName(), WatcherScope.CARD);
    }

    public AureliasFuryDamagedByWatcher(final AureliasFuryDamagedByWatcher watcher) {
        super(watcher);
        this.damagedCreatures.addAll(watcher.damagedCreatures);
        this.damagedPlayers.addAll(watcher.damagedPlayers);
    }

    @Override
    public AureliasFuryDamagedByWatcher copy() {
        return new AureliasFuryDamagedByWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE) {
            MageObject obj = game.getObject(event.getSourceId());
            if (obj instanceof Spell) {
                if (sourceId.equals(((Spell) obj).getSourceId())) {
                    damagedCreatures.add(event.getTargetId());
                }
            }
        }
        if (event.getType() == EventType.DAMAGED_PLAYER) {
            MageObject obj = game.getObject(event.getSourceId());
            if (obj instanceof Spell) {
                if (sourceId.equals(((Spell) obj).getSourceId())) {
                    damagedPlayers.add(event.getTargetId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedCreatures.clear();
        damagedPlayers.clear();
    }

}
