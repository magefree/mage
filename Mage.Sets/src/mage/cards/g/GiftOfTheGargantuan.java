package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author North
 */
public final class GiftOfTheGargantuan extends CardImpl {

    public GiftOfTheGargantuan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Look at the top four cards of your library. You may reveal a creature card and/or a land card from among them and put the revealed cards into your hand. Put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new GiftOfTheGargantuanEffect());
    }

    private GiftOfTheGargantuan(final GiftOfTheGargantuan card) {
        super(card);
    }

    @Override
    public GiftOfTheGargantuan copy() {
        return new GiftOfTheGargantuan(this);
    }
}

class GiftOfTheGargantuanEffect extends OneShotEffect {

    GiftOfTheGargantuanEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top four cards of your library. You may reveal a creature card " +
                "and/or a land card from among them and put the revealed cards into your hand. " +
                "Put the rest on the bottom of your library in any order";
    }

    private GiftOfTheGargantuanEffect(final GiftOfTheGargantuanEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfTheGargantuanEffect copy() {
        return new GiftOfTheGargantuanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        TargetCard target = new GiftOfTheGargantuanTarget();
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl();
        toHand.addAll(target.getTargets());
        player.revealCards(source, toHand, game);
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.removeAll(toHand);
        player.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}

class GiftOfTheGargantuanTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a creature card and/or a land card");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    private static final CardTypeAssignment cardTypeAssigner
            = new CardTypeAssignment(CardType.CREATURE, CardType.LAND);

    GiftOfTheGargantuanTarget() {
        super(0, 2, filter);
    }

    private GiftOfTheGargantuanTarget(final GiftOfTheGargantuanTarget target) {
        super(target);
    }

    @Override
    public GiftOfTheGargantuanTarget copy() {
        return new GiftOfTheGargantuanTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return cardTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
