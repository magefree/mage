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
package mage.cards.p;

import java.util.*;
import java.util.stream.Collectors;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author duncant
 */
public class PatriarchsBidding extends CardImpl {

    public PatriarchsBidding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Each player chooses a creature type. Each player returns all creature cards of a type chosen this way from his or her graveyard to the battlefield.
        this.getSpellAbility().addEffect(new PatriarchsBiddingEffect());
    }

    public PatriarchsBidding(final PatriarchsBidding card) {
        super(card);
    }

    @Override
    public PatriarchsBidding copy() {
        return new PatriarchsBidding(this);
    }
}

class PatriarchsBiddingEffect extends OneShotEffect {

    public PatriarchsBiddingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "each player chooses a creature type. Each player returns all creature cards of a type chosen this way from his or her graveyard to the battlefield";
    }

    public PatriarchsBiddingEffect(final PatriarchsBiddingEffect effect) {
        super(effect);
    }

    @Override
    public PatriarchsBiddingEffect copy() {
        return new PatriarchsBiddingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null) {
            Set<String> chosenTypes = new HashSet<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                Choice typeChoice = new ChoiceImpl(true);
                typeChoice.setMessage("Choose a creature type");
                typeChoice.setChoices(SubType.getCreatureTypes(false).stream().map(SubType::toString).collect(Collectors.toSet()));
                while (!player.choose(Outcome.PutCreatureInPlay, typeChoice, game)) {
                    if (!player.canRespond()) {
                        break;
                    }
                }
                String chosenType = typeChoice.getChoice();
                if (chosenType != null) {
                    game.informPlayers(sourceObject.getLogName() + ": " + player.getLogName() + " has chosen " + chosenType);
                    chosenTypes.add(chosenType);
                }
            }

            List<SubtypePredicate> predicates = new ArrayList<>();
            for (String type : chosenTypes) {
                predicates.add(new SubtypePredicate(SubType.byDescription(type)));
            }
            FilterCard filter = new FilterCreatureCard();
            filter.add(Predicates.or(predicates));
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
