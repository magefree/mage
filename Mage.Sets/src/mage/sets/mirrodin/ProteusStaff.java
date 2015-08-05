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
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public class ProteusStaff extends CardImpl {

    public ProteusStaff(UUID ownerId) {
        super(ownerId, 230, "Proteus Staff", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "MRD";

        // {2}{U}, {tap}: Put target creature on the bottom of its owner's library. That creature's controller reveals cards from the top of his or her library until he or she reveals a creature card. The player puts that card onto the battlefield and the rest on the bottom of his or her library in any order. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ProteusStaffEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public ProteusStaff(final ProteusStaff card) {
        super(card);
    }

    @Override
    public ProteusStaff copy() {
        return new ProteusStaff(this);
    }
}

class ProteusStaffEffect extends OneShotEffect {
    
    ProteusStaffEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put target creature on the bottom of its owner's library. That creature's controller reveals cards from the top of his or her library until he or she reveals a creature card. The player puts that card onto the battlefield and the rest on the bottom of his or her library in any order.";
    }
    
    ProteusStaffEffect(final ProteusStaffEffect effect) {
        super(effect);
    }
    
    @Override
    public ProteusStaffEffect copy() {
        return new ProteusStaffEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            Player owner = game.getPlayer(permanent.getOwnerId());
            Player controller = game.getPlayer(permanent.getControllerId());
            if (owner != null && controller != null) {
                // Put target creature on the bottom of its owner's library.
                owner.moveCardToLibraryWithInfo(permanent, source.getSourceId(), game, Zone.BATTLEFIELD, false, true);
                
                // That creature's controller reveals cards from the top of his or her library until he or she reveals a creature card.
                Cards cards = new CardsImpl();
                while (controller.getLibrary().size() > 0) {
                    Card card = controller.getLibrary().removeFromTop(game);
                    if (card != null) {
                        if (card.getCardType().contains(CardType.CREATURE)) {
                            // The player puts that card onto the battlefield
                            controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                            break;
                        }
                        else {
                            cards.add(card);
                        }
                    }
                }
                controller.revealCards("Proteus Staff", cards, game);
                
                // and the rest on the bottom of his or her library in any order.
                while (cards.size() > 0 && controller.canRespond()) {
                    if (cards.size() == 1) {
                        Card card = cards.get(cards.iterator().next(), game);
                        if (card != null) {
                            controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
                            cards.remove(card);
                        }                    
                    }
                    else {
                        TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put on bottom of your library (last chosen will be on bottom)"));
                        controller.choose(Outcome.Neutral, cards, target, game);
                        Card card = cards.get(target.getFirstTarget(), game);
                        if (card != null) {
                            controller.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.LIBRARY, false, false);
                            cards.remove(card);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
