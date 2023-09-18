
package mage.cards.u;

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
 * @author dustinconrad
 */
public final class UthdenTroll extends CardImpl {

    public UthdenTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.TROLL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {R}: Regenerate Uthden Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{R}")));
    }

    private UthdenTroll(final UthdenTroll card) {
        super(card);
    }

    @Override
    public UthdenTroll copy() {
        return new UthdenTroll(this);
    }
}
