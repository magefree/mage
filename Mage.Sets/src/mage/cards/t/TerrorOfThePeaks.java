package mage.cards.t;

import java.util.Objects;
import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

/**
 * @author arcox
 */
public final class TerrorOfThePeaks extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public TerrorOfThePeaks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Spells your opponents cast that target Terror of the Peaks cost an additional 3 life to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TerrorOfThePeaksCostIncreaseEffect()));

        // Whenever another creature enters the battlefield under your control, Terror of the Peaks deals damage equal to that creature's power to any target.
        Ability ability = new TerrorOfThePeaksTriggerAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public TerrorOfThePeaks(final TerrorOfThePeaks card) {
        super(card);
    }

    @Override
    public TerrorOfThePeaks copy() {
        return new TerrorOfThePeaks(this);
    }
}


class TerrorOfThePeaksCostIncreaseEffect extends CostModificationEffectImpl {

    TerrorOfThePeaksCostIncreaseEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells your opponents cast that target {this} cost an additional 3 life to cast";
    }

    private TerrorOfThePeaksCostIncreaseEffect(TerrorOfThePeaksCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public TerrorOfThePeaksCostIncreaseEffect copy() {
        return new TerrorOfThePeaksCostIncreaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        abilityToModify.addCost(new PayLifeCost(3));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || !(abilityToModify instanceof SpellAbility)
                || !controller.hasOpponent(abilityToModify.getControllerId(), game)) {
            return false;
        }
        for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
            Mode mode = abilityToModify.getModes().get(modeId);
            for (Target target : mode.getTargets()) {
                for (UUID targetUUID : target.getTargets()) {
                    if (targetUUID.equals(source.getSourceId())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}


class TerrorOfThePeaksTriggerAbility extends TriggeredAbilityImpl {

    public TerrorOfThePeaksTriggerAbility() {
        super(Zone.BATTLEFIELD, new TerrorOfThePeaksDamageEffect(), false);
    }

    public TerrorOfThePeaksTriggerAbility(TerrorOfThePeaksTriggerAbility ability) {
        super(ability);
    }

    @Override
    public TerrorOfThePeaksTriggerAbility copy() {
        return new TerrorOfThePeaksTriggerAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield under your control, {this} deals damage equal to that creature's power to any target.";
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isCreature()
                && permanent.isControlledBy(this.controllerId)
                && !Objects.equals(event.getTargetId(), this.getSourceId())) {
            Effect effect = this.getEffects().get(0);
            effect.setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }
}


class TerrorOfThePeaksDamageEffect extends OneShotEffect {

    public TerrorOfThePeaksDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage equal to that creature's power to any target";
    }

    public TerrorOfThePeaksDamageEffect(final TerrorOfThePeaksDamageEffect effect) {
        super(effect);
    }

    @Override
    public TerrorOfThePeaksDamageEffect copy() {
        return new TerrorOfThePeaksDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        if (creature != null) {
            int amount = creature.getPower().getValue();
            UUID target = source.getTargets().getFirstTarget();
            Permanent targetCreature = game.getPermanent(target);
            if (targetCreature != null) {
                targetCreature.damage(amount, source.getId(), game, false, true);
                return true;
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(amount, source.getId(), game);
                return true;
            }
        }
        return false;
    }
}
