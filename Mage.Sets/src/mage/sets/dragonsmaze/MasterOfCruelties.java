/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CanAttackOnlyAloneAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class MasterOfCruelties extends CardImpl<MasterOfCruelties> {

    public MasterOfCruelties(UUID ownerId) {
        super(ownerId, 82, "Master of Cruelties", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{R}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Demon");

        this.color.setRed(true);
        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
        // Master of Cruelties can only attack alone.
        this.addAbility(CanAttackOnlyAloneAbility.getInstance());

        // Whenever Master of Cruelties attacks a player and isn't blocked, that player's life total becomes 1. Master of Cruelties assigns no combat damage this combat.
        this.addAbility(new MasterOfCrueltiesTriggeredAbility());

    }

    public MasterOfCruelties(final MasterOfCruelties card) {
        super(card);
    }

    @Override
    public MasterOfCruelties copy() {
        return new MasterOfCruelties(this);
    }
}

class MasterOfCrueltiesTriggeredAbility extends TriggeredAbilityImpl<MasterOfCrueltiesTriggeredAbility> {

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
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_BLOCKERS) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent.isAttacking()) {
                for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                    if (combatGroup.getBlockers().isEmpty()) {
                        Player defendingPlayer = game.getPlayer(combatGroup.getDefenderId());
                        if (defendingPlayer != null) {
                            return true;
                        }
                    }

                }
            }

        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Master of Cruelties attacks a player and isn't blocked, " + super.getRule();
    }
}

class MasterOfCrueltiesEffect extends OneShotEffect<MasterOfCrueltiesEffect> {

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
        Player defendingPlayer = game.getPlayer(game.getCombat().getDefendingPlayer(source.getSourceId()));
        if (defendingPlayer != null) {
            defendingPlayer.setLife(1, game);
            return true;
        }
        return false;
    }
}

class MasterOfCrueltiesNoDamageEffect extends ReplacementEffectImpl<MasterOfCrueltiesNoDamageEffect> {

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
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
            case DAMAGE_PLANESWALKER:
                DamageEvent damageEvent = (DamageEvent) event;
                if (event.getSourceId().equals(source.getSourceId()) && damageEvent.isCombatDamage()) {
                    return true;
                }

                return event.getFlag();
            default:
                return false;
        }
    }
}
