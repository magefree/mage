
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class KurosTaken extends CardImpl {

    public KurosTaken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new BushidoAbility(1));
        // {1}{B}: Regenerate Kuro's Taken.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{B}")));
    }

    private KurosTaken(final KurosTaken card) {
        super(card);
    }

    @Override
    public KurosTaken copy() {
        return new KurosTaken(this);
    }
}
