package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class LukkaCoppercoatOutcast extends CardImpl {

    public LukkaCoppercoatOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LUKKA);
        this.setStartingLoyalty(5);

        // +1: Exile the top three cards of your library. Creature cards exiled this way gain "You may cast this card from exile as long as you control a Lukka planeswalker."
        this.addAbility(new LoyaltyAbility(new LukkaCoppercoatOutcastExileEffect(), 1));

        // −2: Exile target creature you control, then reveal cards from the top of your library until you reveal a creature card with higher converted mana cost. Put that card onto the battlefield and the rest on the bottom of your library in a random order.
        Ability ability = new LoyaltyAbility(new LukkaCoppercoatOutcastPolymorphEffect(), -2);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // −7: Each creature you control deals damage equal to its power to each opponent.
        this.addAbility(new LoyaltyAbility(new LukkaCoppercoatOutcastDamageEffect(), -7));
    }

    private LukkaCoppercoatOutcast(final LukkaCoppercoatOutcast card) {
        super(card);
    }

    @Override
    public LukkaCoppercoatOutcast copy() {
        return new LukkaCoppercoatOutcast(this);
    }
}

class LukkaCoppercoatOutcastExileEffect extends OneShotEffect {

    LukkaCoppercoatOutcastExileEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "Exile the top three cards of your library. Creature cards exiled this way gain " +
                "\"You may cast this card from exile as long as you control a Lukka planeswalker.\"";
    }

    private LukkaCoppercoatOutcastExileEffect(final LukkaCoppercoatOutcastExileEffect effect) {
        super(effect);
    }

    @Override
    public LukkaCoppercoatOutcastExileEffect copy() {
        return new LukkaCoppercoatOutcastExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cards = controller.getLibrary().getTopCards(game, 3);
        controller.moveCards(cards, Zone.EXILED, source, game);

        cards.stream().filter(card1 -> card1.isCreature(game)).forEach(card -> {
            ContinuousEffect effect = new LukkaCoppercoatOutcastCastEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        });
        return true;
    }
}

class LukkaCoppercoatOutcastCastEffect extends AsThoughEffectImpl {

    private static final FilterPermanent filter = new FilterPlaneswalkerPermanent(SubType.LUKKA);

    LukkaCoppercoatOutcastCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.staticText = "You may cast this card from exile as long as you control a Lukka planeswalker.";
    }

    private LukkaCoppercoatOutcastCastEffect(final LukkaCoppercoatOutcastCastEffect effect) {
        super(effect);
    }

    @Override
    public LukkaCoppercoatOutcastCastEffect copy() {
        return new LukkaCoppercoatOutcastCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return game.getBattlefield().countAll(filter, affectedControllerId, game) > 0
                && source.isControlledBy(affectedControllerId)
                && getTargetPointer().getTargets(game, source).contains(sourceId);
    }
}

class LukkaCoppercoatOutcastPolymorphEffect extends OneShotEffect {

    LukkaCoppercoatOutcastPolymorphEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature you control, then reveal cards from the top of your library " +
                "until you reveal a creature card with higher mana value. " +
                "Put that card onto the battlefield and the rest on the bottom of your library in a random order.";
    }

    private LukkaCoppercoatOutcastPolymorphEffect(final LukkaCoppercoatOutcastPolymorphEffect effect) {
        super(effect);
    }

    @Override
    public LukkaCoppercoatOutcastPolymorphEffect copy() {
        return new LukkaCoppercoatOutcastPolymorphEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        int cmc = permanent.getManaValue();
        player.moveCards(permanent, Zone.EXILED, source, game);
        Card toBattlefield = null;
        Cards toReveal = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isCreature(game) && card.getManaValue() > cmc) {
                toBattlefield = card;
                break;
            }
        }
        player.revealCards("", toReveal, game);
        toReveal.remove(toBattlefield);
        player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}

class LukkaCoppercoatOutcastDamageEffect extends OneShotEffect {

    LukkaCoppercoatOutcastDamageEffect() {
        super(Outcome.Benefit);
        staticText = "Each creature you control deals damage equal to its power to each opponent.";
    }

    private LukkaCoppercoatOutcastDamageEffect(final LukkaCoppercoatOutcastDamageEffect effect) {
        super(effect);
    }

    @Override
    public LukkaCoppercoatOutcastDamageEffect copy() {
        return new LukkaCoppercoatOutcastDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> controlledCreatures = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
        );
        List<Player> opponentList = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        for (Permanent permanent : controlledCreatures) {
            for (Player opponent : opponentList) {
                opponent.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
            }
        }
        return true;
    }
}
