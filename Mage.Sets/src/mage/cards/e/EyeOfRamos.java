package mage.cards.e;

import java.util.UUID;
import mage.Mana;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author Jgod
 */
public final class EyeOfRamos extends CardImpl {

    public EyeOfRamos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // Sacrifice Eye of Ramos: Add {U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new SacrificeSourceCost()));
    }

    private EyeOfRamos(final EyeOfRamos card) {
        super(card);
    }

    @Override
    public EyeOfRamos copy() {
        return new EyeOfRamos(this);
    }
}
