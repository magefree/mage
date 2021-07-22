
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
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
 * @author fireshoes
 */
public final class Leveler extends CardImpl {

    public Leveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When Leveler enters the battlefield, exile all cards from your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LevelerExileLibraryEffect(), false));
    }

    private Leveler(final Leveler card) {
        super(card);
    }

    @Override
    public Leveler copy() {
        return new Leveler(this);
    }
}

class LevelerExileLibraryEffect extends OneShotEffect {

    public LevelerExileLibraryEffect() {
        super(Outcome.Exile);
        staticText = "exile all cards from your library";
    }

    private LevelerExileLibraryEffect(final LevelerExileLibraryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = controller.getLibrary().size();
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, count));
            controller.moveCards(cards, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }

    @Override
    public LevelerExileLibraryEffect copy() {
        return new LevelerExileLibraryEffect(this);
    }
}
