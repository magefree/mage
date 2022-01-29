
package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author spjspj
 */
public final class GOTOJAIL extends CardImpl {

    public GOTOJAIL(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // When GO TO JAIL enters the battlefield, exile target creature an opponent controls until GO TO JAIL leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GoToJailExileEffect());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);

        // At the beginning of the upkeep of the exiled card's owner, that player rolls two six-sided dice. If they roll doubles, sacrifice GO TO JAIL.
        this.addAbility(new GoToJailTriggeredAbility());
    }

    private GOTOJAIL(final GOTOJAIL card) {
        super(card);
    }

    @Override
    public GOTOJAIL copy() {
        return new GOTOJAIL(this);
    }
}

class GoToJailExileEffect extends OneShotEffect {

    public GoToJailExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target creature an opponent controls until {this} leaves the battlefield.";
    }

    public GoToJailExileEffect(final GoToJailExileEffect effect) {
        super(effect);
    }

    @Override
    public GoToJailExileEffect copy() {
        return new GoToJailExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);
        Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));

        // If GO TO JAIL leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        if (permanent != null && targetPermanent != null) {
            Player controller = game.getPlayer(targetPermanent.getControllerId());
            if (controller != null) {
                game.getState().setValue(permanent.getId() + ChooseOpponentEffect.VALUE_KEY, controller.getId());
                return new ExileTargetEffect(CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), permanent.getIdName()).apply(game, source);
            }
        }
        return false;
    }
}

class GoToJailTriggeredAbility extends TriggeredAbilityImpl {

    public GoToJailTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GoToJailUpkeepEffect(), false);
    }

    public GoToJailTriggeredAbility(final GoToJailTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoToJailTriggeredAbility copy() {
        return new GoToJailTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getPlayerId().equals(game.getState().getValue(this.getSourceId().toString() + ChooseOpponentEffect.VALUE_KEY));
    }

    @Override
    public String getTriggerPhrase() {
        return "At the beginning of the chosen player's upkeep, " ;
    }
}

class GoToJailUpkeepEffect extends OneShotEffect {

    public GoToJailUpkeepEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "that player rolls two six-sided dice. If they roll doubles, sacrifice {this}";
    }

    public GoToJailUpkeepEffect(final GoToJailUpkeepEffect effect) {
        super(effect);
    }

    @Override
    public GoToJailUpkeepEffect copy() {
        return new GoToJailUpkeepEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        Permanent permanent = (Permanent) source.getSourceObjectIfItStillExists(game);


        if (sourceObject instanceof Permanent && permanent != null) {
            UUID opponentId = (UUID) game.getState().getValue(sourceObject.getId().toString() + ChooseOpponentEffect.VALUE_KEY);
            Player opponent = game.getPlayer(opponentId);

            if (opponent != null) {
                List<Integer> results = opponent.rollDice(outcome, source, game, 6, 2, 0);
                int thisRoll = results.get(0);
                int thatRoll = results.get(1);
                if (thisRoll == thatRoll) {
                    return permanent.sacrifice(source, game);
                }
            }
        }
        return false;
    }
}
