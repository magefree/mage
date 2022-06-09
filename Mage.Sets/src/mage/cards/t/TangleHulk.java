

package mage.cards.t;

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
public final class TangleHulk extends CardImpl {

    public TangleHulk (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{G}")));
    }

    public TangleHulk (final TangleHulk card) {
        super(card);
    }

    @Override
    public TangleHulk copy() {
        return new TangleHulk(this);
    }

}
