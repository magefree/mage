
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Boompile extends CardImpl {

    public Boompile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}: Flip a coin. If you win the flip, destroy all nonland permanents.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new FlipCoinEffect(new DestroyAllEffect(new FilterNonlandPermanent("nonland permanents"))), new TapSourceCost()));
    }

    private Boompile(final Boompile card) {
        super(card);
    }

    @Override
    public Boompile copy() {
        return new Boompile(this);
    }
}
