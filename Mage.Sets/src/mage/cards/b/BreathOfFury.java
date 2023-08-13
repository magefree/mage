
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CanBeEnchantedByPredicate;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author duncant
 */
public final class BreathOfFury extends CardImpl {

    public BreathOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature deals combat damage to a player, sacrifice it and attach Breath of Fury to a creature you control. If you do, untap all creatures you control and after this phase, there is an additional combat phase.
        this.addAbility(new BreathOfFuryAbility());
    }

    private BreathOfFury(final BreathOfFury card) {
        super(card);
    }

    @Override
    public BreathOfFury copy() {
        return new BreathOfFury(this);
    }
}

class BreathOfFuryAbility extends TriggeredAbilityImpl {

    public BreathOfFuryAbility() {
        super(Zone.BATTLEFIELD, new BreathOfFuryEffect());
        setTriggerPhrase("When enchanted creature deals combat damage to a player, ");
    }

    public BreathOfFuryAbility(final BreathOfFuryAbility ability) {
        super(ability);
    }

    @Override
    public BreathOfFuryAbility copy() {
        return new BreathOfFuryAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent enchantment = game.getPermanent(getSourceId());
        if (damageEvent.isCombatDamage()
                && enchantment != null
                && enchantment.isAttachedTo(event.getSourceId())) {
            Permanent creature = game.getPermanent(enchantment.getAttachedTo());
            if (creature != null) {
                for (Effect effect : getEffects()) {
                    effect.setValue("TriggeringCreatureId", creature.getId());
                }
                return true;
            }
        }
        return false;
    }
}

class BreathOfFuryEffect extends OneShotEffect {

    public BreathOfFuryEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice it and attach {this} to a creature you control. If you do, untap all creatures you control and after this phase, there is an additional combat phase";
    }

    public BreathOfFuryEffect(final BreathOfFuryEffect effect) {
        super(effect);
    }

    @Override
    public BreathOfFuryEffect copy() {
        return new BreathOfFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            return false;
        }
        Permanent enchantedCreature = game.getPermanent((UUID) getValue("TriggeringCreatureId"));
        Player controller = game.getPlayer(source.getControllerId());
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control that could be enchanted by " + enchantment.getName());
        filter.add(new CanBeEnchantedByPredicate(enchantment));
        Target target = new TargetControlledCreaturePermanent(filter);
        target.setNotTarget(true);
        // It's important to check that the creature was successfully sacrificed here. Effects that prevent sacrifice will also prevent Breath of Fury's effect from working.
        // Commanders going to the command zone and Rest in Peace style replacement effects don't make Permanent.sacrifice return false.
        if (enchantedCreature != null && controller != null
                && enchantedCreature.sacrifice(source, game)
                && target.canChoose(controller.getId(), source, game)) {
            controller.choose(outcome, target, source, game);
            Permanent newCreature = game.getPermanent(target.getFirstTarget());
            boolean success = false;
            if (newCreature != null) {
                Permanent oldCreature = game.getPermanent(enchantment.getAttachedTo());
                if (oldCreature != null) {
                    if (oldCreature.getId().equals(newCreature.getId())) {
                        success = true;
                    } else {
                        if (oldCreature.removeAttachment(enchantment.getId(), source, game)
                                && newCreature.addAttachment(enchantment.getId(), source, game)) {
                            game.informPlayers(enchantment.getLogName() + " was unattached from " + oldCreature.getLogName() + " and attached to " + newCreature.getLogName());
                            success = true;
                        }
                    }
                } else if (newCreature.addAttachment(enchantment.getId(), source, game)) {
                    game.informPlayers(enchantment.getLogName() + " was attached to " + newCreature.getLogName());
                    success = true;
                }
            }
            if (success) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(new FilterControlledCreaturePermanent(), controller.getId(), game)) {
                    permanent.untap(game);
                }
                game.getState().getTurnMods().add(new TurnMod(source.getControllerId()).withExtraPhase(TurnPhase.COMBAT));
            }
            return true;
        }
        return false;
    }
}
