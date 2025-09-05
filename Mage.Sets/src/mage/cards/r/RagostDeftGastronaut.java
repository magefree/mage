package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.token.FoodAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RagostDeftGastronaut extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition();
    private static final Hint hint = new ConditionHint(condition);

    public RagostDeftGastronaut(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LOBSTER);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Artifacts you control are Foods in addition to their other types and have "{2}, {T}, Sacrifice this artifact: You gain 3 life."
        ContinuousEffect effect = new AddCardSubtypeAllEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS, SubType.FOOD, null
        );
        effect.getDependedToTypes().add(DependencyType.ArtifactAddingRemoving);
        Ability ability = new SimpleStaticAbility(effect);
        effect = new GainAbilityAllEffect(
                new FoodAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS,
                "and have \"{2}, {T}, Sacrifice this artifact: You gain 3 life.\""
        );
        effect.getDependedToTypes().add(DependencyType.ArtifactAddingRemoving);
        ability.addEffect(effect);
        this.addAbility(ability);

        // {1}, {T}, Sacrifice a Food: Ragost deals 3 damage to each opponent.
        ability = new SimpleActivatedAbility(
                new DamagePlayersEffect(3, TargetController.OPPONENT), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_FOOD));
        this.addAbility(ability);

        // At the beginning of each end step, if you gained life this turn, untap Ragost.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new UntapSourceEffect(), false, condition
        ).addHint(hint), new PlayerGainedLifeWatcher());
    }

    private RagostDeftGastronaut(final RagostDeftGastronaut card) {
        super(card);
    }

    @Override
    public RagostDeftGastronaut copy() {
        return new RagostDeftGastronaut(this);
    }
}
