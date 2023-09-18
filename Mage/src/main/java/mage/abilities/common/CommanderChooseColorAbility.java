package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class CommanderChooseColorAbility extends StaticAbility {

    public CommanderChooseColorAbility() {
        super(Zone.ALL, null);
    }

    private CommanderChooseColorAbility(final CommanderChooseColorAbility ability) {
        super(ability);
    }

    @Override
    public CommanderChooseColorAbility copy() {
        return new CommanderChooseColorAbility(this);
    }

    @Override
    public String getRule() {
        return "If {this} is your commander, choose a color before the game begins. {this} is the chosen color.";
    }

    public static boolean checkCard(Card card) {
        return card.getAbilities().stream().anyMatch(CommanderChooseColorAbility.class::isInstance);
    }
}
