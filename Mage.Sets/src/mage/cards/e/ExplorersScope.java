
package mage.cards.e;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class ExplorersScope extends CardImpl {

    public ExplorersScope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped.
        this.addAbility(new AttacksAttachedTriggeredAbility(new ExplorersScopeEffect()));

        // Equip ({1}: Attach to target creature you control. Equip only as a sorcery.)
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(1)));
    }

    private ExplorersScope(final ExplorersScope card) {
        super(card);
    }

    @Override
    public ExplorersScope copy() {
        return new ExplorersScope(this);
    }
}

class ExplorersScopeEffect extends OneShotEffect {

    public ExplorersScopeEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped";
    }

    private ExplorersScopeEffect(final ExplorersScopeEffect effect) {
        super(effect);
    }

    @Override
    public ExplorersScopeEffect copy() {
        return new ExplorersScopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }

        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl(card);
            controller.lookAtCards(sourceObject.getIdName(), cards, game);
            if (card.isLand(game)) {
                String message = "Put " + card.getLogName() + " onto the battlefield tapped?";
                if (controller.chooseUse(Outcome.PutLandInPlay, message, source, game)) {
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
                }
            }
        }
        return true;
    }
}
