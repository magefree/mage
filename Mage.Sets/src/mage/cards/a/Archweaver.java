
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class Archweaver extends CardImpl {

    public Archweaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.SPIDER);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Reach, trample
        this.addAbility(ReachAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());
    }

    private Archweaver(final Archweaver card) {
        super(card);
    }

    @Override
    public Archweaver copy() {
        return new Archweaver(this);
    }
}
