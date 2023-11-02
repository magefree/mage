package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class FlayerOfTheHatebound extends CardImpl {

    public FlayerOfTheHatebound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(new UndyingAbility());

        // Whenever Flayer of the Hatebound or another creature enters the battlefield from your graveyard, that creature deals damage equal to its power to any target.
        Ability ability = new FlayerTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private FlayerOfTheHatebound(final FlayerOfTheHatebound card) {
        super(card);
    }

    @Override
    public FlayerOfTheHatebound copy() {
        return new FlayerOfTheHatebound(this);
    }
}

class FlayerTriggeredAbility extends TriggeredAbilityImpl {

    public FlayerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FlayerEffect(), false);
    }

    private FlayerTriggeredAbility(final FlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && ((EntersTheBattlefieldEvent) event).getFromZone() == Zone.GRAVEYARD
                && permanent.isOwnedBy(controllerId)
                && (permanent.isCreature(game) || permanent.getId().equals(getSourceId()))) {
            Effect effect = this.getEffects().get(0);
            effect.setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Flayer of the Hatebound or another creature enters the battlefield from your graveyard, that creature deals damage equal to its power to any target.";
    }

    @Override
    public FlayerTriggeredAbility copy() {
        return new FlayerTriggeredAbility(this);
    }
}

class FlayerEffect extends OneShotEffect {

    public FlayerEffect() {
        super(Outcome.Damage);
        staticText = "that creature deals damage equal to its power to any target";
    }

    private FlayerEffect(final FlayerEffect effect) {
        super(effect);
    }

    @Override
    public FlayerEffect copy() {
        return new FlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD);
        }
        if (creature != null) {
            int amount = creature.getPower().getValue();
            UUID target = source.getTargets().getFirstTarget();
            Permanent targetCreature = game.getPermanent(target);
            if (targetCreature != null) {
                targetCreature.damage(amount, creature.getId(), source, game, false, true);
                return true;
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(amount, creature.getId(), source, game);
                return true;
            }
        }
        return false;
    }
}
