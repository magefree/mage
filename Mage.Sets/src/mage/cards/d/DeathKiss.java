package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
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
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathKiss extends CardImpl {

    public DeathKiss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.BEHOLDER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever a creature an opponent controls attacks one of your opponents, double its power until end of turn.
        this.addAbility(new DeathKissAdjusterTriggeredAbility());

        // {X}{X}{R}: Monstrosity X.
        this.addAbility(new MonstrosityAbility("{X}{X}{R}", Integer.MAX_VALUE));

        // When Death Kiss becomes monstrous, goad up to X target creatures your opponents control.
        this.addAbility(new BecomesMonstrousSourceTriggeredAbility(
                new GoadTargetEffect().setText("goad up to X target creatures your opponents control")
        ).setTargetAdjuster(DeathKissAdjuster.instance));
    }

    private DeathKiss(final DeathKiss card) {
        super(card);
    }

    @Override
    public DeathKiss copy() {
        return new DeathKiss(this);
    }
}

enum DeathKissAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ((BecomesMonstrousSourceTriggeredAbility) ability).getMonstrosityValue();
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(0, xValue, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
    }
}

class DeathKissAdjusterTriggeredAbility extends TriggeredAbilityImpl {

    DeathKissAdjusterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DeathKissEffect());
    }

    private DeathKissAdjusterTriggeredAbility(final DeathKissAdjusterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DeathKissAdjusterTriggeredAbility copy() {
        return new DeathKissAdjusterTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<UUID> opponents = game.getOpponents(getControllerId());
        if (opponents.contains(game.getControllerId(event.getSourceId()))
                && opponents.contains(event.getTargetId())) {
            this.getEffects().setTargetPointer(new FixedTarget(event.getSourceId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls attacks one of your opponents, double its power until end of turn.";
    }
}

class DeathKissEffect extends OneShotEffect {

    DeathKissEffect() {
        super(Outcome.Benefit);
    }

    private DeathKissEffect(final DeathKissEffect effect) {
        super(effect);
    }

    @Override
    public DeathKissEffect copy() {
        return new DeathKissEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            game.addEffect(new BoostTargetEffect(
                    permanent.getPower().getValue(), 0
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
