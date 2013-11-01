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
package mage.sets.commander2013;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class CommandTower extends CardImpl<CommandTower> {

    public CommandTower(UUID ownerId) {
        super(ownerId, 281, "Command Tower", Rarity.COMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "C13";

        // {tap}: Add to your mana pool one mana of any color in your commander's color identity.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new CommandTowerManaEffect(), new TapSourceCost()));
    }

    public CommandTower(final CommandTower card) {
        super(card);
    }

    @Override
    public CommandTower copy() {
        return new CommandTower(this);
    }
}

class CommandTowerManaEffect extends ManaEffect<CommandTowerManaEffect> {

    public CommandTowerManaEffect() {
        super();
        this.staticText = "Add to your mana pool one mana of any color in your commander's color identity";
    }

    public CommandTowerManaEffect(final CommandTowerManaEffect effect) {
        super(effect);
    }

    @Override
    public CommandTowerManaEffect copy() {
        return new CommandTowerManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card commander = game.getCard(controller.getCommanderId());
            if (commander != null) {
                Mana commanderMana = commander.getManaCost().getMana();
                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Pick a mana color");
                if (commanderMana.getBlack() > 0) {
                    choice.getChoices().add("Black");
                }
                if (commanderMana.getRed() > 0) {
                    choice.getChoices().add("Red");
                }
                if (commanderMana.getBlue() > 0) {
                    choice.getChoices().add("Blue");
                }
                if (commanderMana.getGreen() > 0) {
                    choice.getChoices().add("Green");
                }
                if (commanderMana.getWhite() > 0) {
                    choice.getChoices().add("White");
                }
                if (choice.getChoices().size() > 0) {
                    if (choice.getChoices().size() == 1) {
                        choice.setChoice(choice.getChoices().iterator().next());
                    } else {
                        controller.choose(outcome, choice, game);
                    }
                    if (choice.getChoice().equals("Black")) {
                        controller.getManaPool().addMana(Mana.BlackMana, game, source);
                    } else if (choice.getChoice().equals("Blue")) {
                        controller.getManaPool().addMana(Mana.BlueMana, game, source);
                    } else if (choice.getChoice().equals("Red")) {
                        controller.getManaPool().addMana(Mana.RedMana, game, source);
                    } else if (choice.getChoice().equals("Green")) {
                        controller.getManaPool().addMana(Mana.GreenMana, game, source);
                    } else if (choice.getChoice().equals("White")) {
                        controller.getManaPool().addMana(Mana.WhiteMana, game, source);
                    }
                }
                return true;
            }
        }
        return false;
    }
}
