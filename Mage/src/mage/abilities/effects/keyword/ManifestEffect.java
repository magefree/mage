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
package mage.abilities.effects.keyword;

import java.util.Set;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect;
import mage.abilities.effects.common.continuous.BecomesFaceDownCreatureEffect.FaceDownType;
import mage.cards.Card;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class ManifestEffect extends OneShotEffect {

    private final int amount;

    public ManifestEffect(int amount) {
        super(Outcome.PutCreatureInPlay);
        this.amount = amount;
        this.staticText = setText();
    }

    public ManifestEffect(final ManifestEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public ManifestEffect copy() {
        return new ManifestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Ability newSource = source.copy();
            newSource.setWorksFaceDown(true);
            Set<Card> cards = controller.getLibrary().getTopCards(game, amount);
            for (Card card : cards) {
                ManaCosts manaCosts = null;
                if (card.getCardType().contains(CardType.CREATURE)) {
                    manaCosts = card.getSpellAbility().getManaCosts();
                    if (manaCosts == null) {
                        manaCosts = new ManaCostsImpl("{0}");
                    }
                }
                MageObjectReference objectReference = new MageObjectReference(card.getId(), card.getZoneChangeCounter(game) + 1, game);
                game.addEffect(new BecomesFaceDownCreatureEffect(manaCosts, objectReference, Duration.Custom, FaceDownType.MANIFESTED), newSource);
                controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, newSource.getSourceId(), false, true);
                Permanent permanent = game.getPermanent(card.getId());
                if (permanent != null) {
                    permanent.setManifested(true);
                }
            }
            game.applyEffects(); // to apply before ETB triggered or replace Effects are executed
            return true;
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("manifest the top ");
        if (amount > 1) {
            sb.append(CardUtil.numberToText(amount)).append(" cards ");
        } else {
            sb.append("card ");
        }
        sb.append("of your library. ");
        if (amount > 1) {
            sb.append("<i>(To manifest a card, put it onto the battlefield face down as a 2/2 creature. You may turn it face up at any time for its mana cost if it's a creature card.)</i>");
        } else {
            sb.append("<i>(Put it onto the battlefield face down as a 2/2 creature. Turn it face up at any time for its mana cost if it's a creature card.)</i>");
        }
        return sb.toString();
    }
}
