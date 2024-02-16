
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DesertOfTheGlorified extends CardImpl {

    public DesertOfTheGlorified(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Desert of the Glorified enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // Cycling {1}{B}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{B}")));

    }

    private DesertOfTheGlorified(final DesertOfTheGlorified card) {
        super(card);
    }

    @Override
    public DesertOfTheGlorified copy() {
        return new DesertOfTheGlorified(this);
    }
}
