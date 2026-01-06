package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.BlightCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuntieOolCursewretch extends CardImpl {

    public AuntieOolCursewretch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward--Blight 2.
        this.addAbility(new WardAbility(new BlightCost(2)));

        // Whenever one or more -1/-1 counters are put on a creature, draw a card if you control that creature. If you don't control it, its controller loses 1 life.
        this.addAbility(new AuntieOolCursewretchTriggeredAbility());
    }

    private AuntieOolCursewretch(final AuntieOolCursewretch card) {
        super(card);
    }

    @Override
    public AuntieOolCursewretch copy() {
        return new AuntieOolCursewretch(this);
    }
}

class AuntieOolCursewretchTriggeredAbility extends TriggeredAbilityImpl {

    AuntieOolCursewretchTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AuntieOolCursewretchEffect());
        setTriggerPhrase("Whenever one or more -1/-1 counters are put on a creature, ");
    }

    private AuntieOolCursewretchTriggeredAbility(final AuntieOolCursewretchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AuntieOolCursewretchTriggeredAbility copy() {
        return new AuntieOolCursewretchTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent == null
                || !permanent.isCreature(game)
                || !event.getData().equals(CounterType.M1M1.getName())
                || event.getAmount() < 1) {
            return false;
        }
        this.getEffects().setValue("creature", permanent);
        return true;
    }
}

class AuntieOolCursewretchEffect extends OneShotEffect {

    AuntieOolCursewretchEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card if you control that creature. If you don't control it, its controller loses 1 life";
    }

    private AuntieOolCursewretchEffect(final AuntieOolCursewretchEffect effect) {
        super(effect);
    }

    @Override
    public AuntieOolCursewretchEffect copy() {
        return new AuntieOolCursewretchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable((Permanent) getValue("creature"))
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .map(player -> source.isControlledBy(player.getId())
                        ? player.drawCards(1, source, game)
                        : player.loseLife(1, game, source, false))
                .filter(x -> x > 0)
                .isPresent();
    }
}
