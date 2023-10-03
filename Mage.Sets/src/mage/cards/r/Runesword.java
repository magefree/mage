
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.replacement.DealtDamageToCreatureBySourceDies;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author L_J
 */
public final class Runesword extends CardImpl {

    public Runesword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");
        
        // {3}, {T}: Target attacking creature gets +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0, Duration.EndOfTurn), new GenericManaCost(3));
        // When that creature leaves the battlefield this turn, sacrifice Runesword.
        ability.addEffect(new RuneswordCreateTriggeredAbilityEffect());
        // If the creature deals damage to a creature this turn, the creature dealt damage can't be regenerated this turn.
        ability.addEffect(new RuneswordCantBeRegeneratedEffect());
        // If a creature dealt damage by the targeted creature would die this turn, exile that creature instead.
        SimpleStaticAbility ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new DealtDamageToCreatureBySourceDies(this, Duration.Custom));
        ability2.addWatcher(new DamagedByWatcher(false));
        ability2.setRuleVisible(false);
        ability.addEffect(new GainAbilityTargetEffect(ability2, Duration.EndOfTurn, null, false).setText(
                "If a creature dealt damage by the targeted creature would die this turn, exile that creature instead"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private Runesword(final Runesword card) {
        super(card);
    }

    @Override
    public Runesword copy() {
        return new Runesword(this);
    }
}

class RuneswordCreateTriggeredAbilityEffect extends OneShotEffect {

    public RuneswordCreateTriggeredAbilityEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "When that creature leaves the battlefield this turn, sacrifice {this}";
    }

    private RuneswordCreateTriggeredAbilityEffect(final RuneswordCreateTriggeredAbilityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent targetObject = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (sourceObject != null && targetObject != null) {
            Effect sacrificeEffect = new SacrificeTargetEffect("sacrifice " + sourceObject.getName());
            sacrificeEffect.setTargetPointer(new FixedTarget(sourceObject, game));
            LeavesBattlefieldTriggeredAbility triggerAbility = new LeavesBattlefieldTriggeredAbility(sacrificeEffect, false);
            triggerAbility.setRuleVisible(false);
            ContinuousEffect continuousEffect = new GainAbilityTargetEffect(triggerAbility, Duration.EndOfTurn);
            continuousEffect.setTargetPointer(new FixedTarget(targetObject, game));
            game.addEffect(continuousEffect, source);
            return true;
        }
        return false;
    }

    @Override
    public RuneswordCreateTriggeredAbilityEffect copy() {
        return new RuneswordCreateTriggeredAbilityEffect(this);
    }
}

class RuneswordCantBeRegeneratedEffect extends ContinuousRuleModifyingEffectImpl {
    
    private UUID targetCreatureId;

    public RuneswordCantBeRegeneratedEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit, false, false);
        this.staticText = "If the creature deals damage to a creature this turn, the creature dealt damage can't be regenerated this turn";
    }

    private RuneswordCantBeRegeneratedEffect(final RuneswordCantBeRegeneratedEffect effect) {
        super(effect);
        targetCreatureId = effect.targetCreatureId;
    }

    @Override
    public RuneswordCantBeRegeneratedEffect copy() {
        return new RuneswordCantBeRegeneratedEffect(this);
    }

    public void init(Ability source, Game game) {
        targetCreatureId = getTargetPointer().getFirst(game, source);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.REGENERATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (targetCreatureId != null) {
            DamagedByWatcher watcher = game.getState().getWatcher(DamagedByWatcher.class, targetCreatureId);
            if (watcher != null) {
                return watcher.wasDamaged(event.getTargetId(), game);
            }
        }
        return false;
    }

}
