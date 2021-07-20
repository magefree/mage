package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class GreenDragon extends CardImpl {

    public GreenDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Poison Breath — When Green Dragon enters the battlefield, until end of turn, whenever a creature an opponent controls is dealt damage, destroy it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new GreenDragonDelayedTriggeredAbility(), false
        )).withFlavorWord("Poison Breath"));
    }

    private GreenDragon(final GreenDragon card) {
        super(card);
    }

    @Override
    public GreenDragon copy() {
        return new GreenDragon(this);
    }
}

class GreenDragonDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public GreenDragonDelayedTriggeredAbility() {
        super(new DestroyTargetEffect(), Duration.EndOfTurn, false);
    }

    private GreenDragonDelayedTriggeredAbility(final GreenDragonDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GreenDragonDelayedTriggeredAbility copy() {
        return new GreenDragonDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)
                && game.getOpponents(permanent.getControllerId()).contains(this.getControllerId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "until end of turn, whenever a creature an opponent controls is dealt damage, destroy it.";
    }
}
