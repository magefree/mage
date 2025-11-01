
package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class BondedHorncrest extends CardImpl {

    public BondedHorncrest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Bonded Horncrest can't attack or block alone.
        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(new CantBlockAloneAbility());
    }

    private BondedHorncrest(final BondedHorncrest card) {
        super(card);
    }

    @Override
    public BondedHorncrest copy() {
        return new BondedHorncrest(this);
    }
}
