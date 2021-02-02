
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DromokaWarrior extends CardImpl {

    public DromokaWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);
    }

    private DromokaWarrior(final DromokaWarrior card) {
        super(card);
    }

    @Override
    public DromokaWarrior copy() {
        return new DromokaWarrior(this);
    }
}
