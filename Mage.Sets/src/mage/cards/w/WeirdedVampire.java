
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WeirdedVampire extends CardImpl {

    public WeirdedVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Madness {2}{B}
        this.addAbility(new MadnessAbility(new ManaCostsImpl("{2}{B}")));
    }

    private WeirdedVampire(final WeirdedVampire card) {
        super(card);
    }

    @Override
    public WeirdedVampire copy() {
        return new WeirdedVampire(this);
    }
}
