
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author djbrez
 */
public final class BlockadeRunner extends CardImpl {

    public BlockadeRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {U}: Blockade Runner is unblockable this turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CantBeBlockedSourceEffect(Duration.EndOfTurn),
                new ManaCostsImpl<>("{U}")));
    }

    private BlockadeRunner(final BlockadeRunner card) {
        super(card);
    }

    @Override
    public BlockadeRunner copy() {
        return new BlockadeRunner(this);
    }
}
