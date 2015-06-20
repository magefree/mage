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
package mage.sets.magicorigins;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageSelfEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ChandraRoaringFlame extends CardImpl {

    public ChandraRoaringFlame(UUID ownerId) {
        super(ownerId, 135, "Chandra, Roaring Flame", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "");
        this.expansionSetCode = "ORI";
        this.subtype.add("Chandra");
        this.color.setRed(true);
        
        this.nightCard = true;
        this.canTransform = true;
        
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));
        
        // +1: Chandra, Roaring Flame deals 2 damage to target player.
        LoyaltyAbility loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(2), 1);
        loyaltyAbility.addTarget(new TargetPlayer());
        this.addAbility(loyaltyAbility);        
                
        //-2: Chandra, Roaring Flame deals 2 damage to target creature.
        loyaltyAbility = new LoyaltyAbility(new DamageTargetEffect(2), -2);
        loyaltyAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(loyaltyAbility);        
        
        //-7: Chandra, Roaring Flame deals 6 damage to each opponent.  Each player dealt damage this way gets an emblem with "At the beginning of your upkeep, this emblem deals 3 damage to you."
        this.addAbility(new LoyaltyAbility(new ChandraRoaringFlameEmblemEffect(), -7));
        

    }

    public ChandraRoaringFlame(final ChandraRoaringFlame card) {
        super(card);
    }

    @Override
    public ChandraRoaringFlame copy() {
        return new ChandraRoaringFlame(this);
    }
}

class ChandraRoaringFlameEmblemEffect extends OneShotEffect {
    
    public ChandraRoaringFlameEmblemEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 6 damage to each opponent.  Each player dealt damage this way gets an emblem with \"At the beginning of your upkeep, this emblem deals 3 damage to you.\"";
    }
    
    public ChandraRoaringFlameEmblemEffect(final ChandraRoaringFlameEmblemEffect effect) {
        super(effect);
    }
    
    @Override
    public ChandraRoaringFlameEmblemEffect copy() {
        return new ChandraRoaringFlameEmblemEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<Player> opponentsEmblem = new ArrayList<>();
            for(UUID playerId: game.getOpponents(controller.getId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null) {
                    if (opponent.damage(6, source.getSourceId(), game, false, true) > 0) {
                        opponentsEmblem.add(opponent);
                    }
                }
            }
            for (Player opponent : opponentsEmblem) {
                game.addEmblem(new ChandraRoaringFlameEmblem(), source, opponent.getId());
            }
        }
        return false;
    }
}

/**
 * Emblem with "At the beginning of your upkeep, this emblem deals 3 damage to you."
 */
class ChandraRoaringFlameEmblem extends Emblem {

    public ChandraRoaringFlameEmblem() {
        setName("EMBLEM: Chandra, Roaring Flame");
        Effect effect = new DamageTargetEffect(3);
        effect.setText("this emblem deals 3 damage to you");
        this.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND, effect, TargetController.YOU, false, true));
    }
}