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
package mage.sets.exodus;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class Manabond extends CardImpl<Manabond> {

    public Manabond(UUID ownerId) {
        super(ownerId, 113, "Manabond", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "EXO";

        this.color.setGreen(true);

        // At the beginning of your end step, you may reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new ManabondEffect(), true));
    }

    public Manabond(final Manabond card) {
        super(card);
    }

    @Override
    public Manabond copy() {
        return new Manabond(this);
    }
}


class ManabondEffect extends OneShotEffect<ManabondEffect> {

    public ManabondEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand";
    }

    public ManabondEffect(final ManabondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.revealCards("Manabond", player.getHand(), game);

            Cards hand = player.getHand().copy();
            for(UUID uuid : hand){
                Card card = game.getCard(uuid);
                if(card != null){
                    if(card.getCardType().contains(CardType.LAND)){
                        card.moveToZone(Zone.BATTLEFIELD, source.getId(), game, false);
                    }
                    else{
                        player.discard(card, source, game);
                    }
                }
            }
            
        }   
        return false;
    }

    @Override
    public ManabondEffect copy() {
        return new ManabondEffect(this);
    }
}