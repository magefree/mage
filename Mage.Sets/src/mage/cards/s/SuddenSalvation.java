package mage.cards.s;

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
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuddenSalvation extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard(
            "permanent cards in graveyards that were put there from the battlefield this turn"
    );

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
    }

    public SuddenSalvation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Choose up to three target permanent cards in graveyards that were put there from the battlefield this turn. Return them to the battlefield tapped under their owners' control. You draw a card for each opponent who controls one or more of those permanents.
        this.getSpellAbility().addEffect(new SuddenSalvationEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(0, 3, filter));
        this.getSpellAbility().addWatcher(new CardsPutIntoGraveyardWatcher());
    }

    private SuddenSalvation(final SuddenSalvation card) {
        super(card);
    }

    @Override
    public SuddenSalvation copy() {
        return new SuddenSalvation(this);
    }
}

class SuddenSalvationEffect extends OneShotEffect {

    SuddenSalvationEffect() {
        super(Outcome.Benefit);
        staticText = "choose up to three target permanent cards in graveyards " +
                "that were put there from the battlefield this turn. " +
                "Return them to the battlefield tapped under their owners' control. " +
                "You draw a card for each opponent who controls one or more of those permanents";
    }

    private SuddenSalvationEffect(final SuddenSalvationEffect effect) {
        super(effect);
    }

    @Override
    public SuddenSalvationEffect copy() {
        return new SuddenSalvationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl(getTargetPointer().getTargets(game, source));
        if (player == null || cards.isEmpty()) {
            return false;
        }
        player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, true, null
        );
        int opponents = cards.stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .map(Controllable::getControllerId)
                .distinct()
                .mapToInt(uuid -> player.hasOpponent(uuid, game) ? 1 : 0)
                .sum();
        player.drawCards(opponents, source, game);
        return true;
    }
}
