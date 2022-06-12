

package mage.cards.v;

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
public final class VotaryOfTheConclave extends CardImpl {

    public VotaryOfTheConclave (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{2}{G}")));
    }

    public VotaryOfTheConclave (final VotaryOfTheConclave card) {
        super(card);
    }

    @Override
    public VotaryOfTheConclave copy() {
        return new VotaryOfTheConclave(this);
    }

}
