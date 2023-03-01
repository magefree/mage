package mage.cards.v;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.discard.DiscardHandAllEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class VindictiveFlamestoker extends CardImpl {

    public VindictiveFlamestoker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, put an oil counter on Vindictive Flamestoker.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.OIL.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // {6}{R}, Sacrifice Vindictive Flamestoker: Discard your hand, then draw four cards. This ability costs {1} less to activate for each oil counter on Vindictive Flamestoker.
        Ability ability = new SimpleActivatedAbility(new DiscardHandAllEffect(), new ManaCostsImpl<>("{6}{R}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new DrawCardSourceControllerEffect(4).concatBy(", then"));
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each oil counter on {this}"));
        this.addAbility(ability.setCostAdjuster(VindictiveFlamestokerAdjuster.instance));
    }

    private VindictiveFlamestoker(final VindictiveFlamestoker card) {
        super(card);
    }

    @Override
    public VindictiveFlamestoker copy() {
        return new VindictiveFlamestoker(this);
    }
}

enum VindictiveFlamestokerAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int amount = Optional
                .ofNullable(ability.getSourcePermanentIfItStillExists(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(counters -> counters.getCount(CounterType.OIL))
                .orElse(0);
        CardUtil.reduceCost(ability, amount);
    }
}
