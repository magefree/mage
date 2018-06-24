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
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public final class Prophecy extends CardImpl {

    public Prophecy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}");
        

        // Reveal the top card of target opponent's library. If it's a land, you gain 1 life. Then that player shuffles his or her library.
        this.spellAbility.addEffect(new ProphecyEffect());

        // Draw a card at the beginning of the next turn's upkeep.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(new DrawCardSourceControllerEffect(1)), false));
    }

    public Prophecy(final Prophecy card) {
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
        this.staticText = "Reveal the top card of target opponent's library. If it's a land, you gain 1 life. Then that player shuffles his or her library";
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
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null || targetPlayer == null) {
            return false;
        }
        if (targetPlayer.getLibrary().hasCards()) {
            CardsImpl cards = new CardsImpl();
            Card card = targetPlayer.getLibrary().getFromTop(game);
            if (card == null) {
                return false;
            }
            cards.add(card);
            targetPlayer.revealCards(sourceObject.getName(), cards, game);
            if (card.isLand()) {
                targetPlayer.gainLife(1, game, source.getSourceId());
            }
            targetPlayer.shuffleLibrary(source, game);
        }
        return true;
    }
}
