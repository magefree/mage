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
package mage.sets.onslaught;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class WordsOfWind extends CardImpl<WordsOfWind> {

    public WordsOfWind(UUID ownerId) {
        super(ownerId, 122, "Words of Wind", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "ONS";

        this.color.setBlue(true);

        // {1}: The next time you would draw a card this turn, each player returns a permanent he or she controls to its owner's hand instead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WordsOfWindEffect(), new ManaCostsImpl("{1}")));
    }

    public WordsOfWind(final WordsOfWind card) {
        super(card);
    }

    @Override
    public WordsOfWind copy() {
        return new WordsOfWind(this);
    }
}

class WordsOfWindEffect extends ReplacementEffectImpl<WordsOfWindEffect> {

    public WordsOfWindEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.ReturnToHand);
        staticText = "The next time you would draw a card this turn, each player returns a permanent he or she controls to its owner's hand instead";
    }

    public WordsOfWindEffect(final WordsOfWindEffect effect) {
        super(effect);
    }

    @Override
    public WordsOfWindEffect copy() {
        return new WordsOfWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        
        game.informPlayers("Each player returns a permanent he or she controls to its owner's hand instead");
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                TargetControlledPermanent target = new TargetControlledPermanent();
                List<Permanent> liste = game.getBattlefield().getActivePermanents(new FilterControlledPermanent(), playerId, game);
                if(!liste.isEmpty()){
                    while (!player.choose(Outcome.ReturnToHand, target, source.getSourceId(), game)){
                        game.debugMessage("player canceled choosing permanent. retrying.");
                    }
                    Permanent permanent = game.getPermanent(target.getFirstTarget());
                    if (permanent != null) {
                        permanent.moveToZone(Zone.HAND, source.getId(), game, false);
                    }
                }
            }
        }
        used = true;
        return apply(game, source);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DRAW_CARD && source.getControllerId().equals(event.getPlayerId()) && used == false) {
            
            return true;
        }
        return false;
    }
}