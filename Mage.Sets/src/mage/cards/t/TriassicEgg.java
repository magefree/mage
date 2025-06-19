package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TriassicEgg extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.HATCHLING, 2);

    public TriassicEgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Put a hatchling counter on Triassic Egg.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.HATCHLING.createInstance()), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Sacrifice Triassic Egg: Choose one - You may put a creature card from your hand onto the battlefield;
        ability = new ActivateIfConditionActivatedAbility(
                new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_CREATURE_A),
                new SacrificeSourceCost(), condition
        ).hideCondition();
        ability.getModes().setChooseText("Choose one. Activate only if there are two or more hatchling counters on {this}.");

        // or return target creature card from your graveyard to the battlefield. Activate this ability only if two or more hatchling counters are on Triassic Egg.
        ability.addMode(new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)));

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
