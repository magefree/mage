
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class GoblinLookout extends CardImpl {

    private static final FilterControlledPermanent filterPermanent = new FilterControlledPermanent("a Goblin");

    static {
        filterPermanent.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinLookout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}, Sacrifice a Goblin: Goblin creatures get +2/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostAllEffect(2, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, false), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filterPermanent)));
        this.addAbility(ability);
    }

    private GoblinLookout(final GoblinLookout card) {
        super(card);
    }

    @Override
    public GoblinLookout copy() {
        return new GoblinLookout(this);
    }
}
