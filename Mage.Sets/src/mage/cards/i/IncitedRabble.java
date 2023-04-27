
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
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
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class IncitedRabble extends CardImpl {

    public IncitedRabble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.color.setRed(true);
        
        // this card is the second face of double-faced card
        this.nightCard = true;

        // Incited Rabble attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        
        // {2}: Incited Rabble gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{2}")));
    }

    private IncitedRabble(final IncitedRabble card) {
        super(card);
    }

    @Override
    public IncitedRabble copy() {
        return new IncitedRabble(this);
    }
}
