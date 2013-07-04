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
package mage.sets.championsofkamigawa;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Library;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Reweave extends CardImpl<Reweave> {

    public Reweave(UUID ownerId) {
        super(ownerId, 82, "Reweave", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{5}{U}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");

        this.color.setBlue(true);

        // Target permanent's controller sacrifices it. If he or she does, that player reveals cards from the top of his or her library until he or she reveals a permanent card that shares a card type with the sacrificed permanent, puts that card onto the battlefield, then shuffles his or her library.
        this.getSpellAbility().addEffect(new ReweaveEffect());
        Target target = new TargetPermanent();
        target.setRequired(true);
        this.getSpellAbility().addTarget(target);

        // Splice onto Arcane {2}{U}{U}
        this.addAbility(new SpliceOntoArcaneAbility("{2}{U}{U}"));
    }

    public Reweave(final Reweave card) {
        super(card);
    }

    @Override
    public Reweave copy() {
        return new Reweave(this);
    }
}

class ReweaveEffect extends OneShotEffect<ReweaveEffect> {

    public ReweaveEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target permanent's controller sacrifices it. If he or she does, that player reveals cards from the top of his or her library until he or she reveals a permanent card that shares a card type with the sacrificed permanent, puts that card onto the battlefield, then shuffles his or her library";
    }

    public ReweaveEffect(final ReweaveEffect effect) {
        super(effect);
    }

    @Override
    public ReweaveEffect copy() {
        return new ReweaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().getFirstTarget());
        if ( permanent != null ) {
            if (permanent.sacrifice(this.getId(), game)) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null) {
                    Library library = player.getLibrary();
                    if (library.size() > 0) {
                        Cards cards = new CardsImpl();
                        Card card = null;
                        boolean cardFound = false;
                        if (library.size() > 0) {
                            do  {
                                card = library.removeFromTop(game);
                                cards.add(card);
                                for (CardType cardType : permanent.getCardType()) {
                                    if (card.getCardType().contains(cardType)) {
                                        // a permanent card
                                        if (!card.getCardType().contains(CardType.INSTANT) && !card.getCardType().contains(CardType.SORCERY)) {
                                            cardFound = true;
                                            break;
                                        }
                                    }
                                }
                            } while (!cardFound && library.size() > 0);
                            card.putOntoBattlefield(game, Zone.PICK, source.getId(), player.getId());
                        }
                        
                        if (cards.size() > 0) {
                            player.revealCards("Reweave", cards, game);
                            Set<Card> cardsToShuffle = cards.getCards(game);
                            if (card != null) {
                                cardsToShuffle.remove(card);
                            }
                            library.addAll(cardsToShuffle, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
