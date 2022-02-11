package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfAnyNumberCostPaid;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author weirddan455
 */
public final class PrimalAdversary extends CardImpl {

    public PrimalAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Primal Adversary enters the battlefield, you may pay {1}{G} any number of times.
        // When you pay this cost one or more times, put that many +1/+1 counters on Primal Adversary,
        // then up to that many target lands you control become 3/3 Wolf creatures with haste that are still lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfAnyNumberCostPaid(
                new PrimalAdversaryEffect(), new ManaCostsImpl<>("{1}{G}")
        )));
    }

    private PrimalAdversary(final PrimalAdversary card) {
        super(card);
    }

    @Override
    public PrimalAdversary copy() {
        return new PrimalAdversary(this);
    }
}

class PrimalAdversaryEffect extends OneShotEffect {

    public PrimalAdversaryEffect() {
        super(Outcome.Benefit);
        staticText = "put that many +1/+1 counters on Primal Adversary, " +
                "then up to that many target lands you control become 3/3 Wolf creatures with haste that are still lands";
    }

    private PrimalAdversaryEffect(final PrimalAdversaryEffect effect) {
        super(effect);
    }

    @Override
    public PrimalAdversaryEffect copy() {
        return new PrimalAdversaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer timesPaid = (Integer) getValue("timesPaid");
        if (timesPaid == null || timesPaid <= 0) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(timesPaid)),
                false, staticText
        );
        ability.addEffect(new BecomesCreatureTargetEffect(new PrimalAdversaryToken(), false, true, Duration.Custom));
        ability.addTarget(new TargetPermanent(0, timesPaid, StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class PrimalAdversaryToken extends TokenImpl {

    public PrimalAdversaryToken() {
        super("", "3/3 Wolf creature with haste that's still a land");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(HasteAbility.getInstance());
    }

    private PrimalAdversaryToken(final PrimalAdversaryToken token) {
        super(token);
    }

    @Override
    public PrimalAdversaryToken copy() {
        return new PrimalAdversaryToken(this);
    }
}
