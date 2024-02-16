package mage.cards.k;

import java.util.UUID;
import mage.constants.SuperType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author Xanderhall
 */
public final class KaylasMusicBox extends CardImpl {

    public KaylasMusicBox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);

        // {W}, {T}: Look at the top card of your library, then exile it face down.
        Ability ability = new SimpleActivatedAbility(new KaylasMusicBoxExileEffect(), new ManaCostsImpl<>("{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {T}: Until end of turn, you may play cards you own exiled with Kayla's Music Box.
        this.addAbility(new SimpleActivatedAbility(new KaylasMusicBoxPlayFromExileEffect(), new TapSourceCost()));
    }

    private KaylasMusicBox(final KaylasMusicBox card) {
        super(card);
    }

    @Override
    public KaylasMusicBox copy() {
        return new KaylasMusicBox(this);
    }
}


class KaylasMusicBoxExileEffect extends OneShotEffect {

    KaylasMusicBoxExileEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library, then exile it face down";
    }

    private KaylasMusicBoxExileEffect(final KaylasMusicBoxExileEffect effect) {
        super(effect);
    }

    @Override
    public KaylasMusicBoxExileEffect copy() {
        return new KaylasMusicBoxExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }

        card.setFaceDown(true, game);
        controller.lookAtCards(null, card, game);

        if (controller.moveCardsToExile(card, source, game, true, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source))) {
            card.setFaceDown(true, game);
            // No other player may look at the face-down cards you own exiled with Kayla’s Music Box, even if another player takes control of it. 
            // (2022-10-14)
            ContinuousEffect effect = new KaylasMusicBoxLookEffect(controller.getId());
            effect.setTargetPointer(new FixedTarget(card.getId(), game));
            game.addEffect(effect, source);
        }
        return true;
    }

}

class KaylasMusicBoxLookEffect extends AsThoughEffectImpl {

    private final UUID authorizedPlayerId;

    public KaylasMusicBoxLookEffect(UUID authorizedPlayerId) {
        super(AsThoughEffectType.LOOK_AT_FACE_DOWN, Duration.EndOfGame, Outcome.Benefit);
        this.authorizedPlayerId = authorizedPlayerId;
        staticText = "You may look at the cards exiled with {this}";
    }

    private KaylasMusicBoxLookEffect(final KaylasMusicBoxLookEffect effect) {
        super(effect);
        this.authorizedPlayerId = effect.authorizedPlayerId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public KaylasMusicBoxLookEffect copy() {
        return new KaylasMusicBoxLookEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID cardId = getTargetPointer().getFirst(game, source);
        if (cardId == null) {
            this.discard(); // card is no longer in the origin zone, effect can be discarded
        }
        return affectedControllerId.equals(authorizedPlayerId)
                && objectId.equals(cardId);
    }

}

class KaylasMusicBoxPlayFromExileEffect extends AsThoughEffectImpl {

    KaylasMusicBoxPlayFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, you may play cards you own exiled with {this}.";
    }

    private KaylasMusicBoxPlayFromExileEffect(final KaylasMusicBoxPlayFromExileEffect effect) {
        super(effect);
    }

    @Override
    public KaylasMusicBoxPlayFromExileEffect copy() {
        return new KaylasMusicBoxPlayFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        if (exileZone == null || !exileZone.contains(sourceId)) {
            return false;
        }
        CardUtil.makeCardPlayable(game, source, exileZone.get(sourceId, game), Duration.EndOfTurn, false);
        return true;
    }
}