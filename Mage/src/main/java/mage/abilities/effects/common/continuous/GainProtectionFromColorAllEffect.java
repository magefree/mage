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
package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.keyword.ProtectionAbility;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public class GainProtectionFromColorAllEffect extends GainAbilityAllEffect {

    protected ChoiceColor choice;

    public GainProtectionFromColorAllEffect(Duration duration, FilterPermanent filter) {
        super(new ProtectionAbility(new FilterCard()), duration, filter);
        choice = new ChoiceColor(true);
    }

    public GainProtectionFromColorAllEffect(final GainProtectionFromColorAllEffect effect) {
        super(effect);
        this.choice = effect.choice.copy();
    }

    @Override
    public GainProtectionFromColorAllEffect copy() {
        return new GainProtectionFromColorAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterCard protectionFilter = (FilterCard) ((ProtectionAbility) ability).getFilter();
        protectionFilter.add(new ColorPredicate(choice.getColor()));
        protectionFilter.setMessage(choice.getChoice());
        ((ProtectionAbility) ability).setFilter(protectionFilter);
        return super.apply(game, source);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (sourceObject != null && controller != null) {
            choice.clearChoice();
            while (!choice.isChosen()) {
                controller.choose(Outcome.Protect, choice, game);
                if (!controller.canRespond()) {
                    return;
                }
            }
            if (choice.isChosen() && !game.isSimulation()) {
                game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " has chosen protection from " + choice.getChoice());
            }
        }
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        String text = "Choose a color. " + filter.getMessage() + " gain protection from the chosen color " + duration.toString();

        return text;
    }
}
