package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NahirisWarcrafting extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("creature, planeswalker, or battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public NahirisWarcrafting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Nahiri's Warcrafting deals 5 damage to target creature, planeswalker, or battle. Look at the top X cards of your library, where X is the excess damage dealt this way. You may exile one of those cards. Put the rest on the bottom of your library in a random order. You may play the exiled card this turn.
        this.getSpellAbility().addEffect(new NahirisWarcraftingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private NahirisWarcrafting(final NahirisWarcrafting card) {
        super(card);
    }

    @Override
    public NahirisWarcrafting copy() {
        return new NahirisWarcrafting(this);
    }
}

class NahirisWarcraftingEffect extends OneShotEffect {

    NahirisWarcraftingEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to target creature, planeswalker, or battle. " +
                "Look at the top X cards of your library, where X is the excess damage dealt this way. " +
                "You may exile one of those cards. Put the rest on the bottom of your library " +
                "in a random order. You may play the exiled card this turn";
    }

    private NahirisWarcraftingEffect(final NahirisWarcraftingEffect effect) {
        super(effect);
    }

    @Override
    public NahirisWarcraftingEffect copy() {
        return new NahirisWarcraftingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        int lethal = permanent.getLethalDamage(source.getSourceId(), game);
        int excess = permanent.damage(5, source, game) - lethal;
        if (excess <= 0) {
            return true;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, excess));
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_A);
        player.choose(outcome, cards, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            player.moveCards(card, Zone.EXILED, source, game);
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, false);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
