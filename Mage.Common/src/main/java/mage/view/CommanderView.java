
package mage.view;

import java.io.Serializable;
import mage.cards.Card;
import mage.constants.MageObjectType;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public class CommanderView extends CardView implements CommandObjectView, Serializable{
    public CommanderView(Card sourceCard, Game game) {
        super(sourceCard, game, false);
        this.mageObjectType = MageObjectType.COMMANDER;
    }   
}
