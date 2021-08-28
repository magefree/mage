
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.ThopterToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ThopterFoundry extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a nontoken artifact");

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
        filter.add(TokenPredicate.FALSE);
    }

    public ThopterFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W/B}{U}");

        // {1}, Sacrifice a nontoken artifact: Create a 1/1 blue Thopter artifact creature token with flying. You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new ThopterToken()), new GenericManaCost(1));
        ability.addEffect(new GainLifeEffect(1));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private ThopterFoundry(final ThopterFoundry card) {
        super(card);
    }

    @Override
    public ThopterFoundry copy() {
        return new ThopterFoundry(this);
    }
}
