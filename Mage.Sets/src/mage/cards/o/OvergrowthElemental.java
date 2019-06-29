package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OvergrowthElemental extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent(SubType.ELEMENTAL, "another target Elemental you control");
    private static final FilterPermanent filter2
            = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter2.add(AnotherPredicate.instance);
    }

    public OvergrowthElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Overgrowth Elemental enters the battlefield, put a +1/+1 counter on another target Elemental you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever another creature you control dies, you gain 1 life. If that creature was an Elemental, put a +1/+1 counter on Overgrowth Elemental.
        ability = new DiesCreatureTriggeredAbility(new GainLifeEffect(1), false, filter2, true);
        ability.addEffect(new OvergrowthElementalEffect());
        this.addAbility(ability);
    }

    private OvergrowthElemental(final OvergrowthElemental card) {
        super(card);
    }

    @Override
    public OvergrowthElemental copy() {
        return new OvergrowthElemental(this);
    }
}

class OvergrowthElementalEffect extends OneShotEffect {

    OvergrowthElementalEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature was an Elemental, put a +1/+1 counter on {this}";
    }

    private OvergrowthElementalEffect(final OvergrowthElementalEffect effect) {
        super(effect);
    }

    @Override
    public OvergrowthElementalEffect copy() {
        return new OvergrowthElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));
        if (permanent == null || !permanent.hasSubtype(SubType.ELEMENTAL, game)) {
            return false;
        }
        return new AddCountersSourceEffect(CounterType.P1P1.createInstance()).apply(game, source);
    }
}