
package mage.cards.a;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Arcbond extends CardImpl {

    public Arcbond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Choose target creature. Whenever that creature is dealt damage this turn, it deals that much damage to each other creature and each player.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new ArcbondDelayedTriggeredAbility(), true, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Arcbond(final Arcbond card) {
        super(card);
    }

    @Override
    public Arcbond copy() {
        return new Arcbond(this);
    }
}

class ArcbondDelayedTriggeredAbility extends DelayedTriggeredAbility {

    MageObjectReference targetObject;

    public ArcbondDelayedTriggeredAbility() {
        super(new ArcbondEffect(), Duration.EndOfTurn, false);
    }

    public ArcbondDelayedTriggeredAbility(ArcbondDelayedTriggeredAbility ability) {
        super(ability);
        this.targetObject = ability.targetObject;
    }

    @Override
    public void init(Game game) {
        super.init(game);
        // because target can already be gone from battlefield if triggered ability resolves, we need to hold an own object reference
        targetObject = new MageObjectReference(getTargets().getFirstTarget(), game);
        if (targetObject != null) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("sourceId", targetObject.getSourceId());
            }
            this.getTargets().clear();
        }
    }

    @Override
    public boolean isInactive(Game game) {
        if (targetObject == null) {
            return true;
        }
        return super.isInactive(game);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(targetObject.getSourceId())
                && targetObject.getPermanentOrLKIBattlefield(game) != null) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("damage", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public ArcbondDelayedTriggeredAbility copy() {
        return new ArcbondDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Choose target creature. Whenever that creature is dealt damage this turn, it deals that much damage to each other creature and each player.";
    }
}

class ArcbondEffect extends OneShotEffect {

    public ArcbondEffect() {
        super(Outcome.Benefit);
    }

    public ArcbondEffect(final ArcbondEffect effect) {
        super(effect);
    }

    @Override
    public ArcbondEffect copy() {
        return new ArcbondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = (Integer) this.getValue("damage");
        UUID sourceId = (UUID) this.getValue("sourceId");
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null && damage > 0 && sourceId != null) {
            Permanent targetObject = game.getPermanentOrLKIBattlefield(sourceId);
            if (targetObject != null) {
                game.informPlayers(sourceObject.getLogName() + ": " + targetObject.getLogName() + " deals " + damage + " damage to each other creature and each player");
            }
            FilterPermanent filter = new FilterCreaturePermanent("each other creature");
            filter.add(Predicates.not(new PermanentIdPredicate(sourceId)));
            return new DamageEverythingEffect(StaticValue.get(damage), filter, sourceId).apply(game, source);
        }
        return false;
    }
}
