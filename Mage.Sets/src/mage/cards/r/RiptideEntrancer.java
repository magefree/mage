
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class RiptideEntrancer extends CardImpl {

    public RiptideEntrancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Riptide Entrancer deals combat damage to a player, you may sacrifice it. If you do, gain control of target creature that player controls.
        this.addAbility(new RiptideEntrancerTriggeredAbility());

        // Morph {U}{U}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{U}{U}")));

    }

    public RiptideEntrancer(final RiptideEntrancer card) {
        super(card);
    }

    @Override
    public RiptideEntrancer copy() {
        return new RiptideEntrancer(this);
    }
}

class RiptideEntrancerTriggeredAbility extends TriggeredAbilityImpl {

    public RiptideEntrancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RiptideEntrancerEffect(), true);
    }

    public RiptideEntrancerTriggeredAbility(final RiptideEntrancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RiptideEntrancerTriggeredAbility copy() {
        return new RiptideEntrancerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
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
        return "Whenever {this} deals combat damage to a player, you may sacrifice it. If you do, gain control of target creature that player controls";
    }
}

class RiptideEntrancerEffect extends OneShotEffect {

    public RiptideEntrancerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "sacrifice {this}";
    }

    public RiptideEntrancerEffect(final RiptideEntrancerEffect effect) {
        super(effect);
    }

    @Override
    public RiptideEntrancerEffect copy() {
        return new RiptideEntrancerEffect(this);
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
                ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom);
                effect.setTargetPointer(new FixedTarget(targetPermanent.getId()));
                game.addEffect(effect, source);
                return permanent.sacrifice(source.getSourceId(), game);
            }
            return true;
        }
        return false;
    }
}
