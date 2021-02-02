
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.mana.CommanderColorIdentityManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class CommandersSphere extends CardImpl {

    public CommandersSphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // {tap}: Add one mana of any color in your commander's color identity.
        this.addAbility(new CommanderColorIdentityManaAbility());

        // Sacrifice Commander's Sphere: Draw a card.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new SacrificeSourceCost()));
    }

    private CommandersSphere(final CommandersSphere card) {
        super(card);
    }

    @Override
    public CommandersSphere copy() {
        return new CommandersSphere(this);
    }
}
