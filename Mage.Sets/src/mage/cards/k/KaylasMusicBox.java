package mage.cards.k;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
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

        CardUtil.moveCardsToExileFaceDown(game, source, controller, card, true);
        return true;
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
        Card card = exileZone.get(sourceId, game);
        if (card != null && !card.getOwnerId().equals(affectedControllerId)) {
            return false;
        }
        CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        return true;
    }
}