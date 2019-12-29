package mage.cards.m;

import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoxTantalite extends CardImpl {

    public MoxTantalite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        // Suspend 3—{0}
        this.addAbility(new SuspendAbility(3, new GenericManaCost(0), this));

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private MoxTantalite(final MoxTantalite card) {
        super(card);
    }

    @Override
    public MoxTantalite copy() {
        return new MoxTantalite(this);
    }
}
