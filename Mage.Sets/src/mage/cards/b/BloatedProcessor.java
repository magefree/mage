package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloatedProcessor extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent(SubType.PHYREXIAN, "another Phyrexian");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BloatedProcessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Sacrifice another Phyrexian: Put a +1/+1 counter on Bloated Processor.
        this.addAbility(new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new SacrificeTargetCost(filter)
        ));

        // When Bloated Processor dies, incubate X, where X is its power.
        this.addAbility(new DiesSourceTriggeredAbility(new BloatedProcessorEffect()));
    }

    private BloatedProcessor(final BloatedProcessor card) {
        super(card);
    }

    @Override
    public BloatedProcessor copy() {
        return new BloatedProcessor(this);
    }
}

class BloatedProcessorEffect extends OneShotEffect {

    BloatedProcessorEffect() {
        super(Outcome.Benefit);
        staticText = "incubate X, where X is its power";
    }

    private BloatedProcessorEffect(final BloatedProcessorEffect effect) {
        super(effect);
    }

    @Override
    public BloatedProcessorEffect copy() {
        return new BloatedProcessorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        return permanent != null && IncubateEffect.doIncubate(permanent.getPower().getValue(), game, source);
    }
}
