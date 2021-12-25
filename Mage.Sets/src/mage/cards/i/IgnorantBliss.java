package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class IgnorantBliss extends CardImpl {

    public IgnorantBliss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Exile all cards from your hand face down. At the beginning of the next end step, return those cards to your hand, then draw a card.
        this.getSpellAbility().addEffect(new IgnorantBlissEffect());
    }

    private IgnorantBliss(final IgnorantBliss card) {
        super(card);
    }

    @Override
    public IgnorantBliss copy() {
        return new IgnorantBliss(this);
    }
}

class IgnorantBlissEffect extends OneShotEffect {

    IgnorantBlissEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all cards from your hand face down. At the beginning of the next end step, " +
                "return those cards to your hand, then draw a card";
    }

    private IgnorantBlissEffect(final IgnorantBlissEffect effect) {
        super(effect);
    }

    @Override
    public IgnorantBlissEffect copy() {
        return new IgnorantBlissEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards hand = new CardsImpl(controller.getHand());
        controller.moveCardsToExile(hand.getCards(game), source, game, false, CardUtil.getExileZoneId(game, source), CardUtil.getSourceName(game, source));
        hand.getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> game.getState().getZone(card.getId()) == Zone.EXILED)
                .forEach(card -> card.setFaceDown(true, game));
        DelayedTriggeredAbility ability = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnFromExileEffect(Zone.HAND));
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        game.addDelayedTriggeredAbility(ability, source);
        return true;
    }
}
