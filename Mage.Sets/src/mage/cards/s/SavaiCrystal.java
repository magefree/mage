package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavaiCrystal extends CardImpl {

    public SavaiCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {R}, {W}, or {B}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SavaiCrystal(final SavaiCrystal card) {
        super(card);
    }

    @Override
    public SavaiCrystal copy() {
        return new SavaiCrystal(this);
    }
}
