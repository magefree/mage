package mage.cards.r;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaugrinCrystal extends CardImpl {

    public RaugrinCrystal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {U}, {R}, or {W}.
        this.addAbility(new BlueManaAbility());
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RaugrinCrystal(final RaugrinCrystal card) {
        super(card);
    }

    @Override
    public RaugrinCrystal copy() {
        return new RaugrinCrystal(this);
    }
}
