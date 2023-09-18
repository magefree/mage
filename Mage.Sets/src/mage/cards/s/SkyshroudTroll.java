
package mage.cards.s;

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
 * @author Loki
 */
public final class SkyshroudTroll extends CardImpl {

    public SkyshroudTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}")));
    }

    private SkyshroudTroll(final SkyshroudTroll card) {
        super(card);
    }

    @Override
    public SkyshroudTroll copy() {
        return new SkyshroudTroll(this);
    }
}
