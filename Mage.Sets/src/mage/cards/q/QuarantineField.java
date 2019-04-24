
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class QuarantineField extends CardImpl {

    public QuarantineField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{X}{X}{W}{W}");

        // Quarantine Field enters the battlefield with X isolation counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.ISOLATION.createInstance())));

        // When Quarantine Field enters the battlefield, for each isolation counter on it, exile up to one target nonland permanent an opponenet controls until Quarantine Field leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new QuarantineFieldEffect(), false);
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

    }

    public QuarantineField(final QuarantineField card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof EntersBattlefieldTriggeredAbility) {
            Permanent sourceObject = game.getPermanent(ability.getSourceId());
            if (sourceObject != null) {
                int isolationCounters = sourceObject.getCounters(game).getCount(CounterType.ISOLATION);
                FilterNonlandPermanent filter = new FilterNonlandPermanent("up to " + isolationCounters + " nonland permanents controlled by any opponents");
                filter.add(new ControllerPredicate(TargetController.OPPONENT));
                ability.addTarget(new TargetPermanent(0, isolationCounters, filter, false));
            }

        }
    }

    @Override
    public QuarantineField copy() {
        return new QuarantineField(this);
    }
}

class QuarantineFieldEffect extends OneShotEffect {

    public QuarantineFieldEffect() {
        super(Outcome.Exile);
        this.staticText = "for each isolation counter on it, exile up to one target nonland permanent an opponenet controls until {this} leaves the battlefield";
    }

    public QuarantineFieldEffect(final QuarantineFieldEffect effect) {
        super(effect);
    }

    @Override
    public QuarantineFieldEffect copy() {
        return new QuarantineFieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        // If the source permanent leaves the battlefield before its triggered ability resolves,
        // the targets won't be exiled.
        if (permanent != null) {
            return new ExileTargetEffect(CardUtil.getCardExileZoneId(game, source), permanent.getIdName()).apply(game, source);
        }
        return false;
    }
}
