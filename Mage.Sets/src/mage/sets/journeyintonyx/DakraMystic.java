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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class DakraMystic extends CardImpl<DakraMystic> {

    public DakraMystic(UUID ownerId) {
        super(ownerId, 35, "Dakra Mystic", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Merfolk");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {U},{T}:Each player reveals the top card of his or her library. You may put the revealed cards into their owners graveyard. If you don't, each player draws a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DakraMysticEffect(), new ManaCostsImpl("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
        
        
    }

    public DakraMystic(final DakraMystic card) {
        super(card);
    }

    @Override
    public DakraMystic copy() {
        return new DakraMystic(this);
    }
}

class DakraMysticEffect extends OneShotEffect<DakraMysticEffect> {
    
    public DakraMysticEffect() {
        super(Outcome.Detriment);
        this.staticText = "Each player reveals the top card of his or her library. You may put the revealed cards into their owners graveyard. If you don't, each player draws a card";
    }
    
    public DakraMysticEffect(final DakraMysticEffect effect) {
        super(effect);
    }
    
    @Override
    public DakraMysticEffect copy() {
        return new DakraMysticEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for(UUID playerId: controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null && player.getLibrary().size() > 0) {
                    player.revealCards(player.getName(), new CardsImpl(player.getLibrary().getFromTop(game)), game);
                }
            }
            if (controller.chooseUse(outcome, "Put revealed cards into graveyard?", game)) {
                for(UUID playerId: controller.getInRange()) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && player.getLibrary().size() > 0) {
                        controller.moveCardToGraveyardWithInfo(player.getLibrary().getFromTop(game), source.getSourceId(), game, Zone.LIBRARY);
                    }
                }                
            } else {
                new DrawCardAllEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
