package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

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

    public GruulRagebeast(final GruulRagebeast card) {
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
        this.addTarget(new TargetPermanent(1, 1, StaticFilters.FILTER_PERMANENT, true)); // for info only
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
                && permanent.isCreature()) {
            for (Effect effect : this.getEffects()) {
                if (effect instanceof GruulRagebeastEffect) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));

                    // triggered creature info stores as target
                    Target targetInfo = this.getTargets().get(1);
                    targetInfo.clearChosen();
                    targetInfo.add(permanent.getId(), game);
                    targetInfo.withChooseHint(permanent.getLogName());
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature enters the battlefield under your control, " + super.getRule();
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
        // second target for info only, so use only first
        Permanent triggeredCreature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (triggeredCreature != null
                && target != null
                && triggeredCreature.isCreature()
                && target.isCreature()) {
            return triggeredCreature.fight(target, source, game);
        }
        return false;
    }

    @Override
    public GruulRagebeastEffect copy() {
        return new GruulRagebeastEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        // additional info for stack ability about triggered permanent (it store info in second target)
        Target target = mode.getTargets().get(1);
        String info = (target.getChooseHint() == null ? "" : " (your fighting creature: " + target.getChooseHint() + ")");

        return "that creature fights target creature an opponent controls" + info;
    }
}
