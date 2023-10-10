
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class KnollspineDragon extends CardImpl {

    public KnollspineDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Knollspine Dragon enters the battlefield, you may discard your hand and draw cards equal to the damage dealt to target opponent this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KnollspineDragonEffect(), true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new AmountOfDamageAPlayerReceivedThisTurnWatcher());

    }

    private KnollspineDragon(final KnollspineDragon card) {
        super(card);
    }

    @Override
    public KnollspineDragon copy() {
        return new KnollspineDragon(this);
    }
}

class KnollspineDragonEffect extends OneShotEffect {

    public KnollspineDragonEffect() {
        super(Outcome.Neutral);
        staticText = "you may discard your hand and draw cards equal to the damage dealt to target opponent this turn";
    }

    private KnollspineDragonEffect(final KnollspineDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null) {
            new DiscardHandControllerEffect().apply(game, source);
            if (targetOpponent != null) {
                AmountOfDamageAPlayerReceivedThisTurnWatcher watcher = game.getState().getWatcher(AmountOfDamageAPlayerReceivedThisTurnWatcher.class);
                if (watcher != null) {
                    int drawAmount = watcher.getAmountOfDamageReceivedThisTurn(targetOpponent.getId());
                    controller.drawCards(drawAmount, source, game);
                    return true;
                }
            }
            game.informPlayers(controller.getLogName() + " drew no cards");
            return true;
        }
        return false;
    }

    @Override
    public KnollspineDragonEffect copy() {
        return new KnollspineDragonEffect(this);
    }

}
