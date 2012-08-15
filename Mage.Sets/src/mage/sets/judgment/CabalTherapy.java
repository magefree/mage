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
package mage.sets.judgment;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.players.Player;
import mage.sets.Sets;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public class CabalTherapy extends CardImpl<CabalTherapy> {

    public CabalTherapy(UUID ownerId) {
        super(ownerId, 62, "Cabal Therapy", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "JUD";

        this.color.setBlack(true);

        // Name a nonland card. Target player reveals his or her hand and discards all cards with that name.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new CabalTherapyEffect());
        // Flashback-Sacrifice a creature.
        this.addAbility(new FlashbackAbility(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1)), TimingRule.SORCERY));
    }

    public CabalTherapy(final CabalTherapy card) {
        super(card);
    }

    @Override
    public CabalTherapy copy() {
        return new CabalTherapy(this);
    }
}

class CabalTherapyEffect extends OneShotEffect<CabalTherapyEffect> {

    public CabalTherapyEffect() {
        super(Outcome.Exile);
        staticText = "Name a nonland card. Search target player's hand for all cards with that name and discard them.";
    }

    public CabalTherapyEffect(final CabalTherapyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Choice cardChoice = new ChoiceImpl();
            cardChoice.setChoices(Sets.getNonLandCardNames());
            cardChoice.clearChoice();

            while (!controller.choose(Outcome.Discard, cardChoice, game)) {
                game.debugMessage("player canceled choosing name. retrying.");
            }

            String cardName = cardChoice.getChoice();
            game.informPlayers("Cabal Therapy, named card: [" + cardName + "]");
            for (Card card : player.getHand().getCards(game)) {
                if (card.getName().equals(cardName)) {
                    card.moveToZone(Zone.GRAVEYARD, source.getId(), game, false);
                }
            }

            controller.lookAtCards("Cabal Therapy Hand", player.getHand(), game);
        }
        return true;
    }

    @Override
    public CabalTherapyEffect copy() {
        return new CabalTherapyEffect(this);
    }
}
