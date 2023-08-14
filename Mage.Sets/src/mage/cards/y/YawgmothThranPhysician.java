package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YawgmothThranPhysician extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.HUMAN, "Humans");

    public YawgmothThranPhysician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Protection from Humans.
        this.addAbility(new ProtectionAbility(filter));

        // Pay 1 life, Sacrifice another creature: Put a -1/-1 counter on up to one target creature and draw a card.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()), new PayLifeCost(1)
        );
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // {B}{B}, Discard a card: Proliferate.
        ability = new SimpleActivatedAbility(new ProliferateEffect(), new ManaCostsImpl<>("{B}{B}"));
        ability.addCost(new DiscardCardCost());
        this.addAbility(ability);
    }

    private YawgmothThranPhysician(final YawgmothThranPhysician card) {
        super(card);
    }

    @Override
    public YawgmothThranPhysician copy() {
        return new YawgmothThranPhysician(this);
    }
}
