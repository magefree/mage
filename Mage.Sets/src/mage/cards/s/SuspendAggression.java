package mage.cards.s;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class SuspendAggression extends CardImpl {

    public SuspendAggression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}{W}");

        // Exile target nonland permanent and the top card of your library. For each of those cards, its owner may play it until the end of their next turn.
        this.getSpellAbility().addEffect(new SuspendAggressionEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private SuspendAggression(final SuspendAggression card) {
        super(card);
    }

    @Override
    public SuspendAggression copy() {
        return new SuspendAggression(this);
    }
}

class SuspendAggressionEffect extends OneShotEffect {

    SuspendAggressionEffect() {
        super(Outcome.Exile);
        staticText = "exile target nonland permanent and the top card of your library. " +
                "For each of those cards, its owner may play it until the end of their next turn";
    }

    private SuspendAggressionEffect(final SuspendAggressionEffect effect) {
        super(effect);
    }

    @Override
    public SuspendAggressionEffect copy() {
        return new SuspendAggressionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToExile = new HashSet<>();

        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null) {
            cardsToExile.add(permanent);
        }

        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard != null) {
            cardsToExile.add(topCard);
        }

        if (cardsToExile.isEmpty()) {
            return false;
        }

        UUID exileId = CardUtil.getExileZoneId(game, source);
        String exileName = CardUtil.getSourceName(game, source);
        controller.moveCardsToExile(cardsToExile, source, game, true, exileId, exileName);

        for (Card card : cardsToExile) {
            Card mainCard = card.getMainCard();
            if (game.getState().getZone(mainCard.getId()) != Zone.EXILED) {
                continue;
            }
            UUID ownerId = mainCard.getOwnerId();
            ContinuousEffect effect = new SuspendAggressionPlayEffect(ownerId);
            effect.setTargetPointer(new FixedTarget(mainCard, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class SuspendAggressionPlayEffect extends PlayFromNotOwnHandZoneTargetEffect {

    private final UUID ownerId;

    SuspendAggressionPlayEffect(UUID ownerId) {
        super(Zone.EXILED, TargetController.OWNER, Duration.UntilEndOfYourNextTurn, false, false);
        this.ownerId = ownerId;
    }

    private SuspendAggressionPlayEffect(final SuspendAggressionPlayEffect effect) {
        super(effect);
        this.ownerId = effect.ownerId;
    }

    @Override
    public SuspendAggressionPlayEffect copy() {
        return new SuspendAggressionPlayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        setStartingControllerAndTurnNum(game, ownerId, game.getActivePlayerId());
    }
}
