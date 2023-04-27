
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class VisceraDragger extends CardImpl {

    public VisceraDragger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private VisceraDragger(final VisceraDragger card) {
        super(card);
    }

    @Override
    public VisceraDragger copy() {
        return new VisceraDragger(this);
    }
}
