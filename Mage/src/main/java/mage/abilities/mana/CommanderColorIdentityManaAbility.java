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
package mage.abilities.mana;

import java.util.List;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.ManaEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class CommanderColorIdentityManaAbility extends ManaAbility {

    private FilterMana commanderMana;

    public CommanderColorIdentityManaAbility() {
        super(Zone.BATTLEFIELD, new CommanderIdentityManaEffect(), new TapSourceCost());
    }

    public CommanderColorIdentityManaAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new CommanderIdentityManaEffect(), cost);
        commanderMana = null;
    }

    public CommanderColorIdentityManaAbility(final CommanderColorIdentityManaAbility ability) {
        super(ability);
        this.commanderMana = ability.commanderMana;
    }

    @Override
    public CommanderColorIdentityManaAbility copy() {
        return new CommanderColorIdentityManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        if (netMana.isEmpty() && game != null) {
            Player controller = game.getPlayer(getControllerId());
            if (controller != null) {
                if (commanderMana == null) {
                    Card commander = game.getCard(controller.getCommanderId());
                    if (commander != null) {
                        commanderMana = CardUtil.getColorIdentity(commander);
                    } else {
                        // In formats other than Commander, Command Tower's ability produces no mana.
                        commanderMana = new FilterMana();
                    }
                }
                if (commanderMana.isBlack()) {
                    netMana.add(new Mana(ColoredManaSymbol.B));
                }
                if (commanderMana.isBlue()) {
                    netMana.add(new Mana(ColoredManaSymbol.U));
                }
                if (commanderMana.isGreen()) {
                    netMana.add(new Mana(ColoredManaSymbol.G));
                }
                if (commanderMana.isRed()) {
                    netMana.add(new Mana(ColoredManaSymbol.R));
                }
                if (commanderMana.isWhite()) {
                    netMana.add(new Mana(ColoredManaSymbol.W));
                }
            }
        }
        return netMana;
    }

    @Override
    public boolean definesMana() {
        return true;
    }

}

class CommanderIdentityManaEffect extends ManaEffect {

    private FilterMana commanderMana;

    public CommanderIdentityManaEffect() {
        super();
        this.staticText = "Add to your mana pool one mana of any color in your commander's color identity";
        commanderMana = null;
    }

    public CommanderIdentityManaEffect(final CommanderIdentityManaEffect effect) {
        super(effect);
        this.commanderMana = effect.commanderMana;
    }

    @Override
    public CommanderIdentityManaEffect copy() {
        return new CommanderIdentityManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (commanderMana == null) {
                Card commander = game.getCard(controller.getCommanderId());
                if (commander != null) {
                    commanderMana = CardUtil.getColorIdentity(commander);
                } else {
                    // In formats other than Commander, Command Tower's ability produces no mana.
                    commanderMana = new FilterMana();
                }
            }
            Choice choice = new ChoiceImpl();
            choice.setMessage("Pick a mana color");
            if (commanderMana.isBlack()) {
                choice.getChoices().add("Black");
            }
            if (commanderMana.isRed()) {
                choice.getChoices().add("Red");
            }
            if (commanderMana.isBlue()) {
                choice.getChoices().add("Blue");
            }
            if (commanderMana.isGreen()) {
                choice.getChoices().add("Green");
            }
            if (commanderMana.isWhite()) {
                choice.getChoices().add("White");
            }
            if (choice.getChoices().size() > 0) {
                if (choice.getChoices().size() == 1) {
                    choice.setChoice(choice.getChoices().iterator().next());
                } else {
                    if (!controller.choose(outcome, choice, game)) {
                        return false;
                    }
                }
                Mana mana = new Mana();
                switch (choice.getChoice()) {
                    case "Black":
                        mana.setBlack(1);
                        break;
                    case "Blue":
                        mana.setBlue(1);
                        break;
                    case "Red":
                        mana.setRed(1);
                        break;
                    case "Green":
                        mana.setGreen(1);
                        break;
                    case "White":
                        mana.setWhite(1);
                        break;
                }
                checkToFirePossibleEvents(mana, game, source);
                controller.getManaPool().addMana(mana, game, source);
                return true;
            }
        }
        return false;
    }

    @Override
    public Mana getMana(Game game, Ability source) {
        return null;
    }
}
