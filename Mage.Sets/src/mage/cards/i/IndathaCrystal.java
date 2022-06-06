package mage.cards.i;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndathaCrystal extends CardImpl {

    public IndathaCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {W}, {B}, or {G}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private IndathaCrystal(final IndathaCrystal card) {
        super(card);
    }

    @Override
    public IndathaCrystal copy() {
        return new IndathaCrystal(this);
    }
}
