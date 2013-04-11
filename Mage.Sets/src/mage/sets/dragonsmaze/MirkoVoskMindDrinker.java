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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */


public class MirkoVoskMindDrinker extends CardImpl<MirkoVoskMindDrinker> {

    public MirkoVoskMindDrinker (UUID ownerId) {
        super(ownerId, 85, "Mirko Vosk, Mind Drinker", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.expansionSetCode = "DGM";
        this.subtype.add("Vampire");
        this.supertype.add("Legendary");
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Mirko Vosk, Mind Drinker deals combat damage to a player, that player reveals cards from the top of his or her library until he or she reveals four land cards, then puts those cards into his or her graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MirkoVoskMindDrinkerEffect(), false, true));
    }

    public MirkoVoskMindDrinker (final MirkoVoskMindDrinker card) {
        super(card);
    }

    @Override
    public MirkoVoskMindDrinker copy() {
        return new MirkoVoskMindDrinker(this);
    }

}

class MirkoVoskMindDrinkerEffect extends OneShotEffect<MirkoVoskMindDrinkerEffect> {

    public MirkoVoskMindDrinkerEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player reveals cards from the top of his or her library until he or she reveals four land cards, then puts those cards into his or her graveyard";
    }

    public MirkoVoskMindDrinkerEffect(final MirkoVoskMindDrinkerEffect effect) {
        super(effect);
    }

    @Override
    public MirkoVoskMindDrinkerEffect copy() {
        return new MirkoVoskMindDrinkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }
        int landsToReveal = 4;
        Cards cards = new CardsImpl();
        while(player.getLibrary().size() > 0){
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if(card.getCardType().contains(CardType.LAND)){
                    --landsToReveal;
                    if (landsToReveal < 1) {
                        break;
                    }
                }
            }
        }
        player.revealCards(new StringBuilder("by ").append(sourceCard.getName()).append(" from ").append(player.getName()).toString(), cards, game);
        for(Card card : cards.getCards(game)){
            if(card != null){
                card.moveToZone(Zone.GRAVEYARD, source.getSourceId(), game, false);
            }
        }
        return true;
    }
}
