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
package mage.sets.onslaught;

import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.cards.CardImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author EvilGeek
 */
public class Standardize extends CardImpl {

    public Standardize(UUID ownerId) {
        super(ownerId, 116, "Standardize", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{U}{U}");
        this.expansionSetCode = "ONS";

        // Choose a creature type other than Wall. Each creature becomes that type until end of turn.

        this.getSpellAbility().addEffect(new StandardizeEffect());
    }

    public Standardize(final Standardize card) {
        super(card);
    }

    @Override
    public Standardize copy() {
        return new Standardize(this);
    }
}



class StandardizeEffect extends OneShotEffect {

    public StandardizeEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose a creature type other than wall, each creature's type becomes that type until end of turn";

    }

    public StandardizeEffect(final StandardizeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        String chosenType = "";
        if (player != null && permanent != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose creature type other than Wall");
            Set<String> types = CardRepository.instance.getCreatureTypes();
            types.remove("Wall");
            typeChoice.setChoices(types);
            while (!player.choose(Outcome.BoostCreature, typeChoice, game)) {
                if (!player.canRespond()) {
                    return false;
                }
            }
            game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + typeChoice.getChoice());
            chosenType = typeChoice.getChoice();
            if (chosenType != null && !chosenType.isEmpty()) {
                // ADD TYPE TO TARGET
                ContinuousEffect effect = new BecomesSubtypeAllEffect(Duration.EndOfTurn, chosenType);
                game.addEffect(effect, source);
                return true;
            }

        }
        return false;
    }

    @Override
    public Effect copy() {
        return new StandardizeEffect(this);
    }
}
