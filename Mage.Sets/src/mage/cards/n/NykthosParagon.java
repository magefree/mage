package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author weirddan455
 */
public final class NykthosParagon extends CardImpl {

    public NykthosParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.
        this.addAbility(new NykthosParagonTriggeredAbility());
    }

    private NykthosParagon(final NykthosParagon card) {
        super(card);
    }

    @Override
    public NykthosParagon copy() {
        return new NykthosParagon(this);
    }
}

class NykthosParagonTriggeredAbility extends TriggeredAbilityImpl {

    public NykthosParagonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NykthosParagonEffect(), true);
    }

    private NykthosParagonTriggeredAbility(final NykthosParagonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NykthosParagonTriggeredAbility copy() {
        return new NykthosParagonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAINED_LIFE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (abilityAvailableThisTurn(game) && event.getPlayerId().equals(this.getControllerId())) {
            for (Effect effect : this.getEffects()) {
                effect.setValue("gainedLife", event.getAmount());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean resolve(Game game) {
        if (abilityAvailableThisTurn(game) && super.resolve(game)) {
            game.getState().setValue(CardUtil.getCardZoneString(
                    "lastTurnResolved" + originalId, sourceId, game
            ), game.getTurnNum());
            return true;
        }
        return false;
    }

    private boolean abilityAvailableThisTurn(Game game) {
        Integer lastTurnResolved = (Integer) game.getState().getValue(
                CardUtil.getCardZoneString("lastTurnResolved" + originalId, sourceId, game)
        );
        return lastTurnResolved == null || lastTurnResolved != game.getTurnNum();
    }

    @Override
    public String getRule() {
        return "Whenever you gain life, you may put that many +1/+1 counters on each creature you control. Do this only once each turn.";
    }
}

class NykthosParagonEffect extends OneShotEffect {

    public NykthosParagonEffect() {
        super(Outcome.BoostCreature);
    }

    private NykthosParagonEffect(final NykthosParagonEffect effect) {
        super(effect);
    }

    @Override
    public NykthosParagonEffect copy() {
        return new NykthosParagonEffect(this);
    }

    @Override
    public boolean apply (Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        Integer life = (Integer) this.getValue("gainedLife");
        if (controller != null && sourceObject != null && life != null) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
                if (permanent != null && permanent.isCreature(game)) {
                    permanent.addCounters(CounterType.P1P1.createInstance(life), source.getControllerId(), source, game);
                    if (!game.isSimulation()) {
                        game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " puts " + life
                                + " +1/+1 counters on " + permanent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
