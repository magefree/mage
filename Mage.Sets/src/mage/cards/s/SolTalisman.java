package mage.cards.s;

import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.SuspendAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolTalisman extends CardImpl {

    public SolTalisman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        // Suspend 3—{1}
        this.addAbility(new SuspendAbility(3, new GenericManaCost(1), this));

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private SolTalisman(final SolTalisman card) {
        super(card);
    }

    @Override
    public SolTalisman copy() {
        return new SolTalisman(this);
    }
}
