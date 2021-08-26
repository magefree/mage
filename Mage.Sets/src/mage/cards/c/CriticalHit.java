package mage.cards.c;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DieRolledEvent;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CriticalHit extends CardImpl {

    public CriticalHit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Target creature gains double strike until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // When you roll a natural 20, return Critical Hit from your graveyard to your hand.
        this.addAbility(new CriticalHitTriggeredAbility());
    }

    private CriticalHit(final CriticalHit card) {
        super(card);
    }

    @Override
    public CriticalHit copy() {
        return new CriticalHit(this);
    }
}

class CriticalHitTriggeredAbility extends TriggeredAbilityImpl {

    CriticalHitTriggeredAbility() {
        super(Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect());
    }

    private CriticalHitTriggeredAbility(final CriticalHitTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DIE_ROLLED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DieRolledEvent drEvent = (DieRolledEvent) event;
        return isControlledBy(event.getPlayerId())
                && drEvent.getNaturalResult() == 20;
    }

    @Override
    public CriticalHitTriggeredAbility copy() {
        return new CriticalHitTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When you roll a natural 20, return {this} from your graveyard to your hand.";
    }
}
