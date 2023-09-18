package mage.cards.c;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ReconfigureAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainflailCentipede extends CardImpl {

    public ChainflailCentipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.EQUIPMENT);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Chainflail Centipede or equipped creature attacks, it gets +2/+0 until end of turn.
        this.addAbility(new ChainflailCentipedeTriggeredAbility());

        // Reconfigure {2}
        this.addAbility(new ReconfigureAbility("{2}"));
    }

    private ChainflailCentipede(final ChainflailCentipede card) {
        super(card);
    }

    @Override
    public ChainflailCentipede copy() {
        return new ChainflailCentipede(this);
    }
}

class ChainflailCentipedeTriggeredAbility extends TriggeredAbilityImpl {

    ChainflailCentipedeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(2, 0));
    }

    private ChainflailCentipedeTriggeredAbility(final ChainflailCentipedeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChainflailCentipedeTriggeredAbility copy() {
        return new ChainflailCentipedeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID attacker;
        if (!game.getCombat().getAttackers().contains(getSourceId())) {
            Permanent permanent = getSourcePermanentOrLKI(game);
            if (permanent != null && game.getCombat().getAttackers().contains(permanent.getAttachedTo())) {
                attacker = permanent.getAttachedTo();
            } else {
                attacker = null;
            }
        } else {
            attacker = getSourceId();
        }
        if (attacker == null) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(attacker, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or equipped creature attacks, it gets +2/+0 until end of turn.";
    }
}
