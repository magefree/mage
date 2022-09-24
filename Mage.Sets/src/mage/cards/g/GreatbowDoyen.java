package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GreatbowDoyen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Archer creatures you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.ARCHER.getPredicate());
    }

    public GreatbowDoyen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other Archer creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Whenever an Archer you control deals damage to a creature, that Archer deals that much damage to that creature's controller.
        this.addAbility(new GreatbowDoyenTriggeredAbility());
    }

    private GreatbowDoyen(final GreatbowDoyen card) {
        super(card);
    }

    @Override
    public GreatbowDoyen copy() {
        return new GreatbowDoyen(this);
    }
}

class GreatbowDoyenTriggeredAbility extends TriggeredAbilityImpl {

    public GreatbowDoyenTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GreatbowDoyenEffect());
        setTriggerPhrase("Whenever an Archer you control deals damage to a creature, ");
    }

    public GreatbowDoyenTriggeredAbility(final GreatbowDoyenTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GreatbowDoyenTriggeredAbility copy() {
        return new GreatbowDoyenTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent creature = game.getPermanent(event.getSourceId());
        Permanent damagedCreature = game.getPermanent(event.getTargetId());
        if (creature != null && damagedCreature != null
                && creature.isCreature(game)
                && creature.hasSubtype(SubType.ARCHER, game)
                && creature.isControlledBy(controllerId)) {
            this.getEffects().get(0).setValue("damageAmount", event.getAmount());
            this.getEffects().get(0).setValue("controller", damagedCreature.getControllerId());
            this.getEffects().get(0).setValue("source", event.getSourceId());
            return true;
        }
        return false;
    }
}

class GreatbowDoyenEffect extends OneShotEffect {

    public GreatbowDoyenEffect() {
        super(Outcome.Damage);
        this.staticText = "that Archer deals that much damage to that creature's controller";
    }

    public GreatbowDoyenEffect(final GreatbowDoyenEffect effect) {
        super(effect);
    }

    @Override
    public GreatbowDoyenEffect copy() {
        return new GreatbowDoyenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damageAmount = (Integer) this.getValue("damageAmount");
        UUID controllerId = (UUID) this.getValue("controller");
        UUID sourceOfDamage = (UUID) this.getValue("source");
        if (damageAmount != null && controllerId != null) {
            Permanent permanent = game.getPermanent(sourceOfDamage);
            if (permanent == null) {
                permanent = (Permanent) game.getLastKnownInformation(sourceOfDamage, Zone.BATTLEFIELD);
            }
            if (permanent != null) {
                Player player = game.getPlayer(controllerId);
                if (player != null) {
                    player.damage(damageAmount, sourceOfDamage, source, game);
                    game.informPlayers(permanent.getName() + " deals " + damageAmount + " damage to " + player.getLogName());
                    return true;
                }
            }
        }
        return false;
    }
}
