
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class EtheriumAstrolabe extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact");
    static{
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }
    
    public EtheriumAstrolabe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}{U}");


        // Flash
        this.addAbility(FlashAbility.getInstance());
        // {B}, {tap}, Sacrifice an artifact: Draw a card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    public EtheriumAstrolabe(final EtheriumAstrolabe card) {
        super(card);
    }

    @Override
    public EtheriumAstrolabe copy() {
        return new EtheriumAstrolabe(this);
    }
}
