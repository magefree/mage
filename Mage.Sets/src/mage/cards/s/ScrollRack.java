package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public final class ScrollRack extends CardImpl {

    public ScrollRack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {1}, {tap}: Exile any number of cards from your hand face down. Put that many cards from the top of your library into your hand. Then look at the exiled cards and put them on top of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScrollRackEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ScrollRack(final ScrollRack card) {
        super(card);
    }

    @Override
    public ScrollRack copy() {
        return new ScrollRack(this);
    }
}

class ScrollRackEffect extends OneShotEffect {

    public ScrollRackEffect() {
        super(Outcome.Neutral);
        staticText = "Exile any number of cards from your hand face down. Put that many cards from the top of your library into your hand. Then look at the exiled cards and put them on top of your library in any order";
    }

    public ScrollRackEffect(final ScrollRackEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterCard("card in your hand to exile");
            TargetCardInHand target = new TargetCardInHand(0, controller.getHand().size(), filter);
            target.setRequired(false);
            int amountExiled = 0;
            if (target.canChoose(source.getControllerId(), source, game) && target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game)) {
                if (!target.getTargets().isEmpty()) {
                    for (UUID targetId : target.getTargets()) {
                        Card card = game.getCard(targetId);
                        if (card != null) {
                            card.setFaceDown(true, game);
                            amountExiled++;
                        }
                    }
                    controller.moveCardsToExile(new CardsImpl(target.getTargets()).getCards(game), source, game, false, source.getSourceId(), sourceObject.getIdName());
                    ExileZone exileZone = game.getExile().getExileZone(source.getSourceId());
                    if (exileZone != null) {
                        for (Card card : exileZone.getCards(game)) {
                            card.setFaceDown(true, game);
                        }
                    }
                }
            }
            // Put that many cards from the top of your library into your hand.
            if (amountExiled > 0) {
                controller.moveCards(controller.getLibrary().getTopCards(game, amountExiled), Zone.HAND, source, game);
            }
            // Then look at the exiled cards and put them on top of your library in any order
            controller.putCardsOnTopOfLibrary(game.getExile().getExileZone(source.getSourceId()), game, source, true);
            return true;
        }
        return false;
    }

    @Override
    public ScrollRackEffect copy() {
        return new ScrollRackEffect(this);
    }
}
