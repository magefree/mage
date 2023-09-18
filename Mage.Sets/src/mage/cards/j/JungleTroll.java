
package mage.cards.j;

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
 * @author fireshoes
 */
public final class JungleTroll extends CardImpl {

    public JungleTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {R}: Regenerate Jungle Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{R}")));
        
        // {G}: Regenerate Jungle Troll.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{G}")));
    }

    private JungleTroll(final JungleTroll card) {
        super(card);
    }

    @Override
    public JungleTroll copy() {
        return new JungleTroll(this);
    }
}
