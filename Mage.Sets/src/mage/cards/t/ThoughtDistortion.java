package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtDistortion extends CardImpl {

    public ThoughtDistortion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");

        // This spell can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Target opponent reveals their hand. Exile all noncreature, nonland cards from that player's hand and graveyard.
        this.getSpellAbility().addEffect(new ThoughtDistortionEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private ThoughtDistortion(final ThoughtDistortion card) {
        super(card);
    }

    @Override
    public ThoughtDistortion copy() {
        return new ThoughtDistortion(this);
    }
}

class ThoughtDistortionEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterNonlandCard();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    ThoughtDistortionEffect() {
        super(Outcome.Benefit);
        staticText = "Target opponent reveals their hand. Exile all noncreature, " +
                "nonland cards from that player's hand and graveyard.";
    }

    private ThoughtDistortionEffect(final ThoughtDistortionEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtDistortionEffect copy() {
        return new ThoughtDistortionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        player.revealCards(source, player.getHand(), game);
        Cards cards = new CardsImpl(player.getHand().getCards(filter, game));
        cards.addAllCards(player.getGraveyard().getCards(filter, game));
        return player.moveCards(cards, Zone.EXILED, source, game);
    }
}