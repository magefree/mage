
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SagaChapter;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class TimeOfIce extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public TimeOfIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);

        // I, II — Tap target creature an opponent controls. It doesn't untap during its controller's untap step for as long as you control Time of Ice.
        Effects effects = new Effects();
        effects.add(new TapTargetEffect());
        effects.add(new TimeOfIceEffect());
        sagaAbility.addChapterEffect(
                this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, effects,
                new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE)
        );

        // III — Return all tapped creatures to their owners' hands.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new ReturnToHandFromBattlefieldAllEffect(filter));
        this.addAbility(sagaAbility, new TimeOfIceWatcher());
    }

    private TimeOfIce(final TimeOfIce card) {
        super(card);
    }

    @Override
    public TimeOfIce copy() {
        return new TimeOfIce(this);
    }
}

class TimeOfIceEffect extends ContinuousRuleModifyingEffectImpl {

    public TimeOfIceEffect() {
        super(Duration.Custom, Outcome.Detriment, false, false);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control {this}";
    }

    public TimeOfIceEffect(final TimeOfIceEffect effect) {
        super(effect);
    }

    @Override
    public TimeOfIceEffect copy() {
        return new TimeOfIceEffect(this);
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
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject == null || sourceObject.getZoneChangeCounter(game) > source.getSourceObjectZoneChangeCounter() + 1) {
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

class TimeOfIceWatcher extends Watcher {

    TimeOfIceWatcher() {
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
