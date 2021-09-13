
package mage.cards.s;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public final class SearchForAzcanta extends CardImpl {

    public SearchForAzcanta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.secondSideCardClazz = mage.cards.a.AzcantaTheSunkenRuin.class;

        this.addSuperType(SuperType.LEGENDARY);

        // At the beginning of your upkeep, look at the top card of your library. You may put it into your graveyard. Then if you have seven or more cards in your graveyard, you may transform Search for Azcanta.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new SearchForAzcantaLookLibraryEffect(), TargetController.YOU, false);
        this.addAbility(ability);
        this.addAbility(new TransformAbility());
    }

    private SearchForAzcanta(final SearchForAzcanta card) {
        super(card);
    }

    @Override
    public SearchForAzcanta copy() {
        return new SearchForAzcanta(this);
    }
}

class SearchForAzcantaLookLibraryEffect extends OneShotEffect {

    public SearchForAzcantaLookLibraryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. You may put that card into your graveyard. "
                + "Then if you have seven or more cards in your graveyard, you may transform {this}.";
    }

    public SearchForAzcantaLookLibraryEffect(final SearchForAzcantaLookLibraryEffect effect) {
        super(effect);
    }

    @Override
    public SearchForAzcantaLookLibraryEffect copy() {
        return new SearchForAzcantaLookLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    controller.lookAtCards(sourceObject.getIdName(), new CardsImpl(card), game);
                    if (controller.chooseUse(Outcome.Neutral, "Put that card into your graveyard?", source, game)) {
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }
                    if (controller.getGraveyard().size() > 6 && controller.chooseUse(Outcome.Neutral, "Transform " + sourceObject.getLogName() + "?", source, game)) {
                        new TransformSourceEffect().apply(game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}