package mage.cards.m;

import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MageHuntersOnslaught extends CardImpl {

    public MageHuntersOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Destroy target creature or planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());

        // Whenever a creature blocks this turn, its controller loses 1 life.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new MageHuntersOnslaughtDelayedTriggeredAbility()
        ).concatBy("<br>"));
    }

    private MageHuntersOnslaught(final MageHuntersOnslaught card) {
        super(card);
    }

    @Override
    public MageHuntersOnslaught copy() {
        return new MageHuntersOnslaught(this);
    }
}

class MageHuntersOnslaughtDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MageHuntersOnslaughtDelayedTriggeredAbility() {
        super(new LoseLifeTargetEffect(1), Duration.EndOfTurn, false, false);
    }

    private MageHuntersOnslaughtDelayedTriggeredAbility(final MageHuntersOnslaughtDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
        return true;
    }

    @Override
    public MageHuntersOnslaughtDelayedTriggeredAbility copy() {
        return new MageHuntersOnslaughtDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a creature blocks this turn, its controller loses 1 life.";
    }
}
