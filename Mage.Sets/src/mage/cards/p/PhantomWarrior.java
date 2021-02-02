
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class PhantomWarrior extends CardImpl {

    public PhantomWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private PhantomWarrior(final PhantomWarrior card) {
        super(card);
    }

    @Override
    public PhantomWarrior copy() {
        return new PhantomWarrior(this);
    }
}
