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
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class AethermagesTouch extends CardImpl {

    public AethermagesTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}{U}");


        // Reveal the top four cards of your library. You may put a creature card from among them onto the battlefield. It gains "At the beginning of your end step, return this creature to its owner's hand." Then put the rest of the cards revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new AethermagesTouchEffect());
    }

    public AethermagesTouch(final AethermagesTouch card) {
        super(card);
    }

    @Override
    public AethermagesTouch copy() {
        return new AethermagesTouch(this);
    }
}
class AethermagesTouchEffect extends OneShotEffect {

    private static final FilterCreatureCard filterPutOntoBattlefield = new FilterCreatureCard("a creature card to put onto the battlefield");

    public AethermagesTouchEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. You may put a creature card from among them onto the battlefield. It gains \"At the beginning of your end step, return this creature to its owner's hand.\" Then put the rest of the cards revealed this way on the bottom of your library in any order";
    }

    public AethermagesTouchEffect(final AethermagesTouchEffect effect) {
        super(effect);
    }

    @Override
    public AethermagesTouchEffect copy() {
        return new AethermagesTouchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl();

            boolean properCardFound = false;
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (filterPutOntoBattlefield.match(card, source.getSourceId(), source.getControllerId(), game)) {
                        properCardFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                player.revealCards("Aethermage's Touch", cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filterPutOntoBattlefield);
                if (properCardFound && player.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        if (card.putOntoBattlefield(game, Zone.LIBRARY, source.getSourceId(), source.getControllerId())) {
                            // It gains \"At the beginning of your end step, return this creature to its owner's hand.\"
                            Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), TargetController.YOU, null, false);
                            ContinuousEffect effect = new GainAbilityTargetEffect(ability, Duration.Custom);
                            effect.setTargetPointer(new FixedTarget(card.getId()));
                            game.addEffect(effect, source);
                        }
                    }

                }
                player.putCardsOnBottomOfLibrary(cards, game, source, true);

            }
            return true;
        }
        return false;
    }
}
