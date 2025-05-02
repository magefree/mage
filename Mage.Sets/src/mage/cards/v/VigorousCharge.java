package mage.cards.v;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.DamagedBatchBySourceEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2 & L_J
 */
public final class VigorousCharge extends CardImpl {

    private static final String staticText = "Whenever that creature deals combat damage this turn, if this spell was kicked, you gain life equal to that damage";

    public VigorousCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        this.addAbility(new KickerAbility("{W}"));
        // Target creature gains trample until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        // Whenever that creature deals combat damage this turn, if this spell was kicked, you gain life equal to that damage.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(new VigorousChargeEffect(), KickedCondition.ONCE, staticText));

    }

    private VigorousCharge(final VigorousCharge card) {
        super(card);
    }

    @Override
    public VigorousCharge copy() {
        return new VigorousCharge(this);
    }
}


class VigorousChargeEffect extends OneShotEffect {

    VigorousChargeEffect() {
        super(Outcome.Benefit);
        staticText = "Whenever that creature deals combat damage this turn, if this spell was kicked, you gain life equal to that damage.";
    }

    private VigorousChargeEffect(final VigorousChargeEffect effect) {
        super(effect);
    }

    @Override
    public VigorousChargeEffect copy() {
        return new VigorousChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new VigorousChargeTriggeredAbility(new MageObjectReference(permanent, game)), source);
        return true;
    }
}

class VigorousChargeTriggeredAbility extends DelayedTriggeredAbility implements BatchTriggeredAbility<DamagedEvent> {

    private final MageObjectReference mor;

    VigorousChargeTriggeredAbility(MageObjectReference mor) {
        super(new GainLifeEffect(SavedDamageValue.MUCH), Duration.EndOfTurn, false, false);
        this.mor = mor;
    }

    private VigorousChargeTriggeredAbility(final VigorousChargeTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public VigorousChargeTriggeredAbility copy() {
        return new VigorousChargeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_BY_SOURCE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!mor.refersTo(event.getSourceId(), game) || !((DamagedBatchBySourceEvent) event).isCombatDamage()) {
            return false;
        }
        this.getEffects().setValue("damage", event.getAmount());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever that creature deals combat damage this turn, if this spell was kicked, you gain life equal to that damage.";
    }
}
