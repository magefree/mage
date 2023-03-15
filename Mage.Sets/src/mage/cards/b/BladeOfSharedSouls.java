package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.AttachedToCreatureSourceTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ForMirrodinAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.UnattachedEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.EmptyCopyApplier;

import java.util.UUID;

public class BladeOfSharedSouls extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }
    public BladeOfSharedSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");
        this.addSubType(SubType.EQUIPMENT);

        //For Mirrodin!
        this.addAbility(new ForMirrodinAbility());

        //Whenever Blade of Shared Souls becomes attached to a creature, for as long as Blade of Shared Souls remains
        //attached to it, you may have that creature become a copy of another target creature you control.
        AttachedToCreatureSourceTriggeredAbility attachedToCreatureSourceTriggeredAbility =
                new AttachedToCreatureSourceTriggeredAbility(new BladeOfSharedSoulsEffect(), true);
        attachedToCreatureSourceTriggeredAbility.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(attachedToCreatureSourceTriggeredAbility.setTriggerPhrase("Whenever Blade of Shared Souls " +
                "becomes attached to a creature, for as long as {this} remains attached to it, "));

        //Equip {2}
        this.addAbility(new EquipAbility(2, false));
    }

    private BladeOfSharedSouls(final BladeOfSharedSouls card) {
        super(card);
    }

    @Override
    public BladeOfSharedSouls copy() {
        return new BladeOfSharedSouls(this);
    }
}

class BladeOfSharedSoulsEffect extends OneShotEffect {
    BladeOfSharedSoulsEffect() {
        super(Outcome.Benefit);
        staticText = "that creature become a copy of another target creature you control.";
    }

    private BladeOfSharedSoulsEffect(final BladeOfSharedSoulsEffect effect) {
        super(effect);
    }

    @Override
    public BladeOfSharedSoulsEffect copy() {
        return new BladeOfSharedSoulsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (sourcePermanent == null || creature == null) {
            return false;
        }
        game.copyPermanent(Duration.Custom, creature, sourcePermanent.getAttachedTo(), source, new EmptyCopyApplier());
        game.addDelayedTriggeredAbility(new BladeOfSharedSoulsDelayedTriggeredAbility(new BladeOfSharedSoulsUncopyEffect()), source);
        return true;
    }
}

class BladeOfSharedSoulsUncopyEffect extends OneShotEffect {
    BladeOfSharedSoulsUncopyEffect() {
        super(Outcome.Benefit);
        staticText = "Whenever {this} becomes attached to a creature, for as long as {this} remains attached to it, " +
                "you may have that creature become a copy of another target creature you control.";
    }

    private BladeOfSharedSoulsUncopyEffect(final BladeOfSharedSoulsUncopyEffect effect) {
        super(effect);
    }

    @Override
    public BladeOfSharedSoulsUncopyEffect copy() {
        return new BladeOfSharedSoulsUncopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (creature == null) {
            return false;
        }
        for (Effect effect : game.getState().getContinuousEffects().getLayeredEffects(game)) {
            if (effect instanceof CopyEffect) {
                CopyEffect copyEffect = (CopyEffect) effect;
                // there is another copy effect that copies to the same permanent
                if (copyEffect.getSourceId().equals(creature.getId())) {
                    copyEffect.discard();
                }
            }
        }
        return true;
    }
}

class BladeOfSharedSoulsDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public BladeOfSharedSoulsDelayedTriggeredAbility(Effect effect) {
        super(effect);
    }

    private BladeOfSharedSoulsDelayedTriggeredAbility(final BladeOfSharedSoulsDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNATTACHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UnattachedEvent uEvent = (UnattachedEvent) event;
        if (this.getSourceId() == uEvent.getSourceId()) {
            this.getEffects().setTargetPointer(new FixedTarget(uEvent.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public BladeOfSharedSoulsDelayedTriggeredAbility copy() {
        return new BladeOfSharedSoulsDelayedTriggeredAbility(this);
    }
}
