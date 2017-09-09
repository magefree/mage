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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public class CouncilGuardian extends CardImpl {

    public CouncilGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Will of the council - When Council Guardian enters the battlefield, starting with you, each player votes for blue, black, red, or green. Council Guardian gains protection from each color with the most votes or tied for most votes.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CouncilsGuardianEffect(), false, "<i>Will of the council</i> — "));

    }

    public CouncilGuardian(final CouncilGuardian card) {
        super(card);
    }

    @Override
    public CouncilGuardian copy() {
        return new CouncilGuardian(this);
    }
}

class CouncilsGuardianEffect extends OneShotEffect {

    public CouncilsGuardianEffect() {
        super(Outcome.Exile);
        this.staticText = "starting with you, each player votes for blue, black, red, or green. {this} gains protection from each color with the most votes or tied for most votes";
    }

    public CouncilsGuardianEffect(final CouncilsGuardianEffect effect) {
        super(effect);
    }

    @Override
    public CouncilsGuardianEffect copy() {
        return new CouncilsGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        choice.getChoices().remove("White");
        if (controller != null) {
            Map<ObjectColor, Integer> chosenColors = new HashMap<>(2);
            int maxCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    choice.clearChoice();
                    if (player.choose(outcome, choice, game)) {
                        ObjectColor color = choice.getColor();
                        if (color != null) {
                            if (chosenColors.containsKey(color)) {
                                int count = chosenColors.get(color) + 1;
                                if (count > maxCount) {
                                    maxCount = count;
                                }
                                chosenColors.put(color, count);
                            } else {
                                if (maxCount == 0) {
                                    maxCount = 1;
                                }
                                chosenColors.put(color, 1);
                            }
                            game.informPlayers(player.getLogName() + " has chosen " + color.getDescription() + '.');
                        }
                    }
                }
            }

            for (Map.Entry<ObjectColor, Integer> entry : chosenColors.entrySet()) {
                if (entry.getValue() == maxCount) {
                    ObjectColor color = entry.getKey();
                    game.addEffect(new GainAbilitySourceEffect(ProtectionAbility.from(color), Duration.Custom), source);
                }
            }
            return true;
        }
        return false;
    }
}
