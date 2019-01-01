package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CloakOfConfusion extends CardImpl {

    public CloakOfConfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks and isn't blocked, you may have it assign no combat damage this turn. 
        // If you do, defending player discards a card at random.
        this.addAbility(new CloakOfConfusionTriggeredAbility());

    }

    public CloakOfConfusion(final CloakOfConfusion card) {
        super(card);
    }

    @Override
    public CloakOfConfusion copy() {
        return new CloakOfConfusion(this);
    }
}

class CloakOfConfusionTriggeredAbility extends TriggeredAbilityImpl {

    public CloakOfConfusionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CloakOfConfusionEffect(), true);
    }

    public CloakOfConfusionTriggeredAbility(final CloakOfConfusionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CloakOfConfusionTriggeredAbility copy() {
        return new CloakOfConfusionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARE_BLOCKERS_STEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent aura = game.getPermanentOrLKIBattlefield(getSourceId());
        if (aura != null) {
            Permanent enchantedCreature = game.getPermanent(aura.getAttachedTo());
            if (enchantedCreature != null
                    && enchantedCreature.isAttacking()) {
                for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().isEmpty()
                            && combatGroup.getAttackers().contains(enchantedCreature.getId())) {
                        this.getEffects().setTargetPointer(
                                new FixedTarget(game.getCombat().getDefendingPlayerId(
                                        enchantedCreature.getId(), game)));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature attacks and isn't blocked, " + super.getRule();
    }
}

class CloakOfConfusionEffect extends OneShotEffect {

    public CloakOfConfusionEffect() {
        super(Outcome.Neutral);
        this.staticText = "you may have it assign no combat damage this turn. "
                + "If you do, defending player discards a card at random";
    }

    public CloakOfConfusionEffect(final CloakOfConfusionEffect effect) {
        super(effect);
    }

    @Override
    public CloakOfConfusionEffect copy() {
        return new CloakOfConfusionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantedCreature = game.getPermanent(game.getPermanent(source.getSourceId()).getAttachedTo());
        if (controller != null && controller.chooseUse(outcome, "Do you wish to not assign combat damage from "
                + enchantedCreature.getName() + " and have the defending player discard a card at random?", source, game)) {
            ContinuousEffect effect = new AssignNoCombatDamageTargetEffect();
            effect.setTargetPointer(new FixedTarget(enchantedCreature.getId()));
            game.addEffect(effect, source);
            Player defendingPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (defendingPlayer != null) {
                defendingPlayer.discard(1, true, source, game);
            }
            return true;
        }

        return false;
    }
}

class AssignNoCombatDamageTargetEffect extends ReplacementEffectImpl {

    public AssignNoCombatDamageTargetEffect() {
        super(Duration.EndOfTurn, Outcome.Neutral);
    }

    public AssignNoCombatDamageTargetEffect(final AssignNoCombatDamageTargetEffect effect) {
        super(effect);
    }

    @Override
    public AssignNoCombatDamageTargetEffect copy() {
        return new AssignNoCombatDamageTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        return event.getSourceId().equals(targetPointer.getFirst(game, source))
                && damageEvent.isCombatDamage();
    }
}
