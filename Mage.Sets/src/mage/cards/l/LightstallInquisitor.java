package mage.cards.l;

import mage.MageIdentifier;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.CardMorEnteringTappedEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.watchers.Watcher;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class LightstallInquisitor extends CardImpl {

    public LightstallInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, each opponent exiles a card from their hand and may play that card for as long as it remains exiled. Each spell cast this way costs {1} more to cast. Each land played this way enters tapped.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(new LightstallInquisitorEffect())
                        .setIdentifier(MageIdentifier.LightstallInquisitorAlternateCast),
                new LightstallInquisitorWatcher());
    }

    private LightstallInquisitor(final LightstallInquisitor card) {
        super(card);
    }

    @Override
    public LightstallInquisitor copy() {
        return new LightstallInquisitor(this);
    }
}

class LightstallInquisitorEffect extends OneShotEffect {

    public LightstallInquisitorEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles a card from their hand and may play that card for as long as it remains exiled. "
                + "Each spell cast this way costs {1} more to cast. Each land played this way enters tapped.";
    }

    private LightstallInquisitorEffect(final LightstallInquisitorEffect effect) {
        super(effect);
    }

    @Override
    public LightstallInquisitorEffect copy() {
        return new LightstallInquisitorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(playerId);
            if (opponent == null || opponent.getHand().isEmpty()) {
                continue;
            }
            TargetCard target = new TargetCardInHand();
            opponent.choose(Outcome.Exile, opponent.getHand(), target, source, game);
            Cards cards = new CardsImpl(target.getTargets());
            if (cards.isEmpty()) {
                continue;
            }
            opponent.moveCardsToExile(cards.getCards(game), source, game, true, null, "");
            cards.retainZone(Zone.EXILED, game);
            for (Card card : cards.getCards(game)) {
                game.addEffect(new LightstallInquisitorAsThoughEffect(playerId, new MageObjectReference(card, game)), source);
            }
        }
        return true;
    }

}

class LightstallInquisitorAsThoughEffect extends AsThoughEffectImpl {

    private final UUID playerId;
    private final MageObjectReference cardMOR;

    LightstallInquisitorAsThoughEffect(UUID playerId, MageObjectReference cardMOR) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.playerId = playerId;
        this.cardMOR = cardMOR;
    }

    private LightstallInquisitorAsThoughEffect(final LightstallInquisitorAsThoughEffect effect) {
        super(effect);
        this.playerId = effect.playerId;
        this.cardMOR = effect.cardMOR;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public LightstallInquisitorAsThoughEffect copy() {
        return new LightstallInquisitorAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = this.cardMOR.getCard(game);
        if (card == null) {
            // cleanup, the card moved from exile.
            discard();
            return false;
        }
        if (!this.cardMOR.refersTo(objectId, game)
                || !playerId.equals(affectedControllerId)) {
            return false;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }
        if (card.getSpellAbility() != null) {
            ManaCosts<ManaCost> newManaCosts = new ManaCostsImpl<>();
            newManaCosts.addAll(card.getManaCost());
            newManaCosts.add(new GenericManaCost(1));
            player.setCastSourceIdWithAlternateMana(
                    card.getId(), newManaCosts, card.getSpellAbility().getCosts(),
                    MageIdentifier.LightstallInquisitorAlternateCast
            );
        }
        return true;
    }
}


// Similar to CastFromGraveyardOnceWatcher, this Watcher add a EnteringTapped effects for lands played with the identifier.
class LightstallInquisitorWatcher extends Watcher {

    LightstallInquisitorWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (!GameEvent.EventType.PLAY_LAND.equals(event.getType())) {
            return;
        }
        if (!event.hasApprovingIdentifier(MageIdentifier.LightstallInquisitorAlternateCast)) {
            return;
        }
        // The land enters the battlefield tapped.
        Card landCard = game.getCard(event.getTargetId());
        if (landCard == null) {
            return;
        }
        MageObjectReference mor = new MageObjectReference(landCard, game);
        game.getState().addEffect(
                new CardMorEnteringTappedEffect(mor),
                event.getApprovingObject().getApprovingAbility() // ability that approved the cast is the source of the tapping.
        );
    }
}

