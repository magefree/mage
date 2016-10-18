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
package mage.cards.c;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes
 */
public class CabalInterrogator extends CardImpl {

    public CabalInterrogator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add("Zombie");
        this.subtype.add("Wizard");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}{B}, {tap}: Target player reveals X cards from his or her hand and you choose one of them. That player discards that card.
        // Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new CabalInterrogatorEffect(), new ManaCostsImpl("{X}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public CabalInterrogator(final CabalInterrogator card) {
        super(card);
    }

    @Override
    public CabalInterrogator copy() {
        return new CabalInterrogator(this);
    }
}

class CabalInterrogatorEffect extends OneShotEffect {

    public CabalInterrogatorEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player reveals X cards from his or her hand and you choose one of them. That player discards that card";
    }

    public CabalInterrogatorEffect(final CabalInterrogatorEffect effect) {
        super(effect);
    }

    @Override
    public CabalInterrogatorEffect copy() {
        return new CabalInterrogatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }

        int amountToReveal = (new ManacostVariableValue()).calculate(game, source, this);

        Cards revealedCards = new CardsImpl();
        if (amountToReveal > 0 && targetPlayer.getHand().size() > amountToReveal) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());

            TargetCard target = new TargetCard(amountToReveal, Zone.HAND, new FilterCard());

            if (targetPlayer.choose(Outcome.Discard, cardsInHand, target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        revealedCards.add(card);
                    }
                }
            }
        } else {
            revealedCards.addAll(targetPlayer.getHand());
        }

        TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to discard"));

        if (!revealedCards.isEmpty()) {
            targetPlayer.revealCards("Cabal Interrogator", revealedCards, game);
            Card card = null;
            if(revealedCards.size() > 1) {
                controller.choose(Outcome.Discard, revealedCards, targetInHand, game);
                card = revealedCards.get(targetInHand.getFirstTarget(), game);
            } else {
                card = revealedCards.getRandom(game);
            }

            if (card != null) {
                targetPlayer.discard(card, source, game);
            }
        }
        return true;
    }
}
