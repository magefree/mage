
package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
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
 * @author Ketsuban
 */
public final class EverythingamajigB extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("a silver-bordered permanent card");

    static {
        filter.add(Predicates.and(
                Predicates.not(SuperType.BASIC.getPredicate()), // all Un-set basic lands are black bordered cards, and thus illegal choices
                Predicates.not(new NamePredicate("Steamflogger Boss")), // printed in Unstable with a black border
                Predicates.or(new ExpansionSetPredicate("UGL"), new ExpansionSetPredicate("UNH"), new ExpansionSetPredicate("UST"))
        ));
    }

    public EverythingamajigB(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Fool's Tome
        // 2, T: Draw a card. Activate this ability only if you have no cards in hand.
        Ability ability1 = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(2), HellbentCondition.instance);
        ability1.addCost(new TapSourceCost());
        this.addAbility(ability1);
        
        // Tower of Eons
        // 8, T: You gain 10 life.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(10), new GenericManaCost(8));
        ability2.addCost(new TapSourceCost());
        this.addAbility(ability2);

        // Spatula of the Ages
        // 4, T, Sacrifice Everythingamajig: You may put a silver-bordered permanent card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private EverythingamajigB(final EverythingamajigB card) {
        super(card);
    }

    @Override
    public EverythingamajigB copy() {
        return new EverythingamajigB(this);
    }
}
