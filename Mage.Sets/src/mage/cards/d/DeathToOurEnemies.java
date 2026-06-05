package mage.cards.d;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetAnyTargetAmount;
import mage.abilities.Ability;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DeathToOurEnemies extends CardImpl {

    public DeathToOurEnemies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.PLAN);

        // Whenever you cast a noncreature spell, create a tapped Treasure token and put a plan counter on this enchantment.
        Ability ability = new SpellCastControllerTriggeredAbility(
            new CreateTokenEffect(new TreasureToken(), 1, true),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance()).concatBy("and"));
        this.addAbility(ability);

        // When the fourth plan counter is put on this enchantment, sacrifice it. When you do, it deals 7 damage divided as you choose among one or two targets.
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
            new DamageMultiEffect(), false,
            "it deals 7 damage divided as you choose among one or two targets"
        );
        reflexive.addTarget(new TargetAnyTargetAmount(7, 1, 2));
        this.addAbility(new PlanCounterThresholdTriggeredAbility(4, reflexive));
    }

    private DeathToOurEnemies(final DeathToOurEnemies card) {
        super(card);
    }

    @Override
    public DeathToOurEnemies copy() {
        return new DeathToOurEnemies(this);
    }
}
