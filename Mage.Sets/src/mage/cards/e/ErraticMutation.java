package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class ErraticMutation extends CardImpl {

    public ErraticMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Choose target creature. Reveal cards from the top of your library until you reveal a nonland card. That creature gets +X/-X until end of turn, where X is that card's converted mana cost. Put all cards revealed this way on the bottom of your library in any order.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ErraticMutationEffect());

    }

    private ErraticMutation(final ErraticMutation card) {
        super(card);
    }

    @Override
    public ErraticMutation copy() {
        return new ErraticMutation(this);
    }
}

class ErraticMutationEffect extends OneShotEffect {

    public ErraticMutationEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "Choose target creature. Reveal cards from the top of your library until you reveal a nonland card. That creature gets +X/-X until end of turn, where X is that card's mana value. Put all cards revealed this way on the bottom of your library in any order";
    }

    public ErraticMutationEffect(final ErraticMutationEffect effect) {
        super(effect);
    }

    @Override
    public ErraticMutationEffect copy() {
        return new ErraticMutationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            CardsImpl toReveal = new CardsImpl();
            Card nonLandCard = null;
            for (Card card : controller.getLibrary().getCards(game)) {
                toReveal.add(card);
                if (!card.isLand(game)) {
                    nonLandCard = card;
                    break;
                }
            }
            // reveal cards
            controller.revealCards(sourceObject.getIdName(), toReveal, game);

            // the nonland card
            if (nonLandCard != null) {
                int boostValue = nonLandCard.getManaValue();
                // unboost target
                ContinuousEffect effect = new BoostTargetEffect(boostValue, -boostValue, Duration.EndOfTurn);
                effect.setTargetPointer(new FixedTarget(this.getTargetPointer().getFirst(game, source), game));
                game.addEffect(effect, source);
            }
            // put the cards on the bottom of the library in any order
            return controller.putCardsOnBottomOfLibrary(toReveal, game, source, true);
        }
        return false;
    }
}
