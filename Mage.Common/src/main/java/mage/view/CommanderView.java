
package mage.view;

import java.io.Serializable;
import java.util.UUID;

import mage.cards.Card;
import mage.constants.MageObjectType;
import mage.game.Game;
import mage.game.command.Commander;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public class CommanderView extends CardView implements CommandObjectView, Serializable{

    public CommanderView(Commander commander, Card sourceCard, Game game, UUID createdForPlayerId) {
        super(sourceCard, game, CardUtil.canShowAsControlled(sourceCard, createdForPlayerId));
        this.mageObjectType = MageObjectType.COMMANDER;
    }   
}
