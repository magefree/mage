package mage.cards.e;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Plopman
 */
public final class Exhume extends CardImpl {

    public Exhume(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Each player puts a creature card from their graveyard onto the battlefield.
        this.getSpellAbility().addEffect(new ExhumeEffect());
    }

    private Exhume(final Exhume card) {
        super(card);
    }

    @Override
    public Exhume copy() {
        return new Exhume(this);
    }
}

class ExhumeEffect extends OneShotEffect {

    public ExhumeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player puts a creature card from their graveyard onto the battlefield";
    }

    public ExhumeEffect(final ExhumeEffect effect) {
        super(effect);
    }

    @Override
    public ExhumeEffect copy() {
        return new ExhumeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> toBattlefield = new HashSet<>();
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    FilterCreatureCard filterCreatureCard = new FilterCreatureCard("creature card from your graveyard");
                    filterCreatureCard.add(new OwnerIdPredicate(playerId));
                    TargetCardInGraveyard target = new TargetCardInGraveyard(filterCreatureCard);
                    target.setNotTarget(true);
                    if (target.canChoose(playerId, source, game)
                            && player.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            toBattlefield.add(card);
                        }
                    }
                }
            }

            // must happen simultaneously Rule 101.4
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
            return true;
        }
        return false;
    }
}
