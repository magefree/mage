package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class Electropotence extends CardImpl {

    public Electropotence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");


        // Whenever a creature enters the battlefield under your control, you may pay {2}{R}. If you do, that creature deals damage equal to its power to any target.
        Ability ability = new ElectropotenceTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private Electropotence(final Electropotence card) {
        super(card);
    }

    @Override
    public Electropotence copy() {
        return new Electropotence(this);
    }
}

class ElectropotenceTriggeredAbility extends TriggeredAbilityImpl {

    public ElectropotenceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ElectropotenceEffect());
    }

    public ElectropotenceTriggeredAbility(ElectropotenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature(game)
                && permanent.isControlledBy(this.controllerId)) {
            this.getEffects().get(0).setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under your control, you may pay {2}{R}. If you do, that creature deals damage equal to its power to any target.";
    }

    @Override
    public ElectropotenceTriggeredAbility copy() {
        return new ElectropotenceTriggeredAbility(this);
    }
}

class ElectropotenceEffect extends OneShotEffect {

    public ElectropotenceEffect() {
        super(Outcome.Damage);
    }

    public ElectropotenceEffect(final ElectropotenceEffect effect) {
        super(effect);
    }

    @Override
    public ElectropotenceEffect copy() {
        return new ElectropotenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            if (controller.chooseUse(Outcome.Damage, "Pay {2}{R} to do the damage?", source, game)) {
                // if (controller.chooseUse(Outcome.Damage, "Pay {2}{R}? If you do, " + creature.getName() + " deals damage equal to its power to any target.", game)) {
                ManaCosts manaCosts = new ManaCostsImpl<>("{2}{R}");
                if (manaCosts.pay(source, game, source, controller.getId(), false, null)) {
                    int amount = creature.getPower().getValue();
                    UUID target = source.getTargets().getFirstTarget();
                    Permanent targetCreature = game.getPermanent(target);
                    if (targetCreature != null) {
                        targetCreature.damage(amount, creature.getId(), source, game, false, true);
                    } else {
                        Player player = game.getPlayer(target);
                        if (player != null) {
                            player.damage(amount, creature.getId(), source, game);
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
