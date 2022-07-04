
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class ThrunTheLastTroll extends CardImpl {

    public ThrunTheLastTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CantBeCounteredSourceEffect()));
        this.addAbility(HexproofAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}")));
    }

    private ThrunTheLastTroll(final ThrunTheLastTroll card) {
        super(card);
    }

    @Override
    public ThrunTheLastTroll copy() {
        return new ThrunTheLastTroll(this);
    }
}
