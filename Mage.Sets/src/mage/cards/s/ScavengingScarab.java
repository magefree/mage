

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Loki
 */
public final class ScavengingScarab extends CardImpl {

    public ScavengingScarab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.INSECT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new CantBlockAbility());
    }

    private ScavengingScarab(final ScavengingScarab card) {
        super(card);
    }

    @Override
    public ScavengingScarab copy() {
        return new ScavengingScarab(this);
    }

}
