package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class KillSwitch extends CardImpl {

    public KillSwitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {2}, {tap}: Tap all other artifacts. They don't untap during their controllers' untap steps for as long as Kill Switch remains tapped.
        Ability ability = new SimpleActivatedAbility(new KillSwitchEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private KillSwitch(final KillSwitch card) {
        super(card);
    }

    @Override
    public KillSwitch copy() {
        return new KillSwitch(this);
    }
}

class KillSwitchEffect extends OneShotEffect {

    KillSwitchEffect() {
        super(Outcome.Benefit);
        staticText = "tap all other artifacts. They don't untap during their controllers' " +
                "untap steps for as long as {this} remains tapped";
    }

    private KillSwitchEffect(final KillSwitchEffect effect) {
        super(effect);
    }

    @Override
    public KillSwitchEffect copy() {
        return new KillSwitchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_PERMANENT_ARTIFACT,
                        source.getControllerId(), source, game
                );
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent != null) {
            permanents.remove(sourcePermanent);
            game.addEffect(new KillSwitchUntapEffect().setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        for (Permanent permanent : permanents) {
            permanent.tap(source, game);
        }
        return true;
    }
}

class KillSwitchUntapEffect extends ContinuousRuleModifyingEffectImpl {

    KillSwitchUntapEffect() {
        super(Duration.Custom, Outcome.Detriment);
    }

    private KillSwitchUntapEffect(final KillSwitchUntapEffect effect) {
        super(effect);
    }

    @Override
    public KillSwitchUntapEffect copy() {
        return new KillSwitchUntapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurnStepType() != PhaseStep.UNTAP) {
            return false;
        }
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        if (sourcePermanent == null || !sourcePermanent.isTapped()) {
            discard();
            return false;
        }
        return getTargetPointer().getTargets(game, source).contains(event.getTargetId())
                && game.isActivePlayer(game.getControllerId(event.getTargetId()));
    }
}
