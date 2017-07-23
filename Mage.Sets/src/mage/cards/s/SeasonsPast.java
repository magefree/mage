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
package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToLibrarySpellEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public class SeasonsPast extends CardImpl {

    public SeasonsPast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Return any number of cards with different converted mana costs from your graveyard to your hand. Put Seasons Past on the bottom of its owner's library.
        this.getSpellAbility().addEffect(new SeasonsPastEffect());
        this.getSpellAbility().addEffect(new ReturnToLibrarySpellEffect(false));
    }

    public SeasonsPast(final SeasonsPast card) {
        super(card);
    }

    @Override
    public SeasonsPast copy() {
        return new SeasonsPast(this);
    }
}

class SeasonsPastEffect extends OneShotEffect {

    public SeasonsPastEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return any number of cards with different converted mana costs from your graveyard to your hand";
    }

    public SeasonsPastEffect(final SeasonsPastEffect effect) {
        super(effect);
    }

    @Override
    public SeasonsPastEffect copy() {
        return new SeasonsPastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SeasonsPastTarget target = new SeasonsPastTarget();
            controller.choose(outcome, target, source.getSourceId(), game);
            controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}

class SeasonsPastTarget extends TargetCardInYourGraveyard {

    public SeasonsPastTarget() {
        super(0, Integer.MAX_VALUE, new FilterCard("cards with different converted mana costs from your graveyard"));
    }

    public SeasonsPastTarget(SeasonsPastTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<Integer> usedCMC = new HashSet<>();
        for (UUID targetId : this.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                usedCMC.add(card.getConvertedManaCost());
            }
        }
        Set<UUID> possibleTargets = super.possibleTargets(sourceId, sourceControllerId, game);
        Set<UUID> leftPossibleTargets = new HashSet<>();
        for (UUID targetId : possibleTargets) {
            Card card = game.getCard(targetId);
            if (card != null && !usedCMC.contains(card.getConvertedManaCost())) {
                leftPossibleTargets.add(targetId);
            }
        }
        return leftPossibleTargets;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        if (super.canTarget(playerId, objectId, source, game)) {
            Set<Integer> usedCMC = new HashSet<>();
            for (UUID targetId : this.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    usedCMC.add(card.getConvertedManaCost());
                }
            }
            Card card = game.getCard(objectId);
            return card != null && !usedCMC.contains(card.getConvertedManaCost());
        }
        return false;
    }

    @Override
    public SeasonsPastTarget copy() {
        return new SeasonsPastTarget(this);
    }

}
