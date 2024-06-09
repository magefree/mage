

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Backfir3
 */
public final class Anaconda extends CardImpl {

    public Anaconda (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SNAKE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new SwampwalkAbility());
    }

    private Anaconda(final Anaconda card) {
        super(card);
    }

    @Override
    public Anaconda copy() {
        return new Anaconda(this);
    }
}
