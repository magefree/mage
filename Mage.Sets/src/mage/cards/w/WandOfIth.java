package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author fireshoes & L_J
 */
public final class WandOfIth extends CardImpl {

    public WandOfIth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {T}: Target player reveals a card at random from their hand. If it's a land card, that player discards it unless they pay 1 life. If it isn't a land card, the player discards it unless they pay life equal to its converted mana cost. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new WandOfIthEffect(), new GenericManaCost(3), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);
    }

    private WandOfIth(final WandOfIth card) {
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
        staticText = "Target player reveals a card at random from their hand. If it's a land card, that player discards it unless they pay 1 life. If it isn't a land card, the player discards it unless they pay life equal to its mana value";
    }

    private WandOfIthEffect(final WandOfIthEffect effect) {
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
                int lifeToPay = card.isLand(game) ? 1 : card.getManaValue();
                PayLifeCost cost = new PayLifeCost(lifeToPay);
                if (cost.canPay(source, source, player.getId(), game)
                        && player.chooseUse(outcome, "Pay " + lifeToPay + " life to prevent discarding " + card.getLogName() + "?", source, game)
                        && cost.pay(source, game, source, player.getId(), false, null)) {
                    game.informPlayers(player.getLogName() + " has paid " + lifeToPay + " life to prevent discarding " + card.getLogName() + " (" + sourcePermanent.getLogName() + ')');
                } else {
                    player.discard(card, false, source, game);
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
