
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;

/**
 *
 * @author emerald000
 */
public final class GoblinPyromancer extends CardImpl {

    private static final FilterPermanent filterPermanent = new FilterPermanent("Goblins");

    static {
        filterPermanent.add(SubType.GOBLIN.getPredicate());
    }

    public GoblinPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Goblin Pyromancer enters the battlefield, Goblin creatures get +3/+0 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(3, 0, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE_GOBLINS, false)));

        // At the beginning of the end step, destroy all Goblins.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DestroyAllEffect(filterPermanent, false), TargetController.NEXT, false));
    }

    private GoblinPyromancer(final GoblinPyromancer card) {
        super(card);
    }

    @Override
    public GoblinPyromancer copy() {
        return new GoblinPyromancer(this);
    }
}
