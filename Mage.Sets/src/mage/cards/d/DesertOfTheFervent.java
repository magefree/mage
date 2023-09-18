
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DesertOfTheFervent extends CardImpl {

    public DesertOfTheFervent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Desert of the Fervent enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{R}")));

    }

    private DesertOfTheFervent(final DesertOfTheFervent card) {
        super(card);
    }

    @Override
    public DesertOfTheFervent copy() {
        return new DesertOfTheFervent(this);
    }
}
