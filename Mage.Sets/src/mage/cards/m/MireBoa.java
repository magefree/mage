
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class MireBoa extends CardImpl {

    public MireBoa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
        
        // {G}: Regenerate Mire Boa.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{G}")));
    }

    private MireBoa(final MireBoa card) {
        super(card);
    }

    @Override
    public MireBoa copy() {
        return new MireBoa(this);
    }
}
