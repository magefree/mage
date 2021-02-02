
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantAttackAloneAbility;
import mage.abilities.keyword.CantBlockAloneAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author magenoxx_at_gmail.com
 */
public final class JackalFamiliar extends CardImpl {

    public JackalFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.JACKAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Jackal Familiar can't attack or block alone.
        this.addAbility(new CantAttackAloneAbility());
        this.addAbility(CantBlockAloneAbility.getInstance());
    }

    private JackalFamiliar(final JackalFamiliar card) {
        super(card);
    }

    @Override
    public JackalFamiliar copy() {
        return new JackalFamiliar(this);
    }
}
