
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.InfectAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author North
 */
public final class ToxicNim extends CardImpl {

    public ToxicNim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private ToxicNim(final ToxicNim card) {
        super(card);
    }

    @Override
    public ToxicNim copy() {
        return new ToxicNim(this);
    }
}
