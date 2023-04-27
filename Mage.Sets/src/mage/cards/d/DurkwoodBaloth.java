
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class DurkwoodBaloth extends CardImpl {

    public DurkwoodBaloth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Suspend 5-{G}
        this.addAbility(new SuspendAbility(5, new ManaCostsImpl<>("{G}"), this));
    }

    private DurkwoodBaloth(final DurkwoodBaloth card) {
        super(card);
    }

    @Override
    public DurkwoodBaloth copy() {
        return new DurkwoodBaloth(this);
    }
}
