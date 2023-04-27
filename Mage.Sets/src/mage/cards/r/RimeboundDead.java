
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class RimeboundDead extends CardImpl {

    public RimeboundDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {S}: Regenerate Rimebound Dead.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{S}")));
    }

    private RimeboundDead(final RimeboundDead card) {
        super(card);
    }

    @Override
    public RimeboundDead copy() {
        return new RimeboundDead(this);
    }
}
