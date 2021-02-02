
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class GoblinGlider extends CardImpl {

    public GoblinGlider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Goblin Glider can't block.
        this.addAbility(new CantBlockAbility());
    }

    private GoblinGlider(final GoblinGlider card) {
        super(card);
    }

    @Override
    public GoblinGlider copy() {
        return new GoblinGlider(this);
    }
}
