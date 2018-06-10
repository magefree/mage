
package mage.cards.i;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class IgnorantBliss extends CardImpl {

    public IgnorantBliss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Exile all cards from your hand face down. At the beginning of the next end step, return those cards to your hand, then draw a card.
        this.getSpellAbility().addEffect(new IgnorantBlissExileEffect());
        this.getSpellAbility().addEffect(new IgnorantBlissReturnEffect());

    }

    public IgnorantBliss(final IgnorantBliss card) {
        super(card);
    }

    @Override
    public IgnorantBliss copy() {
        return new IgnorantBliss(this);
    }
}

class IgnorantBlissExileEffect extends OneShotEffect {

    IgnorantBlissExileEffect() {
        super(Outcome.Exile);
        this.staticText = "Exile all cards from your hand face down";
    }

    IgnorantBlissExileEffect(final IgnorantBlissExileEffect effect) {
        super(effect);
    }

    @Override
    public IgnorantBlissExileEffect copy() {
        return new IgnorantBlissExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null
                && sourceObject != null) {
            Cards hand = controller.getHand();
            hand.getCards(game).stream().filter((card) -> (card != null)).map((card) -> {
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), 0);
                controller.moveCardsToExile(card, source, game, false, exileZoneId, sourceObject.getIdName());
                return card;
            }).forEachOrdered((card) -> {
                card.setFaceDown(true, game);
            });
            return true;
        }
        return false;
    }
}

class IgnorantBlissReturnEffect extends OneShotEffect {

    IgnorantBlissReturnEffect() {
        super(Outcome.DrawCard);
        this.staticText = "At the beginning of the next end step, return those cards to your hand, then draw a card";
    }

    IgnorantBlissReturnEffect(final IgnorantBlissReturnEffect effect) {
        super(effect);
    }

    @Override
    public IgnorantBlissReturnEffect copy() {
        return new IgnorantBlissReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), 0));
            if (exileZone != null) {
                Effect effect = new ReturnFromExileEffect(exileZone.getId(), Zone.HAND);
                AtTheBeginOfNextEndStepDelayedTriggeredAbility ability = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                ability.addEffect(new DrawCardSourceControllerEffect(1));
                game.addDelayedTriggeredAbility(ability, source);
                return true;
            }
        }
        return false;
    }
}
