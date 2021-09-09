package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfAnyNumberCostPaid;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpectralAdversary extends CardImpl {

    public SpectralAdversary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Spectral Adversary enters the battlefield, you may pay {1}{U} any number of times. When you pay this cost one or more times, put that many +1/+1 counters on Spectral Adversary, then up to that many other target artifacts, creatures, and/or enchantments phase out.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfAnyNumberCostPaid(
                new SpectralAdversaryEffect(), new ManaCostsImpl<>("{1}{U}")
        )));
    }

    private SpectralAdversary(final SpectralAdversary card) {
        super(card);
    }

    @Override
    public SpectralAdversary copy() {
        return new SpectralAdversary(this);
    }
}

class SpectralAdversaryEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterPermanent("other artifacts, creatures, and/or enchantments");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
        filter.add(AnotherPredicate.instance);
    }

    SpectralAdversaryEffect() {
        super(Outcome.Benefit);
        staticText = "put that many +1/+1 counters on {this}, then up to that many other target " +
                "artifacts, creatures, and/or enchantments phase out";
    }

    private SpectralAdversaryEffect(final SpectralAdversaryEffect effect) {
        super(effect);
    }

    @Override
    public SpectralAdversaryEffect copy() {
        return new SpectralAdversaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Integer timesPaid = (Integer) getValue("timesPaid");
        if (timesPaid == null || timesPaid <= 0) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(timesPaid)),
                false, staticText
        );
        ability.addEffect(new PhaseOutTargetEffect());
        ability.addTarget(new TargetPermanent(0, timesPaid, filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
