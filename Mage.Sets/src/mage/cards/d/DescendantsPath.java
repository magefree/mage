package mage.cards.d;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author noxx
 *
 */
public final class DescendantsPath extends CardImpl {

    public DescendantsPath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // At the beginning of your upkeep, reveal the top card of your library. 
        // If it's a creature card that shares a creature type with a creature you control, 
        // you may cast that card without paying its mana cost. Otherwise, put that card on the bottom of your library.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DescendantsPathEffect(), TargetController.YOU, false);
        this.addAbility(ability);
    }

    public DescendantsPath(final DescendantsPath card) {
        super(card);
    }

    @Override
    public DescendantsPath copy() {
        return new DescendantsPath(this);
    }
}

class DescendantsPathEffect extends OneShotEffect {

    public DescendantsPathEffect() {
        super(Outcome.Discard);
        this.staticText = "reveal the top card of your library. If it's a creature "
                + "card that shares a creature type with a creature you control, "
                + "you may cast that card without paying its mana cost. Otherwise, "
                + "put that card on the bottom of your library";
    }

    public DescendantsPathEffect(final DescendantsPathEffect effect) {
        super(effect);
    }

    @Override
    public DescendantsPathEffect copy() {
        return new DescendantsPathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card == null) {
                    return false;
                }
                controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                if (card.isCreature()) {
                    FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                    boolean found = false;
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                        if (card.shareSubtypes(permanent, game)) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        game.informPlayers(sourceObject.getLogName() + ": Found a creature that shares a creature type with the revealed card.");
                        if (controller.chooseUse(Outcome.Benefit, "Cast the card?", source, game)) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
                            controller.cast(controller.chooseAbilityForCast(card, game, true),
                                    game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
                        } else {
                            game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " canceled casting the card.");
                            controller.getLibrary().putOnBottom(card, game);
                        }
                    } else {
                        game.informPlayers(sourceObject.getLogName() + ": No creature that shares a creature type with the revealed card.");
                        controller.getLibrary().putOnBottom(card, game);
                    }
                } else {
                    game.informPlayers(sourceObject.getLogName() + ": Put " + card.getLogName() + " on the bottom.");
                    controller.getLibrary().putOnBottom(card, game);
                }

                return true;
            }
        }
        return false;
    }
}
