
package mage.cards.p;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PetalsOfInsight extends CardImpl {

    public PetalsOfInsight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{U}");
        this.subtype.add(SubType.ARCANE);

        // Look at the top three cards of your library. You may put those cards on the bottom of your library in any order. If you do, return Petals of Insight to its owner's hand. Otherwise, draw three cards.
        this.getSpellAbility().addEffect(new PetalsOfInsightEffect());
    }

    private PetalsOfInsight(final PetalsOfInsight card) {
        super(card);
    }

    @Override
    public PetalsOfInsight copy() {
        return new PetalsOfInsight(this);
    }
}

class PetalsOfInsightEffect extends OneShotEffect {

    PetalsOfInsightEffect() {
        super(Outcome.Benefit);
        this.staticText = "Look at the top three cards of your library. You may put those cards on the bottom of your library in any order. If you do, return {this} to its owner's hand. Otherwise, draw three cards";
    }

    PetalsOfInsightEffect(final PetalsOfInsightEffect effect) {
        super(effect);
    }

    @Override
    public PetalsOfInsightEffect copy() {
        return new PetalsOfInsightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 3));

        controller.lookAtCards(sourceObject.getIdName(), cards, game);
        if (controller.chooseUse(outcome, "Put the cards on the bottom of your library in any order?", source, game)) {
            controller.putCardsOnBottomOfLibrary(cards, game, source, true);
            Card spellCard = game.getStack().getSpell(source.getSourceId()).getCard();
            if (spellCard != null) {
                controller.moveCards(spellCard, Zone.HAND, source, game);
            }
        } else {
            controller.drawCards(3, source, game);
        }
        return true;
    }
}
