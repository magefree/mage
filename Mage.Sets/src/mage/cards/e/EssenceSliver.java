
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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author cbt33
 */
public final class EssenceSliver extends CardImpl {

    public EssenceSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals damage, its controller gains that much life.
        this.addAbility(new DealsDamageAllTriggeredAbility());

    }

    private EssenceSliver(final EssenceSliver card) {
        super(card);
    }

    @Override
    public EssenceSliver copy() {
        return new EssenceSliver(this);
    }
}

class DealsDamageAllTriggeredAbility extends TriggeredAbilityImpl {

    public DealsDamageAllTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EssenceSliverGainThatMuchLifeEffect(), false);
    }

    public DealsDamageAllTriggeredAbility(final DealsDamageAllTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DealsDamageAllTriggeredAbility copy() {
        return new DealsDamageAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        if (creature != null && creature.hasSubtype(SubType.SLIVER, game)) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a Sliver deals damage, " ;
    }
}

class EssenceSliverGainThatMuchLifeEffect extends OneShotEffect {

    public EssenceSliverGainThatMuchLifeEffect() {
        super(Outcome.GainLife);
        this.staticText = "its controller gains that much life";
    }

    public EssenceSliverGainThatMuchLifeEffect(final EssenceSliverGainThatMuchLifeEffect effect) {
        super(effect);
    }

    @Override
    public EssenceSliverGainThatMuchLifeEffect copy() {
        return new EssenceSliverGainThatMuchLifeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int amount = (Integer) getValue("damage");
            if (amount > 0) {
                controller.gainLife(amount, game, source);

            }
            return true;
        }
        return false;
    }
}
