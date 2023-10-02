package mage.abilities.mana;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.cards.Card;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.ColoredManaSymbol;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterMana;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public class CommanderColorIdentityManaAbility extends ActivatedManaAbilityImpl {

    public CommanderColorIdentityManaAbility() {
        super(Zone.BATTLEFIELD, new CommanderIdentityManaEffect(), new TapSourceCost());
    }

    public CommanderColorIdentityManaAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new CommanderIdentityManaEffect(), cost);
    }

    protected CommanderColorIdentityManaAbility(final CommanderColorIdentityManaAbility ability) {
        super(ability);
    }

    @Override
    public CommanderColorIdentityManaAbility copy() {
        return new CommanderColorIdentityManaAbility(this);
    }

    @Override
    public boolean definesMana(Game game) {
        return true;
    }

}

class CommanderIdentityManaEffect extends ManaEffect {

    public CommanderIdentityManaEffect() {
        super();
        this.staticText = "Add one mana of any color in your commander's color identity";
    }

    protected CommanderIdentityManaEffect(final CommanderIdentityManaEffect effect) {
        super(effect);
    }

    @Override
    public CommanderIdentityManaEffect copy() {
        return new CommanderIdentityManaEffect(this);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID commanderId : game.getCommandersIds(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)) {
                Card commander = game.getCard(commanderId);
                if (commander != null) {
                    FilterMana commanderMana = commander.getColorIdentity();
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
        }
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl();
            choice.setMessage("Pick a mana color");
            for (UUID commanderId : game.getCommandersIds(controller, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)) {
                Card commander = game.getCard(commanderId);
                if (commander != null) {
                    FilterMana commanderMana = commander.getColorIdentity();
                    if (commanderMana.isWhite()) {
                        choice.getChoices().add("White");
                    }
                    if (commanderMana.isBlue()) {
                        choice.getChoices().add("Blue");
                    }
                    if (commanderMana.isBlack()) {
                        choice.getChoices().add("Black");
                    }
                    if (commanderMana.isRed()) {
                        choice.getChoices().add("Red");
                    }
                    if (commanderMana.isGreen()) {
                        choice.getChoices().add("Green");
                    }
                }
            }
            if (!choice.getChoices().isEmpty()) {
                if (choice.getChoices().size() == 1) {
                    choice.setChoice(choice.getChoices().iterator().next());
                } else {
                    if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
                        return mana;
                    }
                }

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

            }
        }
        return mana;
    }
}
