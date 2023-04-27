package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author cbt33
 */
public final class EssenceSliver extends CardImpl {

    public EssenceSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals damage, its controller gains that much life.
        this.addAbility(new EssenceSliverTriggeredAbility());

    }

    private EssenceSliver(final EssenceSliver card) {
        super(card);
    }

    @Override
    public EssenceSliver copy() {
        return new EssenceSliver(this);
    }
}

class EssenceSliverTriggeredAbility extends TriggeredAbilityImpl {

    public EssenceSliverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EssenceSliverEffect(), false);
        setTriggerPhrase("Whenever a Sliver deals damage, ");
    }

    public EssenceSliverTriggeredAbility(final EssenceSliverTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EssenceSliverTriggeredAbility copy() {
        return new EssenceSliverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent sliver = game.getPermanent(event.getSourceId());
        if (sliver != null
                && sliver.hasSubtype(SubType.SLIVER, game)
                && sliver.getControllerId() != null) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
                effect.setTargetPointer(new FixedTarget(sliver.getControllerId()));
            }
            return true;
        }
        return false;
    }
}

class EssenceSliverEffect extends OneShotEffect {

    public EssenceSliverEffect() {
        super(Outcome.GainLife);
        this.staticText = "its controller gains that much life";
    }

    public EssenceSliverEffect(final EssenceSliverEffect effect) {
        super(effect);
    }

    @Override
    public EssenceSliverEffect copy() {
        return new EssenceSliverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controllerOfSliver = game.getPlayer(targetPointer.getFirst(game, source));
        if (controllerOfSliver != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                controllerOfSliver.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}
