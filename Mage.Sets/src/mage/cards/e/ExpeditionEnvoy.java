
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class ExpeditionEnvoy extends CardImpl {

    public ExpeditionEnvoy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
    }

    private ExpeditionEnvoy(final ExpeditionEnvoy card) {
        super(card);
    }

    @Override
    public ExpeditionEnvoy copy() {
        return new ExpeditionEnvoy(this);
    }
}
