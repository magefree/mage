package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ToughnessGreaterThanPowerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoranBesiegedByTime extends CardImpl {

    private static final FilterCard filter = new FilterCard("each creature spell you cast with toughness greater than its power");

    static {
        filter.add(ToughnessGreaterThanPowerPredicate.instance);
    }

    public DoranBesiegedByTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Each creature spell you cast with toughness greater than its power costs {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever a creature you control attacks or blocks, it gets +X/+X until end of turn, where X is the difference between its power and toughness.
        this.addAbility(new DoranBesiegedByTimeTriggeredAbility());
    }

    private DoranBesiegedByTime(final DoranBesiegedByTime card) {
        super(card);
    }

    @Override
    public DoranBesiegedByTime copy() {
        return new DoranBesiegedByTime(this);
    }
}

class DoranBesiegedByTimeTriggeredAbility extends TriggeredAbilityImpl {

    DoranBesiegedByTimeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoranBesiegedByTimeEffect());
        setTriggerPhrase("Whenever a creature you control attacks or blocks, ");
    }

    private DoranBesiegedByTimeTriggeredAbility(final DoranBesiegedByTimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public DoranBesiegedByTimeTriggeredAbility copy() {
        return new DoranBesiegedByTimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED
                || event.getType() == GameEvent.EventType.CREATURE_BLOCKS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent;
        switch (event.getType()) {
            case ATTACKER_DECLARED:
                permanent = game.getPermanent(event.getSourceId());
                break;
            case CREATURE_BLOCKS:
                permanent = game.getPermanent(event.getTargetId());
                break;
            default:
                return false;
        }
        if (permanent == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTarget(permanent, game));
        return true;
    }
}

class DoranBesiegedByTimeEffect extends OneShotEffect {

    DoranBesiegedByTimeEffect() {
        super(Outcome.Benefit);
        staticText = "it gets +X/+X until end of turn, where X is the difference between its power and toughness";
    }

    private DoranBesiegedByTimeEffect(final DoranBesiegedByTimeEffect effect) {
        super(effect);
    }

    @Override
    public DoranBesiegedByTimeEffect copy() {
        return new DoranBesiegedByTimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int diff = Math.abs(permanent.getToughness().getValue() - permanent.getPower().getValue());
        if (diff < 1) {
            return false;
        }
        game.addEffect(new BoostTargetEffect(diff, diff).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
