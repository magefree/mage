
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ProvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class GoblinGrappler extends CardImpl {

    public GoblinGrappler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Provoke
        this.addAbility(new ProvokeAbility());
    }

    private GoblinGrappler(final GoblinGrappler card) {
        super(card);
    }

    @Override
    public GoblinGrappler copy() {
        return new GoblinGrappler(this);
    }
}
