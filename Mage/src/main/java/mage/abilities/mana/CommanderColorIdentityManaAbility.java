package mage.abilities.mana;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

/**
 *
 * @author LevelX2
 */
public class CommanderColorIdentityManaAbility extends ActivatedManaAbilityImpl {

    public CommanderColorIdentityManaAbility() {
        super(Zone.BATTLEFIELD, new CommanderIdentityManaEffect(), new TapSourceCost());
    }

    public CommanderColorIdentityManaAbility(Cost cost) {
        super(Zone.BATTLEFIELD, new CommanderIdentityManaEffect(), cost);
    }

    public CommanderColorIdentityManaAbility(final CommanderColorIdentityManaAbility ability) {
        super(ability);
    }

    @Override
    public CommanderColorIdentityManaAbility copy() {
        return new CommanderColorIdentityManaAbility(this);
    }

    @Override
    public List<Mana> getNetMana(Game game) {
        List<Mana> netManas = new ArrayList<>();
        if (netMana.isEmpty() && game != null) {
            Player controller = game.getPlayer(getControllerId());
            if (controller != null) {
                for (UUID commanderId : controller.getCommandersIds()) {
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
        }
        netManas.addAll(netMana);
        return netManas;
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

    public CommanderIdentityManaEffect(final CommanderIdentityManaEffect effect) {
        super(effect);
    }

    @Override
    public CommanderIdentityManaEffect copy() {
        return new CommanderIdentityManaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            checkToFirePossibleEvents(getMana(game, source), game, source);
            controller.getManaPool().addMana(getMana(game, source), game, source);
            return true;
        }
        return false;
    }

    @Override
    public Mana produceMana(boolean netMana, Game game, Ability source) {
        Mana mana = new Mana();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl();
            choice.setMessage("Pick a mana color");
            for (UUID commanderId : controller.getCommandersIds()) {
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
                    if (!controller.choose(outcome, choice, game)) {
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
