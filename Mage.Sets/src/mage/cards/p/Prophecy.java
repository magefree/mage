package mage.cards.p;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author noahg
 */
public final class Prophecy extends CardImpl {

    public Prophecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");
        

        // Reveal the top card of target opponent's library. If it's a land, you gain 1 life. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ProphecyEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false).concatBy("<br>"));
    }

    private Prophecy(final Prophecy card) {
        super(card);
    }

    @Override
    public Prophecy copy() {
        return new Prophecy(this);
    }
}

class ProphecyEffect extends OneShotEffect {

    public ProphecyEffect() {
        super(Outcome.GainLife);
        this.staticText = "Reveal the top card of target opponent's library. If it's a land, you gain 1 life. Then that player shuffles";
    }

    public ProphecyEffect(final ProphecyEffect effect) {
        super(effect);
    }

    @Override
    public ProphecyEffect copy() {
        return new ProphecyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (sourceObject == null || targetPlayer == null || controller == null) {
            return false;
        }
        if (targetPlayer.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);
            targetPlayer.revealCards(sourceObject.getIdName(), cards, game);
            if (card.isLand(game)) {
                controller.gainLife(1, game, source);
            }
            targetPlayer.shuffleLibrary(source, game);
        }
        return true;
    }
}
