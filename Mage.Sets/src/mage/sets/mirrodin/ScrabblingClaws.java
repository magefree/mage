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
package mage.sets.mirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public class ScrabblingClaws extends CardImpl {

    public ScrabblingClaws(UUID ownerId) {
        super(ownerId, 237, "Scrabbling Claws", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "MRD";

        // {tap}: Target player exiles a card from his or her graveyard.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScrabblingClawsEffect(), new TapSourceCost());
        firstAbility.addTarget(new TargetPlayer());
        this.addAbility(firstAbility);
        // {1}, Sacrifice Scrabbling Claws: Exile target card from a graveyard. Draw a card.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new mage.abilities.effects.common.ExileTargetEffect(), new SacrificeSourceCost());
        ability.addCost(new GenericManaCost(1));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
        ability.addEffect(new DrawCardSourceControllerEffect(1));
    }

    public ScrabblingClaws(final ScrabblingClaws card) {
        super(card);
    }

    @Override
    public ScrabblingClaws copy() {
        return new ScrabblingClaws(this);
    }
}

class ScrabblingClawsEffect extends OneShotEffect {

    public ScrabblingClawsEffect() {
        super(Outcome.Exile);
        this.staticText = "Target player exiles a card from his or her graveyard";
    }

    public ScrabblingClawsEffect(final ScrabblingClawsEffect effect) {
        super(effect);
    }

    @Override
    public ScrabblingClawsEffect copy() {
        return new ScrabblingClawsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            FilterCard filter = new FilterCard("card from your graveyard");
            filter.add(new OwnerIdPredicate(targetPlayer.getId()));
            TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
            if (targetPlayer.chooseTarget(Outcome.Exile, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    targetPlayer.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.GRAVEYARD, true);
                }
                return true;
            }
        }
        return false;
    }
}