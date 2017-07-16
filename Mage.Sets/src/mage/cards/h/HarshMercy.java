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
package mage.cards.h;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCreatureType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author pcasaretto
 */
public class HarshMercy extends CardImpl {

    public HarshMercy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");

        // Each player chooses a creature type. Destroy all creatures that aren't of a type chosen this way. They can't be regenerated.
        this.getSpellAbility().addEffect(new HarshMercyEffect());
    }

    public HarshMercy(final HarshMercy card) {
        super(card);
    }

    @Override
    public HarshMercy copy() {
        return new HarshMercy(this);
    }
}

class HarshMercyEffect extends OneShotEffect {

    public HarshMercyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Each player chooses a creature type. Destroy all creatures that aren't of a type chosen this way. They can't be regenerated.";
    }

    public HarshMercyEffect(final HarshMercyEffect effect) {
        super(effect);
    }

    @Override
    public HarshMercyEffect copy() {
        return new HarshMercyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Set<String> chosenTypes = new HashSet<>();
            PlayerIteration:
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                Choice typeChoice = new ChoiceCreatureType();
                while (!player.choose(Outcome.DestroyPermanent, typeChoice, game)) {
                    if (!player.canRespond()) {
                        continue PlayerIteration;
                    }
                }
                String chosenType = typeChoice.getChoice();
                if (chosenType != null) {
                    game.informPlayers(sourceObject.getIdName() + ": " + player.getLogName() + " has chosen " + chosenType);
                    chosenTypes.add(chosenType);
                }
            }

            FilterPermanent filter = new FilterCreaturePermanent("creatures");
            for (String type : chosenTypes) {
                filter.add(Predicates.not(new SubtypePredicate(SubType.byDescription(type))));
            }

            return new DestroyAllEffect(filter, true).apply(game, source);
        }
        return false;
    }

}
