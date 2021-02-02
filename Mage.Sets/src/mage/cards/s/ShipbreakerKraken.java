
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public final class ShipbreakerKraken extends CardImpl {

    public ShipbreakerKraken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.KRAKEN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // {6}{U}{U}: Monstrosity 4.
        this.addAbility(new MonstrosityAbility("{6}{U}{U}", 4));
        // When Shipbreaker Kraken becomes monstrous, tap up to four target creatures. Those creatures don't untap during their controllers' untap steps for as long as you control Shipbreaker Kraken.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new TapTargetEffect());
        ability.addTarget(new TargetCreaturePermanent(0,4));
        ability.addEffect(new ShipbreakerKrakenReplacementEffect());
        this.addAbility(ability, new ShipbreakerKrakenWatcher());
    }

    private ShipbreakerKraken(final ShipbreakerKraken card) {
        super(card);
    }

    @Override
    public ShipbreakerKraken copy() {
        return new ShipbreakerKraken(this);
    }
}

class ShipbreakerKrakenReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public ShipbreakerKrakenReplacementEffect() {
        super(Duration.Custom, Outcome.Detriment);
        this.staticText = "Those creatures don't untap during their controllers' untap steps for as long as you control {this}";
    }

    public ShipbreakerKrakenReplacementEffect(final ShipbreakerKrakenReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ShipbreakerKrakenReplacementEffect copy() {
        return new ShipbreakerKrakenReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL ||
               event.getType() == GameEvent.EventType.ZONE_CHANGE ||
               event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // Source must be on the battlefield (it's neccessary to check here because if as response to the enter
        // the battlefield triggered ability the source dies (or will be exiled), then the ZONE_CHANGE or LOST_CONTROL
        // event will happen before this effect is applied ever)
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null || !sourcePermanent.isControlledBy(source.getControllerId())) {
            discard();
            return false;
        }
        if (event.getType() == GameEvent.EventType.LOST_CONTROL) {
            if (event.getTargetId().equals(source.getSourceId())) {
                discard();
                return false;
            }
        }
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && event.getTargetId().equals(source.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                discard();
                return false;
            }
        }

        if (game.getTurn().getStepType() == PhaseStep.UNTAP && event.getType() == GameEvent.EventType.UNTAP) {
            if (targetPointer.getTargets(game, source).contains(event.getTargetId())) {
                return true;
            }
        }

        return false;
    }
}

class ShipbreakerKrakenWatcher extends Watcher {

    ShipbreakerKrakenWatcher () {
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
            ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
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
