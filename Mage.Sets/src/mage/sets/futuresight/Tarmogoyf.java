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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class Tarmogoyf extends CardImpl<Tarmogoyf> {

    public Tarmogoyf(UUID ownerId) {
        super(ownerId, 153, "Tarmogoyf", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.expansionSetCode = "FUT";
        this.subtype.add("Lhurgoyf");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Tarmogoyf's power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TarmogoyfEffect()));
    }

    public Tarmogoyf(final Tarmogoyf card) {
        super(card);
    }

    @Override
    public Tarmogoyf copy() {
        return new Tarmogoyf(this);
    }
}


class TarmogoyfEffect extends ContinuousEffectImpl<TarmogoyfEffect> {


    public TarmogoyfEffect() {
        super(Duration.EndOfGame, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "{this}'s power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1";
    }


    public TarmogoyfEffect(final TarmogoyfEffect effect) {
        super(effect);
    }

    @Override
    public TarmogoyfEffect copy() {
        return new TarmogoyfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject target = game.getObject(source.getSourceId());
        if (target != null) {
            int number = 0;
            Cards cards = new CardsImpl();
            for(Player player : game.getPlayers().values()){
                if(player != null){
                    cards.addAll(player.getGraveyard());
                }
            }
            
            for(CardType cardtype : CardType.values())
            {
                for(UUID uuid : cards)
                {
                    Card card = game.getCard(uuid);
                    if(card.getCardType().contains(cardtype)){
                        number++;
                        break;
                    }
                }
            }
            target.getPower().setValue(number);
            target.getToughness().setValue(number + 1);
            return true;
            
        }
        return false;
    }

}
