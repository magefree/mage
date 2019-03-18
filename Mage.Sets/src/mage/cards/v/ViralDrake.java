
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class ViralDrake extends CardImpl {

    public ViralDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ProliferateEffect(), new ManaCostsImpl("{3}{U}")));
    }

    public ViralDrake(final ViralDrake card) {
        super(card);
    }

    @Override
    public ViralDrake copy() {
        return new ViralDrake(this);
    }
}
