package mage.cards.q;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class QueenKaylaBinKroog extends CardImpl {

    public QueenKaylaBinKroog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {4}, {T}: Discard all the cards in your hand, then draw that many cards. You may chose an artifact or creature card with mana value 1 you discarded this way, then do the same for artifact or creature cards with mana values 2 and 3. Return those cards to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new QueenKaylaBinKroogEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private QueenKaylaBinKroog(final QueenKaylaBinKroog card) {
        super(card);
    }

    @Override
    public QueenKaylaBinKroog copy() {
        return new QueenKaylaBinKroog(this);
    }
}

class QueenKaylaBinKroogEffect extends OneShotEffect {

    QueenKaylaBinKroogEffect() {
        super(Outcome.Benefit);
        staticText = "discard all the cards in your hand, then draw that many cards. " +
                "You may choose an artifact or creature card with mana value 1 you discarded this way, " +
                "then do the same for artifact or creature cards with mana values 2 and 3. " +
                "Return those cards to the battlefield";
    }

    private QueenKaylaBinKroogEffect(final QueenKaylaBinKroogEffect effect) {
        super(effect);
    }

    @Override
    public QueenKaylaBinKroogEffect copy() {
        return new QueenKaylaBinKroogEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        Cards cards = player.discard(player.getHand(), false, source, game);
        player.drawCards(cards.size(), source, game);
        TargetCard target = new QueenKaylaBinKroogTarget();
        player.choose(outcome, cards, target, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        return true;
    }
}

class QueenKaylaBinKroogTarget extends TargetCard {

    private static final FilterCard filter = new FilterCard("artifact or creature card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    QueenKaylaBinKroogTarget() {
        super(0, 3, Zone.ALL, filter);
        this.setNotTarget(true);
    }

    private QueenKaylaBinKroogTarget(final QueenKaylaBinKroogTarget target) {
        super(target);
    }

    @Override
    public QueenKaylaBinKroogTarget copy() {
        return new QueenKaylaBinKroogTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (!super.canTarget(playerId, id, ability, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null && 1 <= card.getManaValue() && card.getManaValue() <= 3 && this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .noneMatch(x -> card.getManaValue() == x);
    }


    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<Integer> manaValues = this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(MageObject::getManaValue)
                .collect(Collectors.toSet());
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null && manaValues.contains(card.getManaValue());
        });
        return possibleTargets;
    }
}
