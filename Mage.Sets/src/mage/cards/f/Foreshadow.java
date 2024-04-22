package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Quercitron & L_J
 */
public final class Foreshadow extends CardImpl {

    public Foreshadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose a card name, then target opponent puts the top card of their library into their graveyard. If that card has the chosen name, you draw a card.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new ForeshadowEffect().concatBy(", then"));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false)
                .concatBy("<br>"));
    }

    private Foreshadow(final Foreshadow card) {
        super(card);
    }

    @Override
    public Foreshadow copy() {
        return new Foreshadow(this);
    }
}

class ForeshadowEffect extends OneShotEffect {

    ForeshadowEffect() {
        super(Outcome.DrawCard);
        this.staticText = "target opponent mills a card. If a card with the chosen name was milled this way, you draw a card";
    }

    private ForeshadowEffect(final ForeshadowEffect effect) {
        super(effect);
    }

    @Override
    public ForeshadowEffect copy() {
        return new ForeshadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (controller == null || targetPlayer == null || cardName == null || cardName.isEmpty()) {
            return false;
        }
        for (Card card : targetPlayer.millCards(1, source, game).getCards(game)) {
            if (CardUtil.haveSameNames(card, cardName, game)) {
                controller.drawCards(1, source, game);
                break;
            }
        }
        return true;
    }

}
