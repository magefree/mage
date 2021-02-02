
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ArchersOfQarsi extends CardImpl {

    public ArchersOfQarsi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Reach
        this.addAbility(ReachAbility.getInstance());
    }

    private ArchersOfQarsi(final ArchersOfQarsi card) {
        super(card);
    }

    @Override
    public ArchersOfQarsi copy() {
        return new ArchersOfQarsi(this);
    }
}
