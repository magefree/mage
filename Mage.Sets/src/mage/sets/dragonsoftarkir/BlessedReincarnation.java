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
package mage.sets.dragonsoftarkir;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ReboundAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class BlessedReincarnation extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BlessedReincarnation(UUID ownerId) {
        super(ownerId, 47, "Blessed Reincarnation", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{3}{U}");
        this.expansionSetCode = "DTK";

        // Exile target creature an opponent controls. 
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        
        // That player reveals cards from the top of his or her library until a creature card is revealed. 
        // The player puts that card onto the battlefield, then shuffles the rest into his or her library.
        this.getSpellAbility().addEffect(new BlessedReincarnationEffect());
        
        // Rebound
        this.addAbility(new ReboundAbility());
    }

    public BlessedReincarnation(final BlessedReincarnation card) {
        super(card);
    }

    @Override
    public BlessedReincarnation copy() {
        return new BlessedReincarnation(this);
    }
}

class BlessedReincarnationEffect extends OneShotEffect {

    public BlessedReincarnationEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "That player reveals cards from the top of his or her library until a creature card is revealed. The player puts that card onto the battlefield, then shuffles the rest into his or her library";
    }

    public BlessedReincarnationEffect(final BlessedReincarnationEffect effect) {
        super(effect);
    }

    @Override
    public BlessedReincarnationEffect copy() {
        return new BlessedReincarnationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        }

        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                Library library = player.getLibrary();
                if (library.size() > 0) {
                    Cards cards = new CardsImpl();
                    Card card = library.removeFromTop(game);
                    cards.add(card);
                    while (!card.getCardType().contains(CardType.CREATURE) && library.size() > 0) {
                        card = library.removeFromTop(game);
                        cards.add(card);
                    }

                    if (card.getCardType().contains(CardType.CREATURE)) {
                        card.putOntoBattlefield(game, Zone.PICK, source.getSourceId(), player.getId());
                    }

                    if (cards.size() > 0) {
                        player.revealCards("BlessedReincarnation", cards, game);
                        Set<Card> cardsToShuffle = cards.getCards(game);
                        cardsToShuffle.remove(card);
                        library.addAll(cardsToShuffle, game);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
