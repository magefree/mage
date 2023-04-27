
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class Mortipede extends CardImpl {

    public Mortipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // {2}{G}: All creatures able to block Mortipede this turn do so.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByAllSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")));
    }

    private Mortipede(final Mortipede card) {
        super(card);
    }

    @Override
    public Mortipede copy() {
        return new Mortipede(this);
    }
}
