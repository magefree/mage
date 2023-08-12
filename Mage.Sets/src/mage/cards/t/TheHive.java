
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.permanent.token.WaspToken;

/**
 *
 * @author Loki
 */
public final class TheHive extends CardImpl {

    public TheHive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {5}, {T}: Create a 1/1 colorless Insect artifact creature token with flying named Wasp. (It can’t be blocked except by creatures with flying or reach.)
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WaspToken(), 1), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheHive(final TheHive card) {
        super(card);
    }

    @Override
    public TheHive copy() {
        return new TheHive(this);
    }
}
