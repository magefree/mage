package mage.abilities.effects;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.cards.Card;
import mage.cards.MeldCard;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.players.Player;

public class ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect extends OneShotEffect {

    private final MageObjectReference objectToReturn;
    private final Counters counters;
    private final String counterText;

    public ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
            MageObjectReference objectToReturn,
            Counter counter,
            String counterText
    ) {
        this(objectToReturn, new Counters().addCounter(counter), counterText);
    }

    public ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
            MageObjectReference objectToReturn,
            Counters counters,
            String counterText
    ) {
        super(Outcome.PutCardInPlay);
        this.objectToReturn = objectToReturn;
        this.counters = counters.copy();
        this.counterText = counterText;
        this.staticText = "return that card to the battlefield with " + counterText + " on it";
    }

    private ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(
            final ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect effect
    ) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
        this.counters = effect.counters.copy();
        this.counterText = effect.counterText;
    }

    @Override
    public ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect copy() {
        return new ReturnMORToBattlefieldUnderOwnerControlWithCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(objectToReturn.getSourceId());
        if (card != null && objectToReturn.refersTo(card, game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                if (card instanceof MeldCard) {
                    MeldCard meldCard = (MeldCard) card;
                    game.setEnterWithCounters(meldCard.getTopHalfCard().getId(), counters);
                    game.setEnterWithCounters(meldCard.getBottomHalfCard().getId(), counters);
                } else {
                    game.setEnterWithCounters(objectToReturn.getSourceId(), counters);
                }
                owner.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return true;
    }
}