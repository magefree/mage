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
package mage.sets.morningtide;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public class CreamOfTheCrop extends CardImpl {

    public CreamOfTheCrop(UUID ownerId) {
        super(ownerId, 117, "Cream of the Crop", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");
        this.expansionSetCode = "MOR";


        // Whenever a creature enters the battlefield under your control, you may look at the top X cards of your library, where X is that creature's power. If you do, put one of those cards on top of your library and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new CreamOfTheCropEffect(), 
                new FilterCreaturePermanent(), true, SetTargetPointer.PERMANENT,
                "Whenever a creature enters the battlefield under your control, you may look at the top X cards of your library, where X is that creature's power. If you do, put one of those cards on top of your library and the rest on the bottom of your library in any order"));
    }

    public CreamOfTheCrop(final CreamOfTheCrop card) {
        super(card);
    }

    @Override
    public CreamOfTheCrop copy() {
        return new CreamOfTheCrop(this);
    }
}

class CreamOfTheCropEffect extends OneShotEffect {
    
    CreamOfTheCropEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top X cards of your library, where X is that creature's power. If you do, put one of those cards on top of your library and the rest on the bottom of your library in any order";
    }
    
    CreamOfTheCropEffect(final CreamOfTheCropEffect effect) {
        super(effect);
    }
    
    @Override
    public CreamOfTheCropEffect copy() {
        return new CreamOfTheCropEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentOrLKIBattlefield(this.getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null) {
            int numLooked = Math.min(permanent.getPower().getValue(), player.getLibrary().size());
            if (numLooked > 0) {
                Cards cards = new CardsImpl();
                for (int i = 0; i < numLooked; i++) {
                    cards.add(player.getLibrary().removeFromTop(game));
                }
                TargetCard target = new TargetCardInHand(new FilterCard("card to put on top of your library"));
                player.choose(Outcome.Benefit, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                cards.remove(card);
                player.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, true, false);
                player.putCardsOnBottomOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }
}
