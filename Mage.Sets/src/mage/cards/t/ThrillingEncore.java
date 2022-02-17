package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author L_J
 */
public final class ThrillingEncore extends CardImpl {

    public ThrillingEncore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Put onto the battlefield under your control all creature cards in all graveyards that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new ThrillingEncoreEffect());
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private ThrillingEncore(final ThrillingEncore card) {
        super(card);
    }

    @Override
    public ThrillingEncore copy() {
        return new ThrillingEncore(this);
    }
}

class ThrillingEncoreEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    ThrillingEncoreEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Put onto the battlefield under your control all creature cards " +
                "in all graveyards that were put there from the battlefield this turn";
    }

    private ThrillingEncoreEffect(final ThrillingEncoreEffect effect) {
        super(effect);
    }

    @Override
    public ThrillingEncoreEffect copy() {
        return new ThrillingEncoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        if (controller == null || watcher == null) { return false; }

        Cards cards = new CardsImpl(watcher.getCardsPutIntoGraveyardFromBattlefield(game));
        cards.removeIf(uuid -> !game.getCard(uuid).isCreature(game));

        return controller.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
