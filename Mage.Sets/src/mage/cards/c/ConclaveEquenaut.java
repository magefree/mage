
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class ConclaveEquenaut extends CardImpl {

    public ConclaveEquenaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Convoke
        this.addAbility(new ConvokeAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private ConclaveEquenaut(final ConclaveEquenaut card) {
        super(card);
    }

    @Override
    public ConclaveEquenaut copy() {
        return new ConclaveEquenaut(this);
    }
}
