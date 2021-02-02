
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class BondedConstruct extends CardImpl {

    public BondedConstruct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bonded Construct can't attack alone.
        this.addAbility(new CantAttackAloneAbility());
    }

    private BondedConstruct(final BondedConstruct card) {
        super(card);
    }

    @Override
    public BondedConstruct copy() {
        return new BondedConstruct(this);
    }
}
