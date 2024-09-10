package mage.cards.b;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.BlockingCreatureCount;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class BarrelingAttack extends CardImpl {

    public BarrelingAttack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");

        // Target creature gains trample until end of turn.
        // When that creature becomes blocked this turn, it gets +1/+1 until end of turn for each creature blocking it.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new BarrelingAttackTriggeredAbility()));
    }

    private BarrelingAttack(final BarrelingAttack card) {
        super(card);
    }

    @Override
    public BarrelingAttack copy() {
        return new BarrelingAttack(this);
    }
}

class BarrelingAttackTriggeredAbility extends DelayedTriggeredAbility {

    BarrelingAttackTriggeredAbility() {
        super(new BoostTargetEffect(BlockingCreatureCount.TARGET, BlockingCreatureCount.TARGET, Duration.EndOfTurn),
                Duration.EndOfTurn, false);
        setTriggerPhrase("When that creature becomes blocked this turn, ");
    }

    private BarrelingAttackTriggeredAbility(final BarrelingAttackTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getFirstTarget())) {
            Permanent attackingCreature = game.getPermanentOrLKIBattlefield(event.getTargetId());
            return attackingCreature != null;
        }
        return false;
    }

    @Override
    public BarrelingAttackTriggeredAbility copy() {
        return new BarrelingAttackTriggeredAbility(this);
    }
}
