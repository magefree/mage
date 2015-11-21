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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author nigelzor
 */
public class ErraticExplosion extends CardImpl {

    public ErraticExplosion(UUID ownerId) {
        super(ownerId, 41, "Erratic Explosion", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{2}{R}");
        this.expansionSetCode = "PC2";

        // Choose target creature or player. Reveal cards from the top of your library until you reveal a nonland card. Erratic Explosion deals damage equal to that card's converted mana cost to that creature or player. Put the revealed cards on the bottom of your library in any order.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        this.getSpellAbility().addEffect(new ErraticExplosionEffect());
    }

    public ErraticExplosion(final ErraticExplosion card) {
        super(card);
    }

    @Override
    public ErraticExplosion copy() {
        return new ErraticExplosion(this);
    }
}

class ErraticExplosionEffect extends OneShotEffect {

    public ErraticExplosionEffect() {
        super(Outcome.Damage);
        this.staticText = "Choose target creature or player. Reveal cards from the top of your library until you reveal a nonland card. {this} deals damage equal to that card's converted mana cost to that creature or player. Put the revealed cards on the bottom of your library in any order";
    }

    public ErraticExplosionEffect(ErraticExplosionEffect effect) {
        super(effect);
    }

    @Override
    public ErraticExplosionEffect copy() {
        return new ErraticExplosionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            CardsImpl toReveal = new CardsImpl();
            boolean nonLandFound = false;
            Card nonLandCard = null;

            while (nonLandFound && controller.getLibrary().size() > 0) {
                nonLandCard = controller.getLibrary().removeFromTop(game);
                toReveal.add(nonLandCard);
                nonLandFound = nonLandCard.getCardType().contains(CardType.LAND);
            }
            // reveal cards
            if (!toReveal.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), toReveal, game);
            }
            // the nonland card
            if (nonLandCard != null) {
                Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (targetCreature != null) {
                    targetCreature.damage(nonLandCard.getManaCost().convertedManaCost(), source.getSourceId(), game, false, true);
                } else {
                    Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
                    if (targetPlayer != null) {
                        targetPlayer.damage(nonLandCard.getManaCost().convertedManaCost(), source.getSourceId(), game, false, true);
                    }
                }
            }
            // put the cards on the bottom of the library in any order
            return controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);
        }
        return false;
    }
}
