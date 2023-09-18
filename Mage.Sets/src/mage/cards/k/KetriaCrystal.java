package mage.cards.k;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KetriaCrystal extends CardImpl {

    public KetriaCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {G}, {U}, or {R}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private KetriaCrystal(final KetriaCrystal card) {
        super(card);
    }

    @Override
    public KetriaCrystal copy() {
        return new KetriaCrystal(this);
    }
}
