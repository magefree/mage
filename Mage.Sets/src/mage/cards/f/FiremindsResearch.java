package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FiremindsResearch extends CardImpl {

    public FiremindsResearch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{R}");

        // Whenever you cast an instant or sorcery spell, put a charge counter on Firemind's Research.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        ));

        // {1}{U}, Remove two charge counters from Firemind's Research: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new RemoveCountersSourceCost(
                CounterType.CHARGE.createInstance(2)
        ));
        this.addAbility(ability);

        // {1}{R}, Remove five charge counters from Firemind's Research: It deals 5 damage to any target.
        ability = new SimpleActivatedAbility(
                new DamageTargetEffect(5, "it"),
                new ManaCostsImpl<>("{1}{R}")
        );
        ability.addCost(new RemoveCountersSourceCost(
                CounterType.CHARGE.createInstance(5)
        ));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FiremindsResearch(final FiremindsResearch card) {
        super(card);
    }

    @Override
    public FiremindsResearch copy() {
        return new FiremindsResearch(this);
    }
}
