package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreenaTheDemagogue extends CardImpl {

    public BreenaTheDemagogue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever a player attacks one of your opponents, if that opponent has more life than another of your opponents, that attacking player draws a card and you put two +1/+1 counters on a creature you control.
        this.addAbility(new BreenaTheDemagogueTriggeredAbility());
    }

    private BreenaTheDemagogue(final BreenaTheDemagogue card) {
        super(card);
    }

    @Override
    public BreenaTheDemagogue copy() {
        return new BreenaTheDemagogue(this);
    }
}

class BreenaTheDemagogueTriggeredAbility extends TriggeredAbilityImpl {

    BreenaTheDemagogueTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardTargetEffect(1), false);
        this.addEffect(new BreenaTheDemagogueEffect());
    }

    private BreenaTheDemagogueTriggeredAbility(final BreenaTheDemagogueTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BreenaTheDemagogueTriggeredAbility copy() {
        return new BreenaTheDemagogueTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        getEffects().setValue("attackedPlayer", event.getTargetId());
        return true;
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Player player = game.getPlayer((UUID) getEffects().get(0).getValue("attackedPlayer"));
        return player != null
                && player.getLife() > game.getOpponents(getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .mapToInt(Player::getLife)
                .min()
                .orElse(0);
    }

    @Override
    public String getRule() {
        return "Whenever a player attacks one of your opponents, " +
                "if that opponent has more life than another of your opponents, " +
                "that attacking player draws a card and you put two +1/+1 counters on a creature you control.";
    }
}

class BreenaTheDemagogueEffect extends OneShotEffect {

    BreenaTheDemagogueEffect() {
        super(Outcome.Benefit);
    }

    private BreenaTheDemagogueEffect(final BreenaTheDemagogueEffect effect) {
        super(effect);
    }

    @Override
    public BreenaTheDemagogueEffect copy() {
        return new BreenaTheDemagogueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        ) < 1) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && permanent.addCounters(
                CounterType.P1P1.createInstance(2),
                source.getControllerId(), source, game
        );
    }
}
