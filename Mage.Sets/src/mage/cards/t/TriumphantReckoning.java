package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriumphantReckoning extends CardImpl {

    public TriumphantReckoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{W}{W}{W}");

        // Return all artifact, enchantment, and planeswalker cards from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new TriumphantReckoningEffect());
    }

    private TriumphantReckoning(final TriumphantReckoning card) {
        super(card);
    }

    @Override
    public TriumphantReckoning copy() {
        return new TriumphantReckoning(this);
    }
}

class TriumphantReckoningEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    TriumphantReckoningEffect() {
        super(Outcome.Benefit);
        staticText = "return all artifact, enchantment, and planeswalker cards from your graveyard to the battlefield";
    }

    private TriumphantReckoningEffect(final TriumphantReckoningEffect effect) {
        super(effect);
    }

    @Override
    public TriumphantReckoningEffect copy() {
        return new TriumphantReckoningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(
                player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game
        );
    }
}
// on your left
