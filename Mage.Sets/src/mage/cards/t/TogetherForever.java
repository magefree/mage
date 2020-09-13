
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SupportAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801, jeffwadsworth
 */
public final class TogetherForever extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public TogetherForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{W}");

        // When Together Forever enters the battlefield, support 2. (Put a +1/+1 counter on each of up to two target creatures.)
        this.addAbility(new SupportAbility(this, 2, false));

        // {1}: Choose target creature with a counter on it. When that creature dies this turn, return that card to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new TogetherForeverEffect(), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public TogetherForever(final TogetherForever card) {
        super(card);
    }

    @Override
    public TogetherForever copy() {
        return new TogetherForever(this);
    }
}

class TogetherForeverEffect extends OneShotEffect {

    public TogetherForeverEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose target creature with a counter on it. When that creature dies this turn, return that card to its owner's hand.";
    }

    public TogetherForeverEffect(final TogetherForeverEffect effect) {
        super(effect);
    }

    @Override
    public TogetherForeverEffect copy() {
        return new TogetherForeverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new TogetherForeverDelayedTriggeredAbility(targetPointer.getFirst(game, source));
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class TogetherForeverDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID target;

    public TogetherForeverDelayedTriggeredAbility(UUID target) {
        super(new TogetherForeverDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public TogetherForeverDelayedTriggeredAbility(TogetherForeverDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.isDiesEvent()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TogetherForeverDelayedTriggeredAbility copy() {
        return new TogetherForeverDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, return that card to its owner's hand";
    }
}

class TogetherForeverDelayedEffect extends OneShotEffect {

    private final UUID target;

    public TogetherForeverDelayedEffect(UUID target) {
        super(Outcome.PutCreatureInPlay);
        this.target = target;
        this.staticText = "return that card to its owner's hand";
    }

    public TogetherForeverDelayedEffect(final TogetherForeverDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public TogetherForeverDelayedEffect copy() {
        return new TogetherForeverDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (controller != null
                && permanent != null) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player != null) {
                Card card = game.getCard(target);
                if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                    return player.moveCards(card, Zone.HAND, source, game);
                }
                return true;
            }
        }
        return false;
    }
}
