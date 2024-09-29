package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.abilityword.SurvivalAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RipSpawnHunter extends CardImpl {

    public RipSpawnHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SURVIVOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Survival -- At the beginning of your second main phase, if Rip, Spawn Hunter is tapped, reveal the top X cards of your library, where X is its power. Put any number of creature and/or Vehicle cards with different powers from among them into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SurvivalAbility(new RipSpawnHunterEffect()));
    }

    private RipSpawnHunter(final RipSpawnHunter card) {
        super(card);
    }

    @Override
    public RipSpawnHunter copy() {
        return new RipSpawnHunter(this);
    }
}

class RipSpawnHunterEffect extends OneShotEffect {

    RipSpawnHunterEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top X cards of your library, where X is its power. " +
                "Put any number of creature and/or Vehicle cards with different powers from among them into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private RipSpawnHunterEffect(final RipSpawnHunterEffect effect) {
        super(effect);
    }

    @Override
    public RipSpawnHunterEffect copy() {
        return new RipSpawnHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int power = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0);
        if (player == null || power < 1) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, power));
        if (cards.isEmpty()) {
            return false;
        }
        player.revealCards(source, cards, game);
        TargetCard target = new RipSpawnHunterTarget();
        player.choose(Outcome.ReturnToHand, cards, target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class RipSpawnHunterTarget extends TargetCardInLibrary {

    private static final FilterCard filter = new FilterCard("creature and/or Vehicle cards with different powers");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public RipSpawnHunterTarget() {
        super(0, Integer.MAX_VALUE, filter);
    }

    private RipSpawnHunterTarget(final RipSpawnHunterTarget target) {
        super(target);
    }

    @Override
    public RipSpawnHunterTarget copy() {
        return new RipSpawnHunterTarget(this);
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
        int power = card.getPower().getValue();
        return this
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .noneMatch(x -> x == power);
    }
}
