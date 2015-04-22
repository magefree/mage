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
package mage.sets.futuresight;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.GrandeurAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class OrissSamiteGuardian extends CardImpl {

    public OrissSamiteGuardian(UUID ownerId) {
        super(ownerId, 28, "Oriss, Samite Guardian", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.expansionSetCode = "FUT";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Prevent all damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventDamageToTargetEffect(Duration.EndOfTurn, Integer.MAX_VALUE), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Grandeur - Discard another card named Oriss, Samite Guardian: Target player can't cast spells this turn, and creatures that player controls can't attack this turn.
        ability = new GrandeurAbility(new OrissSamiteGuardianCantCastEffect(), "Oriss, Samite Guardian");
        ability.addEffect(new OrissSamiteGuardianCantAttackEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public OrissSamiteGuardian(final OrissSamiteGuardian card) {
        super(card);
    }

    @Override
    public OrissSamiteGuardian copy() {
        return new OrissSamiteGuardian(this);
    }
}

class OrissSamiteGuardianCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    OrissSamiteGuardianCantCastEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't cast spells this turn";
    }

    OrissSamiteGuardianCantCastEffect(final OrissSamiteGuardianCantCastEffect effect) {
        super(effect);
    }

    @Override
    public OrissSamiteGuardianCantCastEffect copy() {
        return new OrissSamiteGuardianCantCastEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && player.getId().equals(event.getPlayerId());
    }
}

class OrissSamiteGuardianCantAttackEffect extends ContinuousRuleModifyingEffectImpl {

    OrissSamiteGuardianCantAttackEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = ", and creatures that player controls can't attack this turn";
    }

    OrissSamiteGuardianCantAttackEffect(final OrissSamiteGuardianCantAttackEffect effect) {
        super(effect);
    }

    @Override
    public OrissSamiteGuardianCantAttackEffect copy() {
        return new OrissSamiteGuardianCantAttackEffect(this);
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARE_ATTACKER;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        return player != null && player.getId().equals(event.getPlayerId());
    }
}