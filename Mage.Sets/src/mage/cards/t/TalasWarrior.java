
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class TalasWarrior extends CardImpl {

    public TalasWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Talas Warrior can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
    }

    private TalasWarrior(final TalasWarrior card) {
        super(card);
    }

    @Override
    public TalasWarrior copy() {
        return new TalasWarrior(this);
    }
}
