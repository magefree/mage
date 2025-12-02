package mage.cards.o;


import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.events.DamageEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Susucr
 */
public final class OjerAxonilDeepestMight extends TransformingDoubleFacedCard {

    public OjerAxonilDeepestMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOD}, "{2}{R}{R}",
                "Temple of Power",
                new SuperType[]{}, new CardType[]{CardType.LAND}, new SubType[]{}, "");

        // Ojer Axonil, Deepest Might
        this.getLeftHalfCard().setPT(4, 4);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // If a red source you control would deal an amount of noncombat damage less than Ojer Axonil's power to an opponent, that source deals damage equal to Ojer Axonil's power instead.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new OjerAxonilDeepestMightReplacementEffect()));

        // When Ojer Axonil dies, return it to the battlefield tapped and transformed under its owner's control.
        this.getLeftHalfCard().addAbility(new DiesSourceTriggeredAbility(new OjerAxonilDeepestMightTransformEffect()));

        // Temple of Power
        // {T}: Add {R}.
        this.getRightHalfCard().addAbility(new RedManaAbility());

        // {2}{R}, {T}: Transform Temple of Power. Activate only if red sources you controlled dealt 4 or more noncombat damage this turn and only as a sorcery.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{2}{R}"), TempleOfPowerCondition.instance
        ).setTiming(TimingRule.SORCERY);
        ability.addCost(new TapSourceCost());
        ability.addWatcher(new TempleOfPowerWatcher());
        this.getRightHalfCard().addAbility(ability.addHint(TempleOfPowerHint.instance));
    }

    private OjerAxonilDeepestMight(final OjerAxonilDeepestMight card) {
        super(card);
    }

    @Override
    public OjerAxonilDeepestMight copy() {
        return new OjerAxonilDeepestMight(this);
    }
}

// Inspired by Edgar, Charmed Groom
class OjerAxonilDeepestMightTransformEffect extends OneShotEffect {

    OjerAxonilDeepestMightTransformEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield tapped and transformed under its owner's control";
    }

    private OjerAxonilDeepestMightTransformEffect(final OjerAxonilDeepestMightTransformEffect effect) {
        super(effect);
    }

    @Override
    public OjerAxonilDeepestMightTransformEffect copy() {
        return new OjerAxonilDeepestMightTransformEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = source.getSourceCardIfItStillExists(game);
        if (controller == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);
        return true;
    }
}

// Inspired by Torbran, Thane of Red Fell
class OjerAxonilDeepestMightReplacementEffect extends ReplacementEffectImpl {

    OjerAxonilDeepestMightReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        this.staticText = "If a red source you control would deal an amount of noncombat damage less "
                + "than {this}'s power to an opponent, that source deals damage equal to {this}'s power instead.";
    }

    private OjerAxonilDeepestMightReplacementEffect(final OjerAxonilDeepestMightReplacementEffect effect) {
        super(effect);
    }

    @Override
    public OjerAxonilDeepestMightReplacementEffect copy() {
        return new OjerAxonilDeepestMightReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent ojer = source.getSourcePermanentIfItStillExists(game);
        if (ojer != null && ojer.getPower().getValue() > 0) {
            event.setAmount(ojer.getPower().getValue());
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        // Is damage to an opponent?
        if (controller == null || !controller.hasOpponent(event.getTargetId(), game)) {
            return false;
        }
        MageObject sourceObject;
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (sourcePermanent == null) {
            sourceObject = game.getObject(event.getSourceId());
            if (sourceObject == null
                    || !(sourceObject instanceof Controllable)
                    || !((Controllable) sourceObject).isControlledBy(controller.getId())
            ) {
                return false; // Only source you control.
            }
        } else {
            if (!sourcePermanent.isControlledBy(controller.getId())) {
                return false;  // Only source you control.
            }
            sourceObject = sourcePermanent;
        }

        Permanent ojer = source.getSourcePermanentIfItStillExists(game);
        DamageEvent dmgEvent = (DamageEvent) event;

        return sourceObject != null
                && ojer != null
                && dmgEvent != null
                && sourceObject.getColor(game).isRed()
                && !dmgEvent.isCombatDamage()
                && event.getAmount() > 0
                && event.getAmount() < ojer.getPower().getValue();
    }
}

enum TempleOfPowerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        TempleOfPowerWatcher watcher = game.getState().getWatcher(TempleOfPowerWatcher.class);
        return watcher != null
                && 4 <= watcher.damageForPlayer(source.getControllerId());
    }

    @Override
    public String toString() {
        return "red sources you controlled dealt 4 or more noncombat damage this turn";
    }
}

enum TempleOfPowerHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        TempleOfPowerWatcher watcher = game.getState().getWatcher(TempleOfPowerWatcher.class);
        if (watcher == null) {
            return "";
        }

        return "Non-combat damage from red source: "
                + watcher.damageForPlayer(ability.getControllerId());
    }

    @Override
    public TempleOfPowerHint copy() {
        return instance;
    }
}

class TempleOfPowerWatcher extends Watcher {

    // player -> total non combat damage from red source controlled by that player dealt this turn.
    private final Map<UUID, Integer> damageMap = new HashMap<>();

    public TempleOfPowerWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            DamagedEvent dmgEvent = (DamagedEvent) event;

            // watch only non combat damage events.
            if (dmgEvent == null || dmgEvent.isCombatDamage()) {
                return;
            }

            MageObject sourceObject;
            UUID sourceControllerId = null;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (sourcePermanent != null) {
                // source is a permanent.
                sourceObject = sourcePermanent;
                sourceControllerId = sourcePermanent.getControllerId();
            } else {
                sourceObject = game.getSpellOrLKIStack(event.getSourceId());
                if (sourceObject != null) {
                    // source is a spell.
                    sourceControllerId = ((StackObject) sourceObject).getControllerId();
                } else {
                    sourceObject = game.getObject(event.getSourceId());
                    if (sourceObject instanceof CommandObject) {
                        // source is a Command Object. For instance Emblem
                        sourceControllerId = ((CommandObject) sourceObject).getControllerId();
                    }
                }
            }

            // watch only red sources dealing damage
            if (sourceObject == null || sourceControllerId == null || !sourceObject.getColor().isRed()) {
                return;
            }

            damageMap.compute(sourceControllerId, (k, i) -> (i == null ? 0 : i) + event.getAmount());
        }
    }

    @Override
    public void reset() {
        damageMap.clear();
        super.reset();
    }

    int damageForPlayer(UUID playerId) {
        return damageMap.getOrDefault(playerId, 0);
    }
}
