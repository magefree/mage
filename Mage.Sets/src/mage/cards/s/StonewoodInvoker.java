
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class StonewoodInvoker extends CardImpl {

    public StonewoodInvoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {7}{G}: Stonewood Invoker gets +5/+5 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(5,5,Duration.EndOfTurn), new ManaCostsImpl<>("{7}{G}")));
    }

    private StonewoodInvoker(final StonewoodInvoker card) {
        super(card);
    }

    @Override
    public StonewoodInvoker copy() {
        return new StonewoodInvoker(this);
    }
}
