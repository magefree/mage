
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
public final class SandbarSerpent extends CardImpl {

    public SandbarSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SandbarSerpent(final SandbarSerpent card) {
        super(card);
    }

    @Override
    public SandbarSerpent copy() {
        return new SandbarSerpent(this);
    }
}
