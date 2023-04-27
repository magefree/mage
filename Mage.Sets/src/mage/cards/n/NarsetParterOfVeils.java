package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NarsetParterOfVeils extends CardImpl {

    private static final FilterCard filter = new FilterCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public NarsetParterOfVeils(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NARSET);
        this.setStartingLoyalty(5);

        // Each opponent can't draw more than one card each turn.
        this.addAbility(new SimpleStaticAbility(new NarsetParterOfVeilsEffect()), new CardsAmountDrawnThisTurnWatcher());

        // -2: Look at the top four cards of your library.
        // You may reveal a noncreature, nonland card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM), -2));
    }

    private NarsetParterOfVeils(final NarsetParterOfVeils card) {
        super(card);
    }

    @Override
    public NarsetParterOfVeils copy() {
        return new NarsetParterOfVeils(this);
    }
}

class NarsetParterOfVeilsEffect extends ContinuousRuleModifyingEffectImpl {

    NarsetParterOfVeilsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "Each opponent can't draw more than one card each turn";
    }

    private NarsetParterOfVeilsEffect(final NarsetParterOfVeilsEffect effect) {
        super(effect);
    }

    @Override
    public NarsetParterOfVeilsEffect copy() {
        return new NarsetParterOfVeilsEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        CardsAmountDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        Player controller = game.getPlayer(source.getControllerId());
        return watcher != null && controller != null && watcher.getAmountCardsDrawn(event.getPlayerId()) >= 1
                && game.isOpponent(controller, event.getPlayerId());
    }
}
