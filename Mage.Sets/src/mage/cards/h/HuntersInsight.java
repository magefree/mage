package mage.cards.h;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class HuntersInsight extends CardImpl {

    public HuntersInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Choose target creature you control. Whenever that creature deals combat damage to a player or planeswalker this turn, draw that many cards.
        this.getSpellAbility().addEffect(new HuntersInsightEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private HuntersInsight(final HuntersInsight card) {
        super(card);
    }

    @Override
    public HuntersInsight copy() {
        return new HuntersInsight(this);
    }
}

class HuntersInsightEffect extends OneShotEffect {

    HuntersInsightEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target creature you control. Whenever that creature deals combat damage " +
                "to a player or planeswalker this turn, draw that many cards.";
    }

    private HuntersInsightEffect(final HuntersInsightEffect effect) {
        super(effect);
    }

    @Override
    public HuntersInsightEffect copy() {
        return new HuntersInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new HuntersInsightTriggeredAbility(new MageObjectReference(permanent, game)), source);
        return true;
    }
}

class HuntersInsightTriggeredAbility extends DelayedTriggeredAbility {

    private final MageObjectReference mor;

    HuntersInsightTriggeredAbility(MageObjectReference mor) {
        super(null, Duration.EndOfTurn, false, false);
        this.mor = mor;
    }

    private HuntersInsightTriggeredAbility(final HuntersInsightTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public HuntersInsightTriggeredAbility copy() {
        return new HuntersInsightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!mor.refersTo(event.getSourceId(), game)
                || !((DamagedEvent) event).isCombatDamage()) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && !permanent.isPlaneswalker(game)) {
            return false;
        }
        this.getEffects().clear();
        this.addEffect(new DrawCardSourceControllerEffect(event.getAmount()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever this creature deals combat damage to a player or planeswalker, draw that many cards.";
    }
}
