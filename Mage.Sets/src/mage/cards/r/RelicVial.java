package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RelicVial extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.CLERIC);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public RelicVial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, {T}, Sacrifice a creature: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT)
        ));
        this.addAbility(ability);

        // As long as you control a Cleric, Relic Vial has "Whenever a creature you control dies, each opponent loses 1 life and you gain 1 life."
        ability = new DiesCreatureTriggeredAbility(
                new LoseLifeOpponentsEffect(1), false,
                StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(ability), condition, "As long as you control a Cleric, " +
                "{this} has \"Whenever a creature you control dies, each opponent loses 1 life and you gain 1 life.\""
        )));
    }

    private RelicVial(final RelicVial card) {
        super(card);
    }

    @Override
    public RelicVial copy() {
        return new RelicVial(this);
    }
}
