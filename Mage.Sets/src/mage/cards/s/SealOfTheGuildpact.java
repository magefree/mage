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
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author emerald000
 */
public class SealOfTheGuildpact extends CardImpl {

    public SealOfTheGuildpact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // As Seal of the Guildpact enters the battlefield, choose two colors.
        this.addAbility(new EntersBattlefieldAbility(new SealOfTheGuildpactChooseColorEffect()));
        
        // Each spell you cast costs {1} less to cast for each of the chosen colors it is.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SealOfTheGuildpactCostReductionEffect()));
    }

    public SealOfTheGuildpact(final SealOfTheGuildpact card) {
        super(card);
    }

    @Override
    public SealOfTheGuildpact copy() {
        return new SealOfTheGuildpact(this);
    }
}

class SealOfTheGuildpactChooseColorEffect extends OneShotEffect {
    
    SealOfTheGuildpactChooseColorEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose two colors";
    }
    
    SealOfTheGuildpactChooseColorEffect(final SealOfTheGuildpactChooseColorEffect effect) {
        super(effect);
    }
    
    @Override
    public SealOfTheGuildpactChooseColorEffect copy() {
        return new SealOfTheGuildpactChooseColorEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (controller != null && mageObject != null) {
            ChoiceColor choice1 = new ChoiceColor();
            while (!choice1.isChosen()) {
                controller.choose(Outcome.Benefit, choice1, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            String color1 = choice1.getChoice();
            Set<String> choices2 = new HashSet<>();
            if (!color1.equals("White")) {
                choices2.add("White");
            }
            if (!color1.equals("Blue")) {
                choices2.add("Blue");
            }
            if (!color1.equals("Black")) {
                choices2.add("Black");
            }
            if (!color1.equals("Red")) {
                choices2.add("Red");
            }
            if (!color1.equals("Green")) {
                choices2.add("Green");
            }
            ChoiceColor choice2 = new ChoiceColor();
            choice2.setChoices(choices2);
            while (!choice2.isChosen()) {
                controller.choose(Outcome.Benefit, choice2, game);
                if (!controller.canRespond()) {
                    return false;
                }
            }
            String color2 = choice2.getChoice();
            if (!game.isSimulation()) {
                game.informPlayers(mageObject.getLogName() + ": " + controller.getLogName() + " has chosen " + color1 + " and " + color2 + '.');
            }
            game.getState().setValue(mageObject.getId() + "_color1", choice1.getColor());
            game.getState().setValue(mageObject.getId() + "_color2", choice2.getColor());
            ((Card) mageObject).addInfo("chosen colors", CardUtil.addToolTipMarkTags("Chosen colors: " + color1 + " and " + color2), game);
            return true;
        }
        return false;
    }
}

class SealOfTheGuildpactCostReductionEffect extends CostModificationEffectImpl {

    SealOfTheGuildpactCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Each spell you cast costs {1} less to cast for each of the chosen colors it is";
    }

    SealOfTheGuildpactCostReductionEffect(SealOfTheGuildpactCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        MageObject sourceObject = game.getObject(abilityToModify.getSourceId());
        if (sourceObject != null) {
            ObjectColor color1 = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color1");
            ObjectColor color2 = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color2");
            int amount = 0;
            if (color1 != null && sourceObject.getColor(game).contains(color1)) {
                amount++;
            }
            if (color2 != null && sourceObject.getColor(game).contains(color2)) {
                amount++;
            }
            if (amount > 0) {
                SpellAbility spellAbility = (SpellAbility) abilityToModify;
                CardUtil.adjustCost(spellAbility, amount);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getControllerId().equals(source.getControllerId()) &&
                abilityToModify instanceof SpellAbility;
    }

    @Override
    public SealOfTheGuildpactCostReductionEffect copy() {
        return new SealOfTheGuildpactCostReductionEffect(this);
    }
}
