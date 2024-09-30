package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaltzOfRage extends CardImpl {

    public WaltzOfRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Target creature you control deals damage equal to its power to each other creature. Until end of turn, whenever a creature you control dies, exile the top card of your library. You may play it until the end of your next turn.
        this.getSpellAbility().addEffect(new WaltzOfRageEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new WaltzOfRageTriggeredAbility()));
    }

    private WaltzOfRage(final WaltzOfRage card) {
        super(card);
    }

    @Override
    public WaltzOfRage copy() {
        return new WaltzOfRage(this);
    }
}

class WaltzOfRageEffect extends OneShotEffect {

    WaltzOfRageEffect() {
        super(Outcome.Benefit);
        staticText = "target creature you control deals damage equal to its power to each other creature";
    }

    private WaltzOfRageEffect(final WaltzOfRageEffect effect) {
        super(effect);
    }

    @Override
    public WaltzOfRageEffect copy() {
        return new WaltzOfRageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        if (power < 1) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE,
                source.getControllerId(), source, game
        )) {
            if (!creature.getId().equals(permanent.getId())) {
                creature.damage(power, permanent.getId(), source, game);
            }
        }
        return true;
    }
}

class WaltzOfRageTriggeredAbility extends DelayedTriggeredAbility {

    WaltzOfRageTriggeredAbility() {
        super(new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn), Duration.EndOfTurn, false, false);
    }

    private WaltzOfRageTriggeredAbility(final WaltzOfRageTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WaltzOfRageTriggeredAbility copy() {
        return new WaltzOfRageTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!zEvent.isDiesEvent()) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        return permanent != null && permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever a creature you control dies, " +
                "exile the top card of your library. You may play it until the end of your next turn.";
    }
}
