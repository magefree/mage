/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class UndercityInformer extends CardImpl {

    public UndercityInformer(UUID ownerId) {
        super(ownerId, 82, "Undercity Informer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "GTC";
        
        this.subtype.add("Human");
        this.subtype.add("Rogue");

        
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        //{1}, Sacrifice a creature: Target player reveals the top card of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UndercityInformerEffect(), new ManaCostsImpl("{1}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public UndercityInformer(final UndercityInformer card) {
        super(card);
    }

    @Override
    public UndercityInformer copy() {
        return new UndercityInformer(this);
    }
}


class UndercityInformerEffect extends OneShotEffect {

    public UndercityInformerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Target player reveals the top card of his or her library until he or she reveals a land card, then puts those cards into his or her graveyard";
    }

    public UndercityInformerEffect(final UndercityInformerEffect effect) {
        super(effect);
    }

    @Override
    public UndercityInformerEffect copy() {
        return new UndercityInformerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        
        Cards cards = new CardsImpl();
        while(player.getLibrary().size() > 0){
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                cards.add(card);
                if(card.getCardType().contains(CardType.LAND)){
                    break;
                }
            }
        }
        player.revealCards("Undercity Informer", cards, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
