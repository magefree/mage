
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DesertOfTheTrue extends CardImpl {

    public DesertOfTheTrue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Desert of the True enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // Cycling {1}{W}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{W}")));

    }

    private DesertOfTheTrue(final DesertOfTheTrue card) {
        super(card);
    }

    @Override
    public DesertOfTheTrue copy() {
        return new DesertOfTheTrue(this);
    }
}
