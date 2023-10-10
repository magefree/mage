package mage.cards.m;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MarchFromTheBlackGate extends CardImpl {

    public MarchFromTheBlackGate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When March from the Black Gate enters the battlefield and whenever an Army you control attacks, amass Orcs 1.
        this.addAbility(new MarchFromTheBlackGateTriggeredAbility());
    }

    private MarchFromTheBlackGate(final MarchFromTheBlackGate card) {
        super(card);
    }

    @Override
    public MarchFromTheBlackGate copy() {
        return new MarchFromTheBlackGate(this);
    }
}

class MarchFromTheBlackGateTriggeredAbility extends TriggeredAbilityImpl {

    MarchFromTheBlackGateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AmassEffect(1, SubType.ORC));
        this.setTriggerPhrase("When {this} enters the battlefield and whenever an Army you control attacks, ");
    }

    private MarchFromTheBlackGateTriggeredAbility(final MarchFromTheBlackGateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MarchFromTheBlackGateTriggeredAbility copy() {
        return new MarchFromTheBlackGateTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
            case ATTACKER_DECLARED:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (event.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                return event.getTargetId().equals(this.getSourceId());
            case ATTACKER_DECLARED:
                Permanent permanent = game.getPermanent(event.getSourceId());
                return permanent != null
                        && permanent.hasSubtype(SubType.ARMY, game)
                        && permanent.isControlledBy(this.getControllerId());
        }
        return false;
    }
}
