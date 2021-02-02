
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByOneEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class PathrazerOfUlamog extends CardImpl {

    public PathrazerOfUlamog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{11}");
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        this.addAbility(new AnnihilatorAbility(3));
        // Pathrazer of Ulamog can't be blocked except by three or more creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByOneEffect(3)));
    }

    private PathrazerOfUlamog(final PathrazerOfUlamog card) {
        super(card);
    }

    @Override
    public PathrazerOfUlamog copy() {
        return new PathrazerOfUlamog(this);
    }
}
