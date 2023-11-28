package mage.cards.i;

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
 * @author Susucr
 */
public final class InThePresenceOfAges extends CardImpl {

    public InThePresenceOfAges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Reveal the top four cards of your library. You may put a creature card and/or a land card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new InThePresenceOfAgeEffect());
    }

    private InThePresenceOfAges(final InThePresenceOfAges card) {
        super(card);
    }

    @Override
    public InThePresenceOfAges copy() {
        return new InThePresenceOfAges(this);
    }
}

class InThePresenceOfAgeEffect extends OneShotEffect {

    InThePresenceOfAgeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. "
                + "You may put a creature card and/or a land card from among them into your hand. "
                + "Put the rest into your graveyard";
    }

    private InThePresenceOfAgeEffect(final InThePresenceOfAgeEffect effect) {
        super(effect);
    }

    @Override
    public InThePresenceOfAgeEffect copy() {
        return new InThePresenceOfAgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.revealCards(source, cards, game);
        TargetCard target = new InThePresenceOfAgesTarget();
        player.choose(outcome, cards, target, source, game);
        Cards toHand = new CardsImpl();
        toHand.addAll(target.getTargets());
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.removeAll(toHand);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}

class InThePresenceOfAgesTarget extends TargetCardInLibrary {

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

    InThePresenceOfAgesTarget() {
        super(0, 2, filter);
    }

    private InThePresenceOfAgesTarget(final InThePresenceOfAgesTarget target) {
        super(target);
    }

    @Override
    public InThePresenceOfAgesTarget copy() {
        return new InThePresenceOfAgesTarget(this);
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
