
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class TectonicFiend extends CardImpl {

    public TectonicFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Echo {4}{R}{R}
        this.addAbility(new EchoAbility("{4}{R}{R}"));
        // Tectonic Fiend attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
    }

    private TectonicFiend(final TectonicFiend card) {
        super(card);
    }

    @Override
    public TectonicFiend copy() {
        return new TectonicFiend(this);
    }
}
