
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.ExpansionSetPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author L_J
 */
public final class ApocalypseChime extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("nontoken permanents with a name originally printed in the Homelands expansion");

    static {
        filter.add(Predicates.and(
                TokenPredicate.FALSE,
                new ExpansionSetPredicate("HML")
        ));
    }

    public ApocalypseChime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");

        // {2}, {T}, Sacrifice Apocalypse Chime: Destroy all nontoken permanents with a name originally printed in the Homelands expansion. They can't be regenerated.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyAllEffect(filter, true), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private ApocalypseChime(final ApocalypseChime card) {
        super(card);
    }

    @Override
    public ApocalypseChime copy() {
        return new ApocalypseChime(this);
    }
}
