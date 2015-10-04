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
package mage.sets.archenemy;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BursegSardaukar
 */
public class SkirkCommando extends CardImpl {
    
    public SkirkCommando(UUID ownerId) {
        super(ownerId, 47, "Skirk Commando", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.expansionSetCode = "ARC";
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        
        //Whenever Skirk Commando deals combat damage to a player, you may have it deal 2 damage to target creature that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SkirkCommandoEffect(), true, true));
        
        //Morph {2}{R} (You may cast this card face down as a 2/2 creature for 3. Turn it face up any time for its morph cost.)
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{R}")));
        
    }

    public SkirkCommando(final SkirkCommando card) {
        super(card);
    }

    @Override
    public SkirkCommando copy() {
        return new SkirkCommando(this);
    }
}

class SkirkCommandoEffect extends OneShotEffect {

    public SkirkCommandoEffect() {
        super(Outcome.Damage);
        staticText = "have it deal 2 damage to target creature that player controls";
    }

    public SkirkCommandoEffect(final SkirkCommandoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + player.getLogName() + " controls");
            filter.add(new ControllerIdPredicate(player.getId()));
            TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
            if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Damage, source.getControllerId(), source.getSourceId(), game)) {
                UUID creature = target.getFirstTarget();
                if (creature != null) {
                    game.getPermanent(creature).damage(2, source.getSourceId(), game, false, true);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public SkirkCommandoEffect copy() {
        return new SkirkCommandoEffect(this);
    }
}
