
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileGraveyardAllPlayersEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class ScavengerGrounds extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public ScavengerGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}, Sacrifice a Desert: Exile all cards from all graveyards.
        Ability ability2 = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileGraveyardAllPlayersEffect(),
                new ManaCostsImpl<>("{2}"));
        ability2.addCost(new TapSourceCost());
        ability2.addCost(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true)));
        this.addAbility(ability2);
    }

    private ScavengerGrounds(final ScavengerGrounds card) {
        super(card);
    }

    @Override
    public ScavengerGrounds copy() {
        return new ScavengerGrounds(this);
    }
}
