package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mindleecher extends CardImpl {

    protected static final String VALUE_PREFIX = "ExileZones";

    public Mindleecher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Mutate {4}{B}
        this.addAbility(new MutateAbility(this, "{4}{B}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature mutates, exile the top card of each opponent's library face down. You may look at and play those cards for as long as they remain exiled.
        this.addAbility(new MutatesSourceTriggeredAbility(new MindleecherEffect()));
    }

    private Mindleecher(final Mindleecher card) {
        super(card);
    }

    @Override
    public Mindleecher copy() {
        return new Mindleecher(this);
    }
}

class MindleecherEffect extends OneShotEffect {

    MindleecherEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of each opponent's library face down. " +
                "You may look at and play those cards for as long as they remain exiled.";
    }

    private MindleecherEffect(final MindleecherEffect effect) {
        super(effect);
    }

    @Override
    public MindleecherEffect copy() {
        return new MindleecherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getOpponents(controller.getId())
                .stream()
                .map(game::getPlayer)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .forEach(cards::add);
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.EXILED);
        if (cards.isEmpty()) {
            return false;
        }
        cards.getCards(game).stream().forEach(card -> card.setFaceDown(true, game));
        for (Card card : cards.getCards(game)) {
            game.addEffect(new MindleecherCastFromExileEffect(controller.getId())
                    .setTargetPointer(new FixedTarget(card, game)), source);
            game.addEffect(new MindleecherLookEffect(controller.getId())
                    .setTargetPointer(new FixedTarget(card, game)), source);
        }
        return true;
    }
}

class MindleecherCastFromExileEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    MindleecherCastFromExileEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "For as long as that card remains exiled, you may play it";
    }

    private MindleecherCastFromExileEffect(final MindleecherCastFromExileEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MindleecherCastFromExileEffect copy() {
        return new MindleecherCastFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
            return false;
        }
        return objectId.equals(cardId)
                && affectedControllerId.equals(authorizedPlayerId)
                && game.getCard(objectId) != null;
    }
}

class MindleecherLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    MindleecherLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "For as long as that card remains exiled, you may look at it";
    }

    private MindleecherLookEffect(final MindleecherLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MindleecherLookEffect copy() {
        return new MindleecherLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
            return false;
        }
        return affectedControllerId.equals(authorizedPlayerId)
                && objectId.equals(cardId);
    }
}
