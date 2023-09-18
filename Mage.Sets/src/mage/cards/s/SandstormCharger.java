
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SandstormCharger extends CardImpl {

    public SandstormCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Megamorph {4}{W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{W}"), true));
    }

    private SandstormCharger(final SandstormCharger card) {
        super(card);
    }

    @Override
    public SandstormCharger copy() {
        return new SandstormCharger(this);
    }
}
