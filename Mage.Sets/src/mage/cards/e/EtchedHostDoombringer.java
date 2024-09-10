package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.ProtectedByOpponentPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtchedHostDoombringer extends CardImpl {

    public EtchedHostDoombringer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Etched Host Doombringer enters the battlefield, choose one
        // * Target opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // * Choose target battle. If an opponent protects it, remove three defense counters from it. Otherwise, put three defense counters on it.
        ability.addMode(new Mode(new EtchedHostDoombringerEffect())
                .addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_BATTLE)));
    }

    private EtchedHostDoombringer(final EtchedHostDoombringer card) {
        super(card);
    }

    @Override
    public EtchedHostDoombringer copy() {
        return new EtchedHostDoombringer(this);
    }
}

class EtchedHostDoombringerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(ProtectedByOpponentPredicate.instance);
    }

    EtchedHostDoombringerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target battle. If an opponent protects it, " +
                "remove three defense counters from it. Otherwise, put three defense counters on it";
    }

    private EtchedHostDoombringerEffect(final EtchedHostDoombringerEffect effect) {
        super(effect);
    }

    @Override
    public EtchedHostDoombringerEffect copy() {
        return new EtchedHostDoombringerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (!filter.match(permanent, source.getControllerId(), source, game)) {
            return permanent.addCounters(CounterType.DEFENSE.createInstance(3), source, game);
        }
        permanent.removeCounters(CounterType.DEFENSE.createInstance(3), source, game);
        return true;
    }
}
