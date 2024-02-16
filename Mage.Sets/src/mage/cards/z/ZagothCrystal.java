package mage.cards.z;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZagothCrystal extends CardImpl {

    public ZagothCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {B}, {G}, or {U}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ZagothCrystal(final ZagothCrystal card) {
        super(card);
    }

    @Override
    public ZagothCrystal copy() {
        return new ZagothCrystal(this);
    }
}
