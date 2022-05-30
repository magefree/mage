
package mage.cards.b;

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
public final class BarkhideMauler extends CardImpl {

    public BarkhideMauler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private BarkhideMauler(final BarkhideMauler card) {
        super(card);
    }

    @Override
    public BarkhideMauler copy() {
        return new BarkhideMauler(this);
    }
}
