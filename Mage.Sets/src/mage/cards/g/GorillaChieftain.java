
package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author Quercitron
 */
public final class GorillaChieftain extends CardImpl {

    public GorillaChieftain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}{G}: Regenerate Gorilla Chieftain.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}")));
    }

    private GorillaChieftain(final GorillaChieftain card) {
        super(card);
    }

    @Override
    public GorillaChieftain copy() {
        return new GorillaChieftain(this);
    }
}
