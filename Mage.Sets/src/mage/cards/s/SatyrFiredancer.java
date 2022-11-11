package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
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
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public final class SatyrFiredancer extends CardImpl {

    public SatyrFiredancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an instant or sorcery spell you control deals damage to an opponent, Satyr Firedancer deals that much damage to target creature that player controls.
        this.addAbility(new SatyrFiredancerTriggeredAbility());
    }

    private SatyrFiredancer(final SatyrFiredancer card) {
        super(card);
    }

    @Override
    public SatyrFiredancer copy() {
        return new SatyrFiredancer(this);
    }
}

class SatyrFiredancerTriggeredAbility extends TriggeredAbilityImpl {

    public SatyrFiredancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SatyrFiredancerDamageEffect(), false);
        targetAdjuster = SatyrFiredancerAdjuster.instance;
        setTriggerPhrase("Whenever an instant or sorcery spell you control deals damage to an opponent, ");
    }

    public SatyrFiredancerTriggeredAbility(final SatyrFiredancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SatyrFiredancerTriggeredAbility copy() {
        return new SatyrFiredancerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        MageObject damageSource = game.getObject(event.getSourceId());
        if (damageSource == null) {
            return false;
        }
        UUID damageTargetId = event.getTargetId();
        if (!game.getOpponents(getControllerId()).contains(damageTargetId)) {
            return false;
        }
        MageObject sourceObject = game.getObject(event.getSourceId());
        if (sourceObject == null || !sourceObject.isInstantOrSorcery(game)) {
            return false;
        }
        for (Effect effect : this.getEffects()) {
            effect.setTargetPointer(new FixedTarget(damageTargetId)); // used by adjust targets
            effect.setValue("damage", event.getAmount());
        }
        return true;
    }
}

class SatyrFiredancerDamageEffect extends OneShotEffect {

    public SatyrFiredancerDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals that much damage to target creature that player controls";
    }

    public SatyrFiredancerDamageEffect(final SatyrFiredancerDamageEffect effect) {
        super(effect);
    }

    @Override
    public SatyrFiredancerDamageEffect copy() {
        return new SatyrFiredancerDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetCreature != null && controller != null) {
            int damage = (Integer) this.getValue("damage");
            if (damage > 0) {
                targetCreature.damage(damage, source.getSourceId(), source, game, false, true);
            }
            return true;
        }
        return false;
    }
}

enum SatyrFiredancerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player opponent = game.getPlayer(ability.getEffects().get(0).getTargetPointer().getFirst(game, ability));
        if (opponent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponent.getId()));
            ability.getTargets().add(new TargetCreaturePermanent(filter));
        }
    }
}
