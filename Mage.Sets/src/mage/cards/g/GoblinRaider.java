
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class GoblinRaider extends CardImpl {

    public GoblinRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Goblin Raider can't block.
        this.addAbility(new CantBlockAbility());
    }

    private GoblinRaider(final GoblinRaider card) {
        super(card);
    }

    @Override
    public GoblinRaider copy() {
        return new GoblinRaider(this);
    }
}
