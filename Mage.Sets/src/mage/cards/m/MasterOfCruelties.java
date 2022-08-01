
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CanAttackOnlyAloneAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MasterOfCruelties extends CardImpl {

    public MasterOfCruelties(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Master of Cruelties can only attack alone.
        this.addAbility(new CanAttackOnlyAloneAbility());

        // Whenever Master of Cruelties attacks a player and isn't blocked, that player's life total becomes 1. Master of Cruelties assigns no combat damage this combat.
        this.addAbility(new MasterOfCrueltiesTriggeredAbility());

    }

    private MasterOfCruelties(final MasterOfCruelties card) {
        super(card);
    }

    @Override
    public MasterOfCruelties copy() {
        return new MasterOfCruelties(this);
    }
}

class MasterOfCrueltiesTriggeredAbility extends TriggeredAbilityImpl {

    public MasterOfCrueltiesTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MasterOfCrueltiesEffect());
        this.addEffect(new MasterOfCrueltiesNoDamageEffect());
    }

    public MasterOfCrueltiesTriggeredAbility(final MasterOfCrueltiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MasterOfCrueltiesTriggeredAbility copy() {
        return new MasterOfCrueltiesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_BLOCKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(getSourceId());
        if (sourcePermanent == null) {
            return false;
        }
        if (sourcePermanent.isAttacking()) {
            for (CombatGroup combatGroup : game.getCombat().getGroups()) {
                if (combatGroup.getBlockers().isEmpty() && combatGroup.getAttackers().contains(getSourceId())) {
                    // check if a player is attacked (instead of a planeswalker)
                    Player defendingPlayer = game.getPlayer(combatGroup.getDefenderId());
                    if (defendingPlayer != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever {this} attacks a player and isn't blocked, ";
    }
}

class MasterOfCrueltiesEffect extends OneShotEffect {

    public MasterOfCrueltiesEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player's life total becomes 1";
    }

    public MasterOfCrueltiesEffect(final MasterOfCrueltiesEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfCrueltiesEffect copy() {
        return new MasterOfCrueltiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player defendingPlayer = game.getPlayer(game.getCombat().getDefenderId(source.getSourceId()));
        if (defendingPlayer != null) {
            defendingPlayer.setLife(1, game, source);
            return true;
        }
        return false;
    }
}

class MasterOfCrueltiesNoDamageEffect extends ContinuousRuleModifyingEffectImpl {

    public MasterOfCrueltiesNoDamageEffect() {
        super(Duration.EndOfCombat, Outcome.PreventDamage);
        staticText = "{this} assigns no combat damage this combat";
    }

    public MasterOfCrueltiesNoDamageEffect(final MasterOfCrueltiesNoDamageEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfCrueltiesNoDamageEffect copy() {
        return new MasterOfCrueltiesNoDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        return event.getSourceId().equals(source.getSourceId()) && damageEvent.isCombatDamage();

    }
}
