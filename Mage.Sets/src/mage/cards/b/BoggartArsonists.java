
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.PlainswalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class BoggartArsonists extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Scarecrow or Plains");

    static {
        filter.add(Predicates.or(
                SubType.SCARECROW.getPredicate(),
                SubType.PLAINS.getPredicate()));
    }

    public BoggartArsonists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Plainswalk
        this.addAbility(new PlainswalkAbility());

        // {2}{R}, Sacrifice Boggart Arsonists: Destroy target Scarecrow or Plains.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{2}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private BoggartArsonists(final BoggartArsonists card) {
        super(card);
    }

    @Override
    public BoggartArsonists copy() {
        return new BoggartArsonists(this);
    }
}
