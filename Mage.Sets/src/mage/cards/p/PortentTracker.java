package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapTargetEffect;
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
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PortentTracker extends CardImpl {

    public PortentTracker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Untap target land.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);

        // {T}: Choose target battle. If an opponent protects it, remove a defense counter from it. Otherwise, put a defense counter on it. Activate only as a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(new PortentTrackerEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_BATTLE));
        this.addAbility(ability);
    }

    private PortentTracker(final PortentTracker card) {
        super(card);
    }

    @Override
    public PortentTracker copy() {
        return new PortentTracker(this);
    }
}

class PortentTrackerEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(ProtectedByOpponentPredicate.instance);
    }

    PortentTrackerEffect() {
        super(Outcome.Benefit);
        staticText = "choose target battle. If an opponent protects it, " +
                "remove a defense counter from it. Otherwise, put a defense counter on it";
    }

    private PortentTrackerEffect(final PortentTrackerEffect effect) {
        super(effect);
    }

    @Override
    public PortentTrackerEffect copy() {
        return new PortentTrackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (!filter.match(permanent, source.getControllerId(), source, game)) {
            return permanent.addCounters(CounterType.DEFENSE.createInstance(), source, game);
        }
        permanent.removeCounters(CounterType.DEFENSE.createInstance(), source, game);
        return true;
    }
}
