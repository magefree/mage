package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.MayLookAtTargetCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author DominionSpy
 */
public final class OutrageousRobbery extends CardImpl {

    public OutrageousRobbery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");

        // Target opponent exiles the top X cards of their library face down.
        // You may look at and play those cards for as long as they remain exiled.
        // If you cast a spell this way, you may spend mana as though it were mana of any type to cast it.
        this.getSpellAbility().addEffect(new OutrageousRobberyEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private OutrageousRobbery(final OutrageousRobbery card) {
        super(card);
    }

    @Override
    public OutrageousRobbery copy() {
        return new OutrageousRobbery(this);
    }
}

class OutrageousRobberyEffect extends OneShotEffect {

    OutrageousRobberyEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent exiles the top X cards of their library face down. " +
                "You may look at and play those cards for as long as they remain exiled. " +
                "If you cast a spell this way, you may spend mana as though it were mana of any type to cast it.";
    }

    private OutrageousRobberyEffect(final OutrageousRobberyEffect effect) {
        super(effect);
    }

    @Override
    public OutrageousRobberyEffect copy() {
        return new OutrageousRobberyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || opponent == null || sourceObject == null) {
            return false;
        }

        int xValue = source.getManaCostsToPay().getX();
        if (xValue == 0) {
            return false;
        }

        Set<Card> cards = opponent.getLibrary().getTopCards(game, xValue);
        cards.forEach(card -> card.setFaceDown(true, game));
        UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
        if (cards.size() > 0 && opponent.moveCardsToExile(cards, source, game, false, exileZoneId,
                sourceObject.getIdName() + " (" + controller.getName() + ")")) {
            for (Card card : cards) {
                card.setFaceDown(true, game);

                ContinuousEffect effect = new MayLookAtTargetCardEffect(controller.getId());
                effect.setTargetPointer(new FixedTarget(card.getId(), game));
                game.addEffect(effect, source);

                CardUtil.makeCardPlayable(
                        game, source, card, false, Duration.Custom, true,
                        source.getControllerId(), null);
            }
        }
        return true;
    }
}
