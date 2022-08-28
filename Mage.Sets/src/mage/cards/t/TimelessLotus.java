package mage.cards.t;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimelessLotus extends CardImpl {

    public TimelessLotus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        this.addSuperType(SuperType.LEGENDARY);

        // Timeless Lotus enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}{U}{B}{R}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(1, 1, 1, 1, 1, 0, 0, 0), new TapSourceCost()));
    }

    private TimelessLotus(final TimelessLotus card) {
        super(card);
    }

    @Override
    public TimelessLotus copy() {
        return new TimelessLotus(this);
    }
}
