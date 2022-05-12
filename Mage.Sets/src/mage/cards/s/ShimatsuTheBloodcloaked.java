
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class ShimatsuTheBloodcloaked extends CardImpl {

    public ShimatsuTheBloodcloaked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Shimatsu the Bloodcloaked enters the battlefield, sacrifice any number of permanents. Shimatsu enters the battlefield with that many +1/+1 counters on it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ShimatsuTheBloodcloakedEffect()));
    }

    private ShimatsuTheBloodcloaked(final ShimatsuTheBloodcloaked card) {
        super(card);
    }

    @Override
    public ShimatsuTheBloodcloaked copy() {
        return new ShimatsuTheBloodcloaked(this);
    }
}

class ShimatsuTheBloodcloakedEffect extends ReplacementEffectImpl {

    public ShimatsuTheBloodcloakedEffect() {
        super(Duration.EndOfGame, Outcome.BoostCreature);
        this.staticText = "As {this} enters the battlefield, sacrifice any number of permanents. {this} enters the battlefield with that many +1/+1 counters on it";
    }

    public ShimatsuTheBloodcloakedEffect(final ShimatsuTheBloodcloakedEffect effect) {
        super(effect);
    }

    @Override
    public ShimatsuTheBloodcloakedEffect copy() {
        return new ShimatsuTheBloodcloakedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            Target target = new TargetControlledPermanent(0, Integer.MAX_VALUE, new FilterControlledPermanent(), true);
            if (!target.canChoose(source.getControllerId(), source, game)) {
                return false;
            }
            controller.chooseTarget(Outcome.Detriment, target, source, game);
            if (!target.getTargets().isEmpty()) {
                int sacrificedCreatures = target.getTargets().size();
                game.informPlayers(controller.getLogName() + " sacrifices " + sacrificedCreatures + " creatures for " + creature.getLogName());
                for (UUID targetId : target.getTargets()) {
                    Permanent targetCreature = game.getPermanent(targetId);
                    if (targetCreature == null || !targetCreature.sacrifice(source, game)) {
                        return false;
                    }
                }
                creature.addCounters(CounterType.P1P1.createInstance(sacrificedCreatures), source.getControllerId(), source, game);
            }
        }
        return false;
    }

}
