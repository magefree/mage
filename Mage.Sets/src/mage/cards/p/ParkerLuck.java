package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class ParkerLuck extends CardImpl {

    public ParkerLuck(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        

        // At the beginning of your end step, two target players each reveal the top card of their library. They each lose life equal to the mana value of the card revealed by the other player. Then they each put the card they revealed into their hand.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new ParkerLuckEffect());
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    private ParkerLuck(final ParkerLuck card) {
        super(card);
    }

    @Override
    public ParkerLuck copy() {
        return new ParkerLuck(this);
    }
}

class ParkerLuckEffect extends OneShotEffect {

    ParkerLuckEffect() {
        super(Outcome.Damage);
        staticText = "two target players each reveal the top card of their library. " +
                "They each lose life equal to the mana value of the card " +
                "revealed by the other player. Then they each put the card they revealed into their hand";
    }

    protected ParkerLuckEffect(final ParkerLuckEffect effect) {
        super(effect);
    }

    @Override
    public ParkerLuckEffect copy() {
        return new ParkerLuckEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOne = game.getPlayer(getTargetPointer().getTargets(game, source).get(0));
        Player targetTwo = game.getPlayer(getTargetPointer().getTargets(game, source).get(1));
        if (targetOne == null || targetTwo == null) {
            return false;
        }
        // each reveal top card
        Card targetOneCard = targetOne.getLibrary().getFromTop(game);
        int targetOneMv = 0;
        Card targetTwoCard = targetTwo.getLibrary().getFromTop(game);
        int targetTwoMv = 0;
        if (targetOneCard != null) {
            targetOne.revealCards(source, new CardsImpl(targetOneCard), game);
            targetOneMv = targetOneCard.getManaValue();
        }
        if (targetTwoCard != null) {
            targetTwo.revealCards(source, new CardsImpl(targetTwoCard), game);
            targetTwoMv = targetTwoCard.getManaValue();
        }
        // lose life to mana value of each others card
        targetOne.loseLife(targetTwoMv, game, source, false);
        targetTwo.loseLife(targetOneMv, game, source, false);
        // each put card into their hand
        targetOne.moveCards(targetOneCard, Zone.HAND, source, game);
        targetTwo.moveCards(targetTwoCard, Zone.HAND, source, game);
        return true;
    }
}