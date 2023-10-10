
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author North
 */
public final class ShowAndTell extends CardImpl {

    public ShowAndTell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        // Each player may put an artifact, creature, enchantment, or land card from their hand onto the battlefield.
        this.getSpellAbility().addEffect(new ShowAndTellEffect());
    }

    private ShowAndTell(final ShowAndTell card) {
        super(card);
    }

    @Override
    public ShowAndTell copy() {
        return new ShowAndTell(this);
    }
}

class ShowAndTellEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact, creature, enchantment, or land card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public ShowAndTellEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Each player may put an artifact, creature, enchantment, or land card from their hand onto the battlefield";
    }

    private ShowAndTellEffect(final ShowAndTellEffect effect) {
        super(effect);
    }

    @Override
    public ShowAndTellEffect copy() {
        return new ShowAndTellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToPutIntoPlay = new LinkedHashSet<>();
        TargetCardInHand target = new TargetCardInHand(filter);

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                if (player.chooseUse(outcome, "Put an artifact, creature, enchantment, or land card from hand onto the battlefield?", source, game)) {
                    target.clearChosen();
                    if (player.chooseTarget(outcome, target, source, game)) {
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            cardsToPutIntoPlay.add(card);
                        }
                    }
                }
            }
        }
        return controller.moveCards(cardsToPutIntoPlay, Zone.BATTLEFIELD, source, game, false, false, true, null);
    }
}
