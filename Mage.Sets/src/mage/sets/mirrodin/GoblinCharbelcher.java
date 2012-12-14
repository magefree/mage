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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Plopman
 */
public class GoblinCharbelcher extends CardImpl<GoblinCharbelcher> {

    public GoblinCharbelcher(UUID ownerId) {
        super(ownerId, 176, "Goblin Charbelcher", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "MRD";

        // {3}, {tap}: Reveal cards from the top of your library until you reveal a land card. Goblin Charbelcher deals damage equal to the number of nonland cards revealed this way to target creature or player. If the revealed land card was a Mountain, Goblin Charbelcher deals double that damage instead. Put the revealed cards on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GoblinCharbelcherEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public GoblinCharbelcher(final GoblinCharbelcher card) {
        super(card);
    }

    @Override
    public GoblinCharbelcher copy() {
        return new GoblinCharbelcher(this);
    }
}

class GoblinCharbelcherEffect extends OneShotEffect<GoblinCharbelcherEffect> {

    public GoblinCharbelcherEffect() {
        super(Outcome.Damage);
        this.staticText = "Reveal cards from the top of your library until you reveal a land card. {this} deals damage equal to the number of nonland cards revealed this way to target creature or player. If the revealed land card was a Mountain, {this} deals double that damage instead. Put the revealed cards on the bottom of your library in any order";
    }

    public GoblinCharbelcherEffect(final GoblinCharbelcherEffect effect) {
        super(effect);
    }

    @Override
    public GoblinCharbelcherEffect copy() {
        return new GoblinCharbelcherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean isMountain = false;
        Card sourceCard = game.getCard(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        
        if (player == null || sourceCard == null) {
            return false;
        }
        Cards cards = new CardsImpl(Constants.Zone.PICK);
        while (player.getLibrary().size() > 0) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if(card.getCardType().contains(CardType.LAND)){
                    if(card.getSubtype().contains("Mountain")){
                        isMountain = true;
                    }
                    break;
                }
            }
            else{
                break;
            }
        }
        player.revealCards(sourceCard.getName(), cards, game);
        int damage = cards.size();
        if(isMountain == true){
            damage *= 2;
        }
        
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            permanent.damage(damage, source.getSourceId(), game, true, false);
        }
        else{
            Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
            if (targetPlayer != null) {
                targetPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
        }
        
        TargetCard target = new TargetCard(Constants.Zone.PICK, new FilterCard("card to put on the bottom of your library"));
        target.setRequired(true);
        while (cards.size() > 1) {
            player.choose(Outcome.Neutral, cards, target, game);
            Card card = cards.get(target.getFirstTarget(), game);
            if (card != null) {
                cards.remove(card);
                card.moveToZone(Constants.Zone.PICK, source.getId(), game, false);
            }
            target.clearChosen();
        }
       
        return true;
    }
}
