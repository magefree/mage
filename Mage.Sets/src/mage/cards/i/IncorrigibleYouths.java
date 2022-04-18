
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class IncorrigibleYouths extends CardImpl {

    public IncorrigibleYouths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Madness {2}{R}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{2}{R}")));
    }

    private IncorrigibleYouths(final IncorrigibleYouths card) {
        super(card);
    }

    @Override
    public IncorrigibleYouths copy() {
        return new IncorrigibleYouths(this);
    }
}
