
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.TargetController;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

/**
 *
 * @author fireshoes
 */
public final class IcefallRegent extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public IcefallRegent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Icefall Regent enters the battlefield, tap target creature an opponent controls. That creature doesn't untap during its controller's untap step for as long as you control Icefall Regent.
        Ability ability = new EntersBattlefieldTriggeredAbility(new TapTargetEffect(), false);
        ability.addEffect(new IcefallRegentEffect());
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability, new IcefallRegentWatcher());

        // Spells your opponents cast that target Icefall Regent cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new IcefallRegentCostIncreaseEffect()));

    }

    public IcefallRegent(final IcefallRegent card) {
        super(card);
    }

    @Override
    public IcefallRegent copy() {
        return new IcefallRegent(this);
    }
}

class IcefallRegentEffect extends ContinuousRuleModifyingEffectImpl {

    public IcefallRegentEffect() {
        super(Duration.Custom, Outcome.Detriment, false, false);
        this.staticText = "That creature doesn't untap during its controller's untap step for as long as you control {this}";
    }

    public IcefallRegentEffect(final IcefallRegentEffect effect) {
        super(effect);
    }

    @Override
    public IcefallRegentEffect copy() {
        return new IcefallRegentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_CONTROL
                || event.getType() == GameEvent.EventType.ZONE_CHANGE
                || event.getType() == GameEvent.EventType.UNTAP;
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
            if (event.getTargetId().equals(targetPointer.getFirst(game, source))) {
                Permanent targetCreature = game.getPermanent(targetPointer.getFirst(game, source));
                return targetCreature != null && game.isActivePlayer(targetCreature.getControllerId());
            }
        }

        return false;
    }
}

class IcefallRegentWatcher extends Watcher {

    IcefallRegentWatcher() {
        super("ControlLost", WatcherScope.CARD);
    }

    IcefallRegentWatcher(IcefallRegentWatcher watcher) {
        super(watcher);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LOST_CONTROL && event.getPlayerId().equals(controllerId) && event.getTargetId().equals(sourceId)) {
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

    @Override
    public IcefallRegentWatcher copy() {
        return new IcefallRegentWatcher(this);
    }
}

class IcefallRegentCostIncreaseEffect extends CostModificationEffectImpl {

    private static final String effectText = "Spells your opponents cast that target Icefall Regent cost {2} more to cast";

    IcefallRegentCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = effectText;
    }

    IcefallRegentCostIncreaseEffect(IcefallRegentCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        CardUtil.adjustCost(spellAbility, -2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
                for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                    Mode mode = abilityToModify.getModes().get(modeId);
                    for (Target target : mode.getTargets()) {
                        for (UUID targetUUID : target.getTargets()) {
                            if (targetUUID.equals(source.getSourceId())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public IcefallRegentCostIncreaseEffect copy() {
        return new IcefallRegentCostIncreaseEffect(this);
    }

}
