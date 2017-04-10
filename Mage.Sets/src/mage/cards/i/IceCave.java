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
package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;

import java.util.UUID;

/**
 *
 * @author ThomasLerner
 */
public class IceCave extends CardImpl {

    public IceCave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");


        // Whenever a player casts a spell, any other player may pay that spell's mana cost. If a player does, counter the spell. (Mana cost includes color.)
        this.addAbility(new SpellCastAllTriggeredAbility(Zone.BATTLEFIELD, new IceCaveEffect(), new FilterSpell(), false, SetTargetPointer.SPELL));
    }

    public IceCave(final IceCave card) {
        super(card);
    }

    @Override
    public IceCave copy() {
        return new IceCave(this);
    }
}

class IceCaveEffect extends OneShotEffect {

    public IceCaveEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "any other player may pay that spell's mana cost. If a player does, counter the spell";
    }

    public IceCaveEffect(final IceCaveEffect effect) {
        super(effect);
    }

    @Override
    public IceCaveEffect copy() {
        return new IceCaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Spell spell = (Spell) game.getStack().getStackObject(targetPointer.getFirst(game, source));
        if(sourcePermanent != null && spell != null && controller != null) {
            Player spellController = game.getPlayer(spell.getControllerId());
            Cost cost = new ManaCostsImpl(spell.getSpellAbility().getManaCosts().getText());
            if(spellController != null) {
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if(player != null && player != spellController) {
                        cost.clearPaid();
                        if(cost.canPay(source, source.getSourceId(), player.getId(), game)
                                && player.chooseUse(outcome, "Pay " + cost.getText() + " to counter " + spell.getIdName() + '?', source, game)) {
                            if(cost.pay(source, game, source.getSourceId(), playerId, false, null)) {
                                game.informPlayers(player.getLogName() + " pays" + cost.getText() + " to counter " + spell.getIdName() + '.');
                                game.getStack().counter(spell.getId(), source.getSourceId(), game);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
