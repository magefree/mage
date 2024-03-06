package mage.cards.t;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFourteenthDoctor extends CardImpl {

    public TheFourteenthDoctor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TIME_LORD);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When you cast this spell, reveal the top fourteen cards of your library. Put all Doctor cards revealed this way into your graveyard and the rest on the bottom of your library in a random order.
        this.addAbility(new CastSourceTriggeredAbility(new TheFourteenthDoctorRevealEffect()));

        // You may have The Fourteenth Doctor enter the battlefield as a copy of a Doctor card in your graveyard that was put there from your library this turn. If you do, it gains haste until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheFourteenthDoctorCopyEffect(), true));
    }

    private TheFourteenthDoctor(final TheFourteenthDoctor card) {
        super(card);
    }

    @Override
    public TheFourteenthDoctor copy() {
        return new TheFourteenthDoctor(this);
    }
}

class TheFourteenthDoctorRevealEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SubType.DOCTOR.getPredicate());
    }

    TheFourteenthDoctorRevealEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top fourteen cards of your library. Put all Doctor cards revealed this way " +
                "into your graveyard and the rest on the bottom of your library in a random order";
    }

    private TheFourteenthDoctorRevealEffect(final TheFourteenthDoctorRevealEffect effect) {
        super(effect);
    }

    @Override
    public TheFourteenthDoctorRevealEffect copy() {
        return new TheFourteenthDoctorRevealEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 14));
        player.revealCards(source, cards, game);
        Cards toGrave = new CardsImpl(cards.getCards(filter, game));
        player.moveCards(toGrave, Zone.GRAVEYARD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class TheFourteenthDoctorCopyEffect extends OneShotEffect {

    private enum TheFourteenthDoctorPredicate implements Predicate<Card> {
        instance;

        @Override
        public boolean apply(Card input, Game game) {
            return TheFourteenthDoctorWatcher.checkCard(input, game);
        }
    }

    private static final FilterCard filter
            = new FilterCard("Doctor card in your graveyard that was put there from your library this turn");

    static {
        filter.add(SubType.DOCTOR.getPredicate());
        filter.add(TheFourteenthDoctorPredicate.instance);
    }

    TheFourteenthDoctorCopyEffect() {
        super(Outcome.Benefit);
        staticText = "as a copy of a Doctor card in your graveyard that was put there " +
                "from your library this turn. If you do, it gains haste until end of turn";
    }

    private TheFourteenthDoctorCopyEffect(final TheFourteenthDoctorCopyEffect effect) {
        super(effect);
    }

    @Override
    public TheFourteenthDoctorCopyEffect copy() {
        return new TheFourteenthDoctorCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetCardInYourGraveyard(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Card copyFromCard = game.getCard(target.getFirstTarget());
        if (copyFromCard == null) {
            return false;
        }
        CopyEffect copyEffect = new CopyEffect(Duration.Custom, copyFromCard, source.getSourceId());
        game.addEffect(copyEffect, source);
        game.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
        return true;
    }
}

class TheFourteenthDoctorWatcher extends Watcher {

    private final Set<MageObjectReference> set = new HashSet<>();

    TheFourteenthDoctorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE) {
            return;
        }
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getToZone() == Zone.GRAVEYARD && zEvent.getFromZone() == Zone.LIBRARY) {
            set.add(new MageObjectReference(zEvent.getTargetId(), game));
        }
    }

    @Override
    public void reset() {
        super.reset();
        set.clear();
    }

    static boolean checkCard(Card card, Game game) {
        return game
                .getState()
                .getWatcher(TheFourteenthDoctorWatcher.class)
                .set
                .stream()
                .anyMatch(mor -> mor.refersTo(card, game));
    }
}
