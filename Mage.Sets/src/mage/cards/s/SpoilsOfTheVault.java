package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SpoilsOfTheVault extends CardImpl {

    public SpoilsOfTheVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Name a card. Reveal cards from the top of your library until you reveal the named card, then put that card into your hand. Exile all other cards revealed this way, and you lose 1 life for each of the exiled cards.
        this.getSpellAbility().addEffect(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        this.getSpellAbility().addEffect(new SpoilsOfTheVaultEffect());
    }

    private SpoilsOfTheVault(final SpoilsOfTheVault card) {
        super(card);
    }

    @Override
    public SpoilsOfTheVault copy() {
        return new SpoilsOfTheVault(this);
    }
}

class SpoilsOfTheVaultEffect extends OneShotEffect {

    public SpoilsOfTheVaultEffect() {
        super(Outcome.Damage);
        this.staticText = "Reveal cards from the top of your library until you reveal a card with that name, "
                + "then put that card into your hand. Exile all other cards revealed this way, and you lose 1 life for each of the exiled cards";
    }

    public SpoilsOfTheVaultEffect(final SpoilsOfTheVaultEffect effect) {
        super(effect);
    }

    @Override
    public SpoilsOfTheVaultEffect copy() {
        return new SpoilsOfTheVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player controller = game.getPlayer(source.getControllerId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (sourceObject == null || controller == null || cardName == null || cardName.isEmpty()) {
            return false;
        }

        Cards cardsToReveal = new CardsImpl();
        Cards cardsToExile = new CardsImpl();
        for (Card card : controller.getLibrary().getCards(game)) {
            if (card != null) {
                cardsToReveal.add(card);
                if (CardUtil.haveSameNames(card, cardName, game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                    break;
                } else {
                    cardsToExile.add(card);
                }
            }
        }
        controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
        controller.moveCards(cardsToExile, Zone.EXILED, source, game);
        controller.loseLife(cardsToExile.size(), game, source, false);

        return true;
    }
}
