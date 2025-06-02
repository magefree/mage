package mage.cards.t;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGreatHenge extends CardImpl {

    public TheGreatHenge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{7}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // This spell costs {X} less to cast, where X is the greatest power among creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TheGreatHengeCostReductionEffect())
                .addHint(GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.getHint()));

        // {T}: Add {G}{G}. You gain 2 life.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(2), new TapSourceCost());
        ability.addEffect(new GainLifeEffect(2).setText("You gain 2 life."));
        this.addAbility(ability);

        // Whenever a nontoken creature you control enters, put a +1/+1 counter on it and draw a card.
        ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on it"),
                StaticFilters.FILTER_CREATURE_NON_TOKEN, false, SetTargetPointer.PERMANENT
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private TheGreatHenge(final TheGreatHenge card) {
        super(card);
    }

    @Override
    public TheGreatHenge copy() {
        return new TheGreatHenge(this);
    }
}

class TheGreatHengeCostReductionEffect extends CostModificationEffectImpl {

    TheGreatHengeCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {X} less to cast, where X is the greatest power among creatures you control";
    }

    private TheGreatHengeCostReductionEffect(final TheGreatHengeCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        // TODO: that abilityToModify should be source, but there is currently a bug with that #11166
        int reductionAmount = GreatestAmongPermanentsValue.POWER_CONTROLLED_CREATURES.calculate(game, abilityToModify, this);
        CardUtil.reduceCost(abilityToModify, Math.max(0, reductionAmount));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public TheGreatHengeCostReductionEffect copy() {
        return new TheGreatHengeCostReductionEffect(this);
    }
}
