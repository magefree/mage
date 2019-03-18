
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes & L_J
 */
public final class WandOfIth extends CardImpl {

    public WandOfIth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {3}, {T}: Target player reveals a card at random from their hand. If it's a land card, that player discards it unless he or she pays 1 life. If it isn't a land card, the player discards it unless he or she pays life equal to its converted mana cost. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new WandOfIthEffect(), new GenericManaCost(3), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public WandOfIth(final WandOfIth card) {
        super(card);
    }

    @Override
    public WandOfIth copy() {
        return new WandOfIth(this);
    }
}

class WandOfIthEffect extends OneShotEffect {

    public WandOfIthEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals a card at random from their hand. If it's a land card, that player discards it unless he or she pays 1 life. If it isn't a land card, the player discards it unless he or she pays life equal to its converted mana cost";
    }

    public WandOfIthEffect(final WandOfIthEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && !player.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            Card card = player.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                player.revealCards(sourcePermanent.getName(), revealed, game);
                int lifeToPay = card.isLand() ? 1 : card.getConvertedManaCost();
                PayLifeCost cost = new PayLifeCost(lifeToPay);
                if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                        && player.chooseUse(outcome, "Pay " + lifeToPay + " life to prevent discarding " + card.getLogName() + "?", source, game)
                        && cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                    game.informPlayers(player.getLogName() + " has paid " + lifeToPay + " life to prevent discarding " + card.getLogName() + " (" + sourcePermanent.getLogName() + ')');
                } else {
                    player.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WandOfIthEffect copy() {
        return new WandOfIthEffect(this);
    }

}
