
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class RuneflareTrap extends CardImpl {

    public RuneflareTrap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}{R}");
        this.subtype.add(SubType.TRAP);

        // If an opponent drew three or more cards this turn, you may pay {R} rather than pay Runeflare Trap's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ManaCostsImpl<>("{R}"), RuneflareTrapCondition.instance), new CardsAmountDrawnThisTurnWatcher());

        // Runeflare Trap deals damage to target player equal to the number of cards in that player's hand.
        this.getSpellAbility().addEffect(new DamageTargetEffect(new TargetPlayerCardsInHandCount()));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private RuneflareTrap(final RuneflareTrap card) {
        super(card);
    }

    @Override
    public RuneflareTrap copy() {
        return new RuneflareTrap(this);
    }
}

class TargetPlayerCardsInHandCount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player targetPlayer = game.getPlayer(sourceAbility.getFirstTarget());
        if (targetPlayer != null) {
            return targetPlayer.getHand().size();
        }

        return 0;
    }

    @Override
    public TargetPlayerCardsInHandCount copy() {
        return new TargetPlayerCardsInHandCount();
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "target player's cards in hand";
    }
}

enum RuneflareTrapCondition implements Condition {

 instance;

    @Override
    public boolean apply(Game game, Ability source) {
        CardsAmountDrawnThisTurnWatcher watcher =
                game.getState().getWatcher(CardsAmountDrawnThisTurnWatcher.class);
        return watcher != null && watcher.opponentDrewXOrMoreCards(source.getControllerId(), 3, game);
    }

    @Override
    public String toString() {
        return "If an opponent drew three or more cards this turn";
    }
}
