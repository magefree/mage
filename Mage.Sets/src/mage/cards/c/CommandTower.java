
package mage.cards.c;

import java.util.UUID;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class CommandTower extends CardImpl {

    public CommandTower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add one mana of any color in your commander's color identity.
        this.addAbility(new CommanderColorIdentityManaAbility());
    }

    private CommandTower(final CommandTower card) {
        super(card);
    }

    @Override
    public CommandTower copy() {
        return new CommandTower(this);
    }
}
