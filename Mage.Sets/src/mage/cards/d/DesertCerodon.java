
package mage.cards.d;

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
 * @author fireshoes
 */
public final class DesertCerodon extends CardImpl {

    public DesertCerodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        // Cycling {R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{R}")));
    }

    private DesertCerodon(final DesertCerodon card) {
        super(card);
    }

    @Override
    public DesertCerodon copy() {
        return new DesertCerodon(this);
    }
}
