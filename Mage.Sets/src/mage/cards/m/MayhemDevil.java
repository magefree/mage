package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MayhemDevil extends CardImpl {

    public MayhemDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a player sacrifices a permanent, Mayhem Devil deals 1 damage to any target.
        this.addAbility(new MayhemDevilTriggeredAbility());
    }

    private MayhemDevil(final MayhemDevil card) {
        super(card);
    }

    @Override
    public MayhemDevil copy() {
        return new MayhemDevil(this);
    }
}

class MayhemDevilTriggeredAbility extends TriggeredAbilityImpl {

    MayhemDevilTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(1));
        this.addTarget(new TargetAnyTarget());
    }

    private MayhemDevilTriggeredAbility(final MayhemDevilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SACRIFICED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return true;
    }

    @Override
    public MayhemDevilTriggeredAbility copy() {
        return new MayhemDevilTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a player sacrifices a permanent, {this} deals 1 damage to any target.";
    }
}