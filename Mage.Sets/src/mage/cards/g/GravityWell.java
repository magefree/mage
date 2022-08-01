
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class GravityWell extends CardImpl {

    public GravityWell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");

        // Whenever a creature with flying attacks, it loses flying until end of turn.
        this.addAbility(new GravityWellTriggeredAbility());
    }

    private GravityWell(final GravityWell card) {
        super(card);
    }

    @Override
    public GravityWell copy() {
        return new GravityWell(this);
    }
}

class GravityWellTriggeredAbility extends TriggeredAbilityImpl {

    public GravityWellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GravityWellEffect());
    }

    public GravityWellTriggeredAbility(final GravityWellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent attacker = game.getPermanent(event.getSourceId());
        if (attacker != null && attacker.getAbilities().contains(FlyingAbility.getInstance())) {
            for (Effect effect : getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getSourceId(), game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature with flying attacks, " ;
    }

    @Override
    public GravityWellTriggeredAbility copy() {
        return new GravityWellTriggeredAbility(this);
    }
}

class GravityWellEffect extends ContinuousEffectImpl {

    public GravityWellEffect() {
        super(Duration.EndOfTurn, Outcome.LoseAbility);
        staticText = "it loses flying until end of turn";
    }

    public GravityWellEffect(final GravityWellEffect effect) {
        super(effect);
    }

    @Override
    public GravityWellEffect copy() {
        return new GravityWellEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent != null) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    if (sublayer == SubLayer.NA) {
                        permanent.removeAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                    }
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6;
    }

}
