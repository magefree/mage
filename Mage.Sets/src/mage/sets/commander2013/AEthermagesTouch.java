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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AEthermagesTouch extends CardImpl<AEthermagesTouch> {

    public AEthermagesTouch(UUID ownerId) {
        super(ownerId, 176, "AEthermage's Touch", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");
        this.expansionSetCode = "C13";

        this.color.setBlue(true);
        this.color.setWhite(true);

        // Reveal the top four cards of your library. You may put a creature card from among them onto the battlefield. It gains "At the beginning of your end step, return this creature to its owner's hand." Then put the rest of the cards revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new AEthermagesTouchEffect());
    }

    public AEthermagesTouch(final AEthermagesTouch card) {
        super(card);
    }

    @Override
    public AEthermagesTouch copy() {
        return new AEthermagesTouch(this);
    }
}
class AEthermagesTouchEffect extends OneShotEffect<AEthermagesTouchEffect> {

    private static final FilterCreatureCard filterPutOntoBattlefield = new FilterCreatureCard("a creature card to put onto the battlefield");

    public AEthermagesTouchEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. You may put a creature card from among them onto the battlefield. It gains \"At the beginning of your end step, return this creature to its owner's hand.\" Then put the rest of the cards revealed this way on the bottom of your library in any order";
    }

    public AEthermagesTouchEffect(final AEthermagesTouchEffect effect) {
        super(effect);
    }

    @Override
    public AEthermagesTouchEffect copy() {
        return new AEthermagesTouchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);

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
                player.revealCards("AEthermage's Touch", cards, game);
                TargetCard target = new TargetCard(Zone.PICK, filterPutOntoBattlefield);
                if (properCardFound && player.choose(Outcome.PutCreatureInPlay, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        if (card.putOntoBattlefield(game, Zone.PICK, source.getSourceId(), source.getControllerId())) {
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
