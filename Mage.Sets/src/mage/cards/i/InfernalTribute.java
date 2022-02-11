
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Styxo
 */
public final class InfernalTribute extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a nontoken permanent");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public InfernalTribute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}{B}");

        // {2}, Sacrifice a nontoken permanent: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private InfernalTribute(final InfernalTribute card) {
        super(card);
    }

    @Override
    public InfernalTribute copy() {
        return new InfernalTribute(this);
    }
}
