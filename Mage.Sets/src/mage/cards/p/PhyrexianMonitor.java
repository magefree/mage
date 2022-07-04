
package mage.cards.p;

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
 * @author LoneFox
 */
public final class PhyrexianMonitor extends CardImpl {

    public PhyrexianMonitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {B}: Regenerate Phyrexian Monitor.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private PhyrexianMonitor(final PhyrexianMonitor card) {
        super(card);
    }

    @Override
    public PhyrexianMonitor copy() {
        return new PhyrexianMonitor(this);
    }
}
