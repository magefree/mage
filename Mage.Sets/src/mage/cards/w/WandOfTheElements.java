
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.WandOfTheElementsFirstToken;
import mage.game.permanent.token.WandOfTheElementsSecondToken;
import mage.target.common.TargetControlledPermanent;

/**
 * @author Loki
 */
public final class WandOfTheElements extends CardImpl {

    private static final FilterControlledPermanent islandFilter = new FilterControlledPermanent("an Island");
    private static final FilterControlledPermanent mountainFilter = new FilterControlledPermanent("a Mountain");

    static {
        islandFilter.add(SubType.ISLAND.getPredicate());
        mountainFilter.add(SubType.MOUNTAIN.getPredicate());
    }

    public WandOfTheElements(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {T}, Sacrifice an Island: Create a 2/2 blue Elemental creature token with flying.
        Ability firstAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WandOfTheElementsFirstToken()), new TapSourceCost());
        firstAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(islandFilter)));
        this.addAbility(firstAbility);

        // {T}, Sacrifice a Mountain: Create a 3/3 red Elemental creature token.
        Ability secondAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new WandOfTheElementsSecondToken()), new TapSourceCost());
        secondAbility.addCost(new SacrificeTargetCost(new TargetControlledPermanent(mountainFilter)));
        this.addAbility(secondAbility);

    }

    private WandOfTheElements(final WandOfTheElements card) {
        super(card);
    }

    @Override
    public WandOfTheElements copy() {
        return new WandOfTheElements(this);
    }
}
