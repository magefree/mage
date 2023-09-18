
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author ilcartographer
 */
public final class SedgeTroll extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("Swamp");

    static {
        filter.add(SubType.SWAMP.getPredicate());
    }

    public SedgeTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Sedge Troll gets +1/+1 as long as you control a Swamp.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceWhileControlsEffect(filter, 1, 1)));
        // {B}: Regenerate Sedge Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private SedgeTroll(final SedgeTroll card) {
        super(card);
    }

    @Override
    public SedgeTroll copy() {
        return new SedgeTroll(this);
    }
}
