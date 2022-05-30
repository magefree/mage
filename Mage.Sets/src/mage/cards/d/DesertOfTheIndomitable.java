
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DesertOfTheIndomitable extends CardImpl {

    public DesertOfTheIndomitable(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Desert of the Indomitable enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Cycling {1}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{G}")));

    }

    private DesertOfTheIndomitable(final DesertOfTheIndomitable card) {
        super(card);
    }

    @Override
    public DesertOfTheIndomitable copy() {
        return new DesertOfTheIndomitable(this);
    }
}
