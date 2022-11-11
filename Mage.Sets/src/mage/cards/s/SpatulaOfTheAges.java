
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.card.ExpansionSetPredicate;

/**
 *
 * @author L_J
 */
public final class SpatulaOfTheAges extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("a silver-bordered permanent card");

    static {
        filter.add(Predicates.and(
                Predicates.not(SuperType.BASIC.getPredicate()), // all Un-set basic lands are black bordered cards, and thus illegal choices
                Predicates.not(new NamePredicate("Steamflogger Boss")), // printed in Unstable with a black border
                Predicates.or(new ExpansionSetPredicate("UGL"), new ExpansionSetPredicate("UNH"), new ExpansionSetPredicate("UST"))
        ));
    }

    public SpatulaOfTheAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {4}, {T}, Sacrifice Spatula of the Ages: You may put a silver-bordered permanent card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private SpatulaOfTheAges(final SpatulaOfTheAges card) {
        super(card);
    }

    @Override
    public SpatulaOfTheAges copy() {
        return new SpatulaOfTheAges(this);
    }
}
