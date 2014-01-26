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
package mage.sets.bornofthegods;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ScryEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class Peregrination extends CardImpl<Peregrination> {

    public Peregrination(UUID ownerId) {
        super(ownerId, 132, "Peregrination", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{G}");
        this.expansionSetCode = "BNG";

        this.color.setGreen(true);

        // Seach your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle your library, then scry 1.
        this.getSpellAbility().addEffect(new PeregrinationEffect());
        Effect effect = new ScryEffect(1);
        effect.setText("then scry 1  <i>(Look at the top card of your library. You may put that card on the bottom of your library.)</i>");
        this.getSpellAbility().addEffect(effect);
    }

    public Peregrination(final Peregrination card) {
        super(card);
    }

    @Override
    public Peregrination copy() {
        return new Peregrination(this);
    }
}

class PeregrinationEffect extends OneShotEffect<PeregrinationEffect> {

    protected static final FilterCard filter = new FilterCard("card to put on the battlefield tapped");

    public PeregrinationEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for up to two basic land cards, reveal those cards, and put one onto the battlefield tapped and the other into your hand. Shuffle your library";
    }

    public PeregrinationEffect(final PeregrinationEffect effect) {
        super(effect);
    }

    @Override
    public PeregrinationEffect copy() {
        return new PeregrinationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, new FilterBasicLandCard());
        Player player = game.getPlayer(source.getControllerId());
        if (player.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                Cards revealed = new CardsImpl();
                for (UUID cardId: (List<UUID>)target.getTargets()) {
                    Card card = player.getLibrary().getCard(cardId, game);
                    revealed.add(card);
                }
                player.revealCards("Peregrination", revealed, game);
                if (target.getTargets().size() == 2) {
                    TargetCard target2 = new TargetCard(Zone.PICK, filter);
                    target2.setRequired(true);
                    player.choose(Outcome.Benefit, revealed, target2, game);
                    Card card = revealed.get(target2.getFirstTarget(), game);

                    player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());

                    revealed.remove(card);
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        permanent.setTapped(true);
                    }
                    card = revealed.getCards(game).iterator().next();
                    player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                }
                else if (target.getTargets().size() == 1) {
                    Card card = revealed.getCards(game).iterator().next();
                    player.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                    Permanent permanent = game.getPermanent(card.getId());
                    if (permanent != null) {
                        permanent.setTapped(true);
                    }
                }

            }
            player.shuffleLibrary(game);
            return true;
        }
        player.shuffleLibrary(game);
        return false;

    }

}
