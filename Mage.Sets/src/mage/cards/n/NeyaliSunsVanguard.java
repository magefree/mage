package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NeyaliSunsVanguard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("attacking tokens");

    static {
        filter.add(AttackingPredicate.instance);
        filter.add(TokenPredicate.TRUE);
    }

    public NeyaliSunsVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Attacking tokens you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));

        // Whenever one or more tokens you control attack a player, exile the top card of your library. During any turn you attacked with a token, you may play that card.
        this.addAbility(new NeyaliSunsVanguardTriggeredAbility());
    }

    private NeyaliSunsVanguard(final NeyaliSunsVanguard card) {
        super(card);
    }

    @Override
    public NeyaliSunsVanguard copy() {
        return new NeyaliSunsVanguard(this);
    }
}

class NeyaliSunsVanguardTriggeredAbility extends TriggeredAbilityImpl {

    NeyaliSunsVanguardTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NeyaliSunsVanguardEffect());
        this.addWatcher(new NeyaliSunsVanguardWatcher());
    }

    private NeyaliSunsVanguardTriggeredAbility(final NeyaliSunsVanguardTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NeyaliSunsVanguardTriggeredAbility copy() {
        return new NeyaliSunsVanguardTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getPlayer(event.getTargetId()) != null
                && ((DefenderAttackedEvent) event)
                .getAttackers(game)
                .stream()
                .filter(PermanentToken.class::isInstance)
                .map(Controllable::getControllerId)
                .anyMatch(this::isControlledBy);
    }

    @Override
    public String getRule() {
        return "Whenever one or more tokens you control attack a player, exile the top card of your library. " +
                "During any turn you attacked with a token, you may play that card.";
    }
}

class NeyaliSunsVanguardEffect extends OneShotEffect {

    NeyaliSunsVanguardEffect() {
        super(Outcome.Benefit);
    }

    private NeyaliSunsVanguardEffect(final NeyaliSunsVanguardEffect effect) {
        super(effect);
    }

    @Override
    public NeyaliSunsVanguardEffect copy() {
        return new NeyaliSunsVanguardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(
                game, source, card, Duration.Custom, false,
                source.getControllerId(), NeyaliSunsVanguardWatcher::checkPlayer
        );
        return true;
    }
}

class NeyaliSunsVanguardWatcher extends Watcher {

    private final Set<UUID> set = new HashSet<>();

    NeyaliSunsVanguardWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ATTACKER_DECLARED) {
            return;
        }
        Permanent permanent = game.getPermanent(event.getSourceId());
        if (permanent instanceof PermanentToken) {
            set.add(permanent.getControllerId());
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkPlayer(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(NeyaliSunsVanguardWatcher.class)
                .set
                .contains(source.getControllerId());
    }
}
