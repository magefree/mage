package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoTheFire extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature, planeswalker, and battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public IntoTheFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Choose one --
        // * Into the Fire deals 2 damage to each creature, planeswalker, and battle.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));

        // * Put any number of cards from your hand on the bottom of your library, then draw that many cards plus one.
        this.getSpellAbility().addMode(new Mode(new IntoTheFireEffect()));
    }

    private IntoTheFire(final IntoTheFire card) {
        super(card);
    }

    @Override
    public IntoTheFire copy() {
        return new IntoTheFire(this);
    }
}

class IntoTheFireEffect extends OneShotEffect {

    IntoTheFireEffect() {
        super(Outcome.Benefit);
        staticText = "put any number of cards from your hand on the bottom of your library, then draw that many cards plus one";
    }

    private IntoTheFireEffect(final IntoTheFireEffect effect) {
        super(effect);
    }

    @Override
    public IntoTheFireEffect copy() {
        return new IntoTheFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard targetCard = new TargetCardInHand(
                0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_CARDS
        );
        player.choose(outcome, player.getHand(), targetCard, source, game);
        Cards cards = new CardsImpl(targetCard.getTargets());
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        player.drawCards(cards.size() + 1, source, game);
        return true;
    }
}
