
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author BetaSteward
 */
public final class DungeonGeists extends CardImpl {

    public DungeonGeists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());

        // When Dungeon Geists enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's untap step for as long as you control Dungeon Geists.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new DungeonGeistsEffect());
        Target target = new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE);
        ability.addTarget(target);
        this.addAbility(ability, new DungeonGeistsWatcher());
        // watcher needed to send normal events to Dungeon Geists ReplacementEffect
    }

    private DungeonGeists(final DungeonGeists card) {
        super(card);
    }

    @Override
    public DungeonGeists copy() {
        return new DungeonGeists(this);
    }
}

class DungeonGeistsEffect extends ContinuousRuleModifyingEffectImpl {

    public DungeonGeistsEffect() {
        super(Duration.Custom, Outcome.Detriment, false, false);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control {this}";
    }

    public DungeonGeistsEffect(final DungeonGeistsEffect effect) {
        super(effect);
    }

    @Override
    public DungeonGeistsEffect copy() {
        return new DungeonGeistsEffect(this);
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
        if (!(sourceObject instanceof Permanent) || !((Permanent) sourceObject).isControlledBy(source.getControllerId())) {
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
                if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getTargetId().equals(targetPointer.getFirst(game, source))) {
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

class DungeonGeistsWatcher extends Watcher {

    DungeonGeistsWatcher() {
        super(WatcherScope.CARD);
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
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(sourceId)) {
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
}
