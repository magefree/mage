
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.ChampionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Plopman
 */
public final class WanderwineProphets extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Merfolk");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public WanderwineProphets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.subtype.add(SubType.MERFOLK, SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Champion a Merfolk
        this.addAbility(new ChampionAbility(this, SubType.MERFOLK, false));
        // Whenever Wanderwine Prophets deals combat damage to a player, you may sacrifice a Merfolk. If you do, take an extra turn after this one.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DoIfCostPaid(
                new AddExtraTurnControllerEffect(),
                new SacrificeTargetCost(new TargetControlledPermanent(1, 1, filter, true))
        ), true);
        this.addAbility(ability);

    }

    public WanderwineProphets(final WanderwineProphets card) {
        super(card);
    }

    @Override
    public WanderwineProphets copy() {
        return new WanderwineProphets(this);
    }
}
