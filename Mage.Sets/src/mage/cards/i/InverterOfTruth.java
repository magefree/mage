
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class InverterOfTruth extends CardImpl {

    public InverterOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Inverter of Truth enters the battlefield, exile all cards from your library face down, then shuffle all cards from your graveyard into your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ExileLibraryEffect(), false));
    }

    public InverterOfTruth(final InverterOfTruth card) {
        super(card);
    }

    @Override
    public InverterOfTruth copy() {
        return new InverterOfTruth(this);
    }
}

class ExileLibraryEffect extends OneShotEffect {

    public ExileLibraryEffect() {
        super(Outcome.Exile);
        staticText = "exile all cards from your library face down, then shuffle all cards from your graveyard into your library";
    }

    @Override
    public ExileLibraryEffect copy() {
        return new ExileLibraryEffect();
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = controller.getLibrary().size();
            if (count > 0) {
                for (Card cardToExile: controller.getLibrary().getCards(game)) {
                    cardToExile.moveToExile(null, "", source.getSourceId(), game);
                    cardToExile.setFaceDown(true, game);
                }
            }
            for (Card cardToLibrary: controller.getGraveyard().getCards(game)) {
                controller.moveCardToLibraryWithInfo(cardToLibrary, source.getSourceId(), game, Zone.GRAVEYARD, true, true);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
