
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.IntimidateAbility;
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
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public final class BlindZealot extends CardImpl {

    public BlindZealot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(IntimidateAbility.getInstance());
        // Whenever Blind Zealot deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls.
        Ability ability = new BlindZealotTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public BlindZealot(final BlindZealot card) {
        super(card);
    }

    @Override
    public BlindZealot copy() {
        return new BlindZealot(this);
    }
}

class BlindZealotTriggeredAbility extends TriggeredAbilityImpl {

    public BlindZealotTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BlindZealotEffect(), true);
    }

    public BlindZealotTriggeredAbility(final BlindZealotTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BlindZealotTriggeredAbility copy() {
        return new BlindZealotTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null && event.getSourceId().equals(this.sourceId)) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature " + opponent.getLogName() + " controls");
            filter.add(new ControllerIdPredicate(opponent.getId()));
            this.getTargets().clear();
            this.addTarget(new TargetCreaturePermanent(filter));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a player, you may sacrifice it. If you do, destroy target creature that player controls";
    }
}

class BlindZealotEffect extends OneShotEffect {

    public BlindZealotEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "sacrifice {this}";
    }

    public BlindZealotEffect(final BlindZealotEffect effect) {
        super(effect);
    }

    @Override
    public BlindZealotEffect copy() {
        return new BlindZealotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetPermanent = game.getPermanent(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject == null) {
            if (source.getSourceObject(game) instanceof Spell) {
                sourceObject = game.getPermanent(source.getSourceId());
                if (sourceObject != null && sourceObject.getZoneChangeCounter(game) > source.getSourceObjectZoneChangeCounter() + 1) {
                    return false;
                }
            }
        }
        if (sourceObject instanceof Permanent) {
            Permanent permanent = (Permanent) sourceObject;
            if (source.getControllerId().equals(permanent.getControllerId())) {
                Effect effect = new DestroyTargetEffect();
                effect.setTargetPointer(new FixedTarget(targetPermanent.getId()));
                effect.apply(game, source);
                return permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
