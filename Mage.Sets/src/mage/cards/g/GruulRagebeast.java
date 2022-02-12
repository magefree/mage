package mage.cards.g;

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
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.GameLog;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class GruulRagebeast extends CardImpl {

    public GruulRagebeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Gruul Ragebeast or another creature enters the battlefield under your control, that creature fights target creature an opponent controls.
        this.addAbility(new GruulRagebeastTriggeredAbility());
    }

    private GruulRagebeast(final GruulRagebeast card) {
        super(card);
    }

    @Override
    public GruulRagebeast copy() {
        return new GruulRagebeast(this);
    }
}

class GruulRagebeastTriggeredAbility extends TriggeredAbilityImpl {

    GruulRagebeastTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GruulRagebeastEffect(), false);
        this.addTarget(new TargetOpponentsCreaturePermanent().withChooseHint("for fighting"));
    }

    private GruulRagebeastTriggeredAbility(final GruulRagebeastTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GruulRagebeastTriggeredAbility copy() {
        return new GruulRagebeastTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        Permanent sourceObject = game.getPermanent(this.getSourceId());
        if (sourceObject != null
                && permanent != null
                && permanent.isControlledBy(this.getControllerId())
                && permanent.isCreature(game)) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof GruulRagebeastEffect) {
                    effect.setTargetPointer(
                            new FixedTarget(event.getTargetId(), game)
                                    .withData("triggeredName", GameLog.getColoredObjectIdNameForTooltip(sourceObject))
                    );
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        // that triggers depends on stack order, so make each trigger unique with extra info
        String triggeredInfo = "";
        if (this.getEffects().get(0).getTargetPointer() != null) {
            triggeredInfo = " Your fighting creature: " + this.getEffects().get(0).getTargetPointer().getData("triggeredName") + ".";
        }
        return "Whenever {this} or another creature enters the battlefield under your control, "
                + "that creature fights target creature an opponent controls." + triggeredInfo;
    }
}

class GruulRagebeastEffect extends OneShotEffect {

    GruulRagebeastEffect() {
        super(Outcome.Damage);
    }

    private GruulRagebeastEffect(final GruulRagebeastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent triggeredCreature = game.getPermanent(this.targetPointer.getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature(game)
                && target.isCreature(game)) {
            return triggeredCreature.fight(target, source, game);
        }
        return false;
    }

    @Override
    public GruulRagebeastEffect copy() {
        return new GruulRagebeastEffect(this);
    }
}
