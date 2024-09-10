
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LoneFox
 */
public final class BraidsConjurerAdept extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact, creature, or land card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public BraidsConjurerAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each player's upkeep, that player may put an artifact, creature, or land card from their hand onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(filter, true), TargetController.ANY, false));
    }

    private BraidsConjurerAdept(final BraidsConjurerAdept card) {
        super(card);
    }

    @Override
    public BraidsConjurerAdept copy() {
        return new BraidsConjurerAdept(this);
    }
}
