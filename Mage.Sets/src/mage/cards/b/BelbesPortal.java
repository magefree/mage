
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

/**
 *
 * @author andyfries
 */
public final class BelbesPortal extends CardImpl {

    public BelbesPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // As Belbe's Portal enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.PutCreatureInPlay)));
        // {3}, {tap}: You may put a creature card of the chosen type from your hand onto the battlefield.
        FilterCreatureCard filter = new FilterCreatureCard("a creature card of the chosen type");
        filter.add(ChosenSubtypePredicate.TRUE);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(filter),
                new ManaCostsImpl<>("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private BelbesPortal(final BelbesPortal card) {
        super(card);
    }

    @Override
    public BelbesPortal copy() {
        return new BelbesPortal(this);
    }
}
