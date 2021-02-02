
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author JotaPeRL
 */
public final class SimicInitiate extends CardImpl {

    public SimicInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 1
        this.addAbility(new GraftAbility(this, 1));
    }

    private SimicInitiate(final SimicInitiate card) {
        super(card);
    }

    @Override
    public SimicInitiate copy() {
        return new SimicInitiate(this);
    }
}
