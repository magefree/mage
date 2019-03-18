
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.FlipCoinEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.permanent.token.WireflyToken;

/**
 *
 * @author fireshoes
 */
public final class WireflyHive extends CardImpl {

    public WireflyHive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        // {3}, {tap}: Flip a coin. If you win the flip, create a 2/2 colorless Insect artifact creature token with flying named Wirefly.
        // If you lose the flip, destroy all permanents named Wirefly.
        FilterPermanent filter = new FilterPermanent("permanents named Wirefly");
        filter.add(new NamePredicate("Wirefly"));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new FlipCoinEffect(new CreateTokenEffect(new WireflyToken()), new DestroyAllEffect(filter)), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public WireflyHive(final WireflyHive card) {
        super(card);
    }

    @Override
    public WireflyHive copy() {
        return new WireflyHive(this);
    }
}
