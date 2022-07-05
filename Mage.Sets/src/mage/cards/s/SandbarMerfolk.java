
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class SandbarMerfolk extends CardImpl {

    public SandbarMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SandbarMerfolk(final SandbarMerfolk card) {
        super(card);
    }

    @Override
    public SandbarMerfolk copy() {
        return new SandbarMerfolk(this);
    }
}
