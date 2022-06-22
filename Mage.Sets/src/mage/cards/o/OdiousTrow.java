
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class OdiousTrow extends CardImpl {

    public OdiousTrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/G}");
        this.subtype.add(SubType.TROLL);


        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B/G}")));
    }

    private OdiousTrow(final OdiousTrow card) {
        super(card);
    }

    @Override
    public OdiousTrow copy() {
        return new OdiousTrow(this);
    }
}
