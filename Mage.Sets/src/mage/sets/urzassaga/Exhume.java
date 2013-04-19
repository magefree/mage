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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public class Exhume extends CardImpl<Exhume> {

    public Exhume(UUID ownerId) {
        super(ownerId, 134, "Exhume", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{B}");
        this.expansionSetCode = "USG";

        this.color.setBlack(true);

        // Each player puts a creature card from his or her graveyard onto the battlefield.
        this.getSpellAbility().addEffect(new ExhumeEffect());
    }

    public Exhume(final Exhume card) {
        super(card);
    }

    @Override
    public Exhume copy() {
        return new Exhume(this);
    }
}

class ExhumeEffect extends OneShotEffect<ExhumeEffect> {

    public ExhumeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player puts a creature card from his or her graveyard onto the battlefield";
    }

    public ExhumeEffect(final ExhumeEffect effect) {
        super(effect);
    }

    @Override
    public ExhumeEffect copy() {
        return new ExhumeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : controller.getInRange()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterCreatureCard filterCreatureCard = new FilterCreatureCard("creature card from your graveyard");
                TargetCardInYourGraveyard target = new TargetCardInYourGraveyard(filterCreatureCard);
                if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getId(), player.getId());
                    }
                }
            }
        }
        return true;
    }
}
