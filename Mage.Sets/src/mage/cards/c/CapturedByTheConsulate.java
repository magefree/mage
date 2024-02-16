package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CapturedByTheConsulate extends CardImpl {

    public CapturedByTheConsulate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature you don't control
        TargetPermanent auraTarget = new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAttachedEffect(AttachmentType.AURA)));

        // Whenever an opponent casts a spell, if it has a single target, change the target to enchanted creature if able.
        this.addAbility(new CapturedByTheConsulateTriggeredAbility(Zone.BATTLEFIELD, new CapturedByTheConsulateEffect()));
    }

    private CapturedByTheConsulate(final CapturedByTheConsulate card) {
        super(card);
    }

    @Override
    public CapturedByTheConsulate copy() {
        return new CapturedByTheConsulate(this);
    }
}

class CapturedByTheConsulateTriggeredAbility extends TriggeredAbilityImpl {

    /**
     * @param zone
     * @param effect
     */
    CapturedByTheConsulateTriggeredAbility(Zone zone, Effect effect) {
        super(zone, effect, false);
        setTriggerPhrase("Whenever an opponent casts a spell, if it has a single target, ");
    }

    private CapturedByTheConsulateTriggeredAbility(final CapturedByTheConsulateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        StackObject stackObject = null;
        for (Effect effect : this.getEffects()) {
            stackObject = game.getStack().getStackObject(effect.getTargetPointer().getFirst(game, this));
        }
        if (stackObject != null) {
            int numberOfTargets = 0;
            for (UUID modeId : stackObject.getStackAbility().getModes().getSelectedModes()) {
                Mode mode = stackObject.getStackAbility().getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    numberOfTargets += target.getTargets().size();
                }
            }
            return numberOfTargets == 1;
        }
        return false;
    }

    @Override
    public CapturedByTheConsulateTriggeredAbility copy() {
        return new CapturedByTheConsulateTriggeredAbility(this);
    }
}

class CapturedByTheConsulateEffect extends OneShotEffect {

    CapturedByTheConsulateEffect() {
        super(Outcome.Benefit);
        this.staticText = "change the target to enchanted creature if able";
    }

    private CapturedByTheConsulateEffect(final CapturedByTheConsulateEffect effect) {
        super(effect);
    }

    @Override
    public CapturedByTheConsulateEffect copy() {
        return new CapturedByTheConsulateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceEnchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourceEnchantment != null) {
            StackObject stackObject = game.getStack().getStackObject(getTargetPointer().getFirst(game, source));
            if (stackObject != null) {
                Target target = stackObject.getStackAbility().getTargets().get(0);
                if (target != null) {
                    if (target.canTarget(stackObject.getControllerId(), sourceEnchantment.getAttachedTo(), source, game)) {
                        target.remove(target.getFirstTarget());
                        target.add(sourceEnchantment.getAttachedTo(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
