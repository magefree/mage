
package mage.view;

import java.io.Serializable;
import mage.cards.Card;
import mage.constants.MageObjectType;
import mage.game.Game;
import mage.game.command.Commander;

/**
 *
 * @author Plopman
 */
public class CommanderView extends CardView implements CommandObjectView, Serializable{

    public CommanderView(Commander commander, Card sourceCard, Game game) {
        super(sourceCard, game, false);
        this.mageObjectType = MageObjectType.COMMANDER;
    }   
}
