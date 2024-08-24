package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Grath
 */
public final class MrFoxglove extends CardImpl {

    public MrFoxglove(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Mr. Foxglove attacks, draw cards equal to the number of cards in defending player's hand minus the number of cards in your hand. If you didn't draw cards this way, you may put a creature card from your hand onto the battlefield.
        this.addAbility(new AttacksTriggeredAbility(new MrFoxgloveEffect(), false, null, SetTargetPointer.PLAYER));
    }

    private MrFoxglove(final MrFoxglove card) {
        super(card);
    }

    @Override
    public MrFoxglove copy() {
        return new MrFoxglove(this);
    }
}

class MrFoxgloveEffect extends OneShotEffect {

    MrFoxgloveEffect() {
        super(Outcome.Benefit);
        staticText = "draw cards equal to the number of cards in defending player's hand minus the number of cards" +
                " in your hand. If you didn't draw cards this way, you may put a creature card from your hand onto" +
                " the battlefield.";
    }

    private MrFoxgloveEffect(final MrFoxgloveEffect effect) {
        super(effect);
    }

    @Override
    public MrFoxgloveEffect copy() {
        return new MrFoxgloveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || player == null) {
            return false;
        }
        int cardsToDraw = player.getHand().size() - controller.getHand().size();
        int cardsDrawn = 0;
        if (cardsToDraw > 0) {
            cardsDrawn = controller.drawCards(cardsToDraw, source, game);
        }
        if (cardsToDraw <= 0 || cardsDrawn == 0) {
            if (controller.chooseUse(Outcome.PutCardInPlay, "Put a creature card from your hand onto the battlefield?", source, game)) {
                TargetCardInHand target = new TargetCardInHand(StaticFilters.FILTER_CARD_CREATURE_A);
                if (controller.choose(Outcome.PutCardInPlay, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        return controller.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, false, null);
                    }
                }
            }
        }
        return true;
    }
}