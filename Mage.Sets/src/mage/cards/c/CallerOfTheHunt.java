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

import java.util.UUID;
import java.util.stream.Collectors;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerSourceEffect;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public class CallerOfTheHunt extends CardImpl {

    public CallerOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add("Human");

        // As an additional cost to cast Caller of the Hunt, choose a creature type.
        // Caller of the Hunt's power and toughness are each equal to the number of creatures of the chosen type on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CallerOfTheHuntAdditionalCostEffect()));

    }

    public CallerOfTheHunt(final CallerOfTheHunt card) {
        super(card);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        MageObject mageObject = game.getObject(ability.getSourceId());
        Effect effect = new ChooseCreatureTypeEffect(Outcome.Benefit);
        if (mageObject != null
                && effect.apply(game, ability)) {
            FilterPermanent filter = new FilterPermanent();
            filter.add(new ChosenSubtypePredicate(mageObject.getId()));
            ContinuousEffect effectPower = new SetPowerSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.Custom);
            ContinuousEffect effectToughness = new SetToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.Custom);
            game.addEffect(effectPower, ability);
            game.addEffect(effectToughness, ability);
        }
    }

    @Override
    public CallerOfTheHunt copy() {
        return new CallerOfTheHunt(this);
    }
}

class CallerOfTheHuntAdditionalCostEffect extends OneShotEffect {

    public CallerOfTheHuntAdditionalCostEffect() {
        super(Outcome.Benefit);
        this.staticText = "As an additional cost to cast {this}, choose a creature type. \r"
                + "{this}'s power and toughness are each equal to the number of creatures of the chosen type on the battlefield";
    }

    public CallerOfTheHuntAdditionalCostEffect(final CallerOfTheHuntAdditionalCostEffect effect) {
        super(effect);
    }

    @Override
    public CallerOfTheHuntAdditionalCostEffect copy() {
        return new CallerOfTheHuntAdditionalCostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}

class ChooseCreatureTypeEffect extends OneShotEffect { // code by LevelX2, but that other version is not compatible with this card

    public ChooseCreatureTypeEffect(Outcome outcome) {
        super(outcome);
        staticText = "choose a creature type";
    }

    public ChooseCreatureTypeEffect(final ChooseCreatureTypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        if (controller != null && mageObject != null) {
            Choice typeChoice = new ChoiceImpl(true);
            typeChoice.setMessage("Choose creature type");
            typeChoice.setChoices(SubType.getCreatureTypes(false).stream().map(SubType::toString).collect(Collectors.toSet()));
            while (!controller.choose(outcome, typeChoice, game)) {
                if (!controller.canRespond()) {
                    return false;
                }
            }
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has chosen " + typeChoice.getChoice());
            }
            game.getState().setValue(mageObject.getId() + "_type", typeChoice.getChoice());
            return true;
        }
        return false;
    }

    @Override
    public ChooseCreatureTypeEffect copy() {
        return new ChooseCreatureTypeEffect(this);
    }

}
