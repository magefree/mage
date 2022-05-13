
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class TriassicEgg extends CardImpl {

    public TriassicEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Put a hatchling counter on Triassic Egg.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersSourceEffect(CounterType.HATCHLING.createInstance(), true),
                new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Sacrifice Triassic Egg: Choose one - You may put a creature card from your hand onto the battlefield;
        ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A),
                new SacrificeSourceCost(),
                new SourceHasCounterCondition(CounterType.HATCHLING, 2, Integer.MAX_VALUE));

        // or return target creature card from your graveyard to the battlefield. Activate this ability only if two or more hatchling counters are on Triassic Egg.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD);
        mode.addTarget(target);
        ability.addMode(mode);

        this.addAbility(ability);
    }

    private TriassicEgg(final TriassicEgg card) {
        super(card);
    }

    @Override
    public TriassicEgg copy() {
        return new TriassicEgg(this);
    }
}
