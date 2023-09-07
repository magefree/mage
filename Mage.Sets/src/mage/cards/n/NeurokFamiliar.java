
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author rollergo11
 */
public final class NeurokFamiliar extends CardImpl {

    public NeurokFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Neurok Familiar enters the battlefield, reveal the top card of your library. If it's an artifact card, put it into your hand. Otherwise, put it into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new NeurokFamiliarEffect()));
    }

    private NeurokFamiliar(final NeurokFamiliar card) {
        super(card);
    }

    @Override
    public NeurokFamiliar copy() {
        return new NeurokFamiliar(this);
    }
}

class NeurokFamiliarEffect extends OneShotEffect {

    public NeurokFamiliarEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top card of your library. If it's an artifact card, put it into your hand. Otherwise, put it into your graveyard.";
    }

    private NeurokFamiliarEffect(final NeurokFamiliarEffect effect) {
        super(effect);
    }

    @Override
    public NeurokFamiliarEffect copy() {
        return new NeurokFamiliarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        if (controller.getLibrary().hasCards()) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.revealCards(sourceObject.getIdName(), cards, game);
                if (card.isArtifact(game)) {
                    controller.moveCards(card, Zone.HAND, source, game);
                } else {
                    controller.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }

}
