package mage.abilities.common;

import mage.abilities.StaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class CommanderChooseColorAbility extends StaticAbility {

    public CommanderChooseColorAbility() {
        super(Zone.ALL, new InfoEffect("if {this} is your commander, choose a color before the game begins. {this} is the chosen color"));
    }

    private CommanderChooseColorAbility(final CommanderChooseColorAbility ability) {
        super(ability);
    }

    @Override
    public CommanderChooseColorAbility copy() {
        return new CommanderChooseColorAbility(this);
    }

    public static boolean checkCard(Card card) {
        return card.getAbilities().stream().anyMatch(CommanderChooseColorAbility.class::isInstance);
    }
}
