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
package mage.sets.antiquities;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.SearchEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public class TransmuteArtifact extends CardImpl {

    public TransmuteArtifact(UUID ownerId) {
        super(ownerId, 58, "Transmute Artifact", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{U}{U}");
        this.expansionSetCode = "ATQ";

        // Sacrifice an artifact. If you do, search your library for an artifact card. If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new TransmuteArtifactEffect());
    }

    public TransmuteArtifact(final TransmuteArtifact card) {
        super(card);
    }

    @Override
    public TransmuteArtifact copy() {
        return new TransmuteArtifact(this);
    }
}

class TransmuteArtifactEffect extends SearchEffect {

    public TransmuteArtifactEffect() {
        super(new TargetCardInLibrary(new FilterArtifactCard()), Outcome.PutCardInPlay);
        staticText = "Sacrifice an artifact. If you do, search your library for an artifact card. If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield. If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield. If you don't, put it into its owner's graveyard. Then shuffle your library";
    }

    public TransmuteArtifactEffect(final TransmuteArtifactEffect effect) {
        super(effect);
    }

    @Override
    public TransmuteArtifactEffect copy() {
        return new TransmuteArtifactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        //Sacrifice an artifact.
        int convertedManaCost = 0;
        boolean sacrifice = false;
        TargetControlledPermanent targetArtifact = new TargetControlledPermanent(new FilterControlledArtifactPermanent());
        if (controller.chooseTarget(Outcome.Sacrifice, targetArtifact, source, game)) {
            Permanent permanent = game.getPermanent(targetArtifact.getFirstTarget());
            if (permanent != null) {
                convertedManaCost = permanent.getManaCost().convertedManaCost();
                sacrifice = permanent.sacrifice(source.getSourceId(), game);
            }
        } else {
            return true;
        }
        //If you do, search your library for an artifact card.
        if (sacrifice && controller.searchLibrary(target, game)) {
            if (target.getTargets().size() > 0) {
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        //If that card's converted mana cost is less than or equal to the sacrificed artifact's converted mana cost, put it onto the battlefield.
                        if (card.getManaCost().convertedManaCost() <= convertedManaCost) {
                            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                        } else {
                            //If it's greater, you may pay {X}, where X is the difference. If you do, put it onto the battlefield.
                            GenericManaCost cost = new GenericManaCost(card.getManaCost().convertedManaCost() - convertedManaCost);
                            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)) {
                                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                            } else {
                                //If you don't, put it into its owner's graveyard. Then shuffle your library
                                controller.moveCards(card, Zone.GRAVEYARD, source, game);
                            }
                        }
                    }
                }
            }
            controller.shuffleLibrary(game);
            return true;
        }
        controller.shuffleLibrary(game);
        return false;
    }

}
