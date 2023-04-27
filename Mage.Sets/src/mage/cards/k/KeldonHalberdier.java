
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class KeldonHalberdier extends CardImpl {

    public KeldonHalberdier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Suspend 4-{R}
        this.addAbility(new SuspendAbility(4, new ManaCostsImpl<>("{R}"), this));
    }

    private KeldonHalberdier(final KeldonHalberdier card) {
        super(card);
    }

    @Override
    public KeldonHalberdier copy() {
        return new KeldonHalberdier(this);
    }
}
