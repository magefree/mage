package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.RampageAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.SurvivorToken;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class VarchildsWarRiders extends CardImpl {

    public VarchildsWarRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Cumulative upkeep-Put a 1/1 red Survivor creature token onto the battlefield under an opponent's control.
        this.addAbility(new CumulativeUpkeepAbility(new OpponentCreateSurvivorTokenCost()));

        // Trample; rampage 1
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new RampageAbility(1));
    }

    private VarchildsWarRiders(final VarchildsWarRiders card) {
        super(card);
    }

    @Override
    public VarchildsWarRiders copy() {
        return new VarchildsWarRiders(this);
    }
}

class OpponentCreateSurvivorTokenCost extends CostImpl {

    public OpponentCreateSurvivorTokenCost() {
        this.text = "Have an opponent create a 1/1 red Survivor creature token";
    }

    public OpponentCreateSurvivorTokenCost(OpponentCreateSurvivorTokenCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        Player controller = game.getPlayer(controllerId);
        return controller != null && (!game.getOpponents(controllerId).isEmpty());
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        if (controller != null) {
            TargetOpponent target = new TargetOpponent();
            if (controller.chooseTarget(Outcome.Neutral, target, ability, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent != null) {
                    Effect effect = new CreateTokenTargetEffect(new SurvivorToken());
                    effect.setTargetPointer(new FixedTarget(opponent.getId(), game));
                    paid = effect.apply(game, ability);
                }
            }
        }
        return paid;
    }

    @Override
    public OpponentCreateSurvivorTokenCost copy() {
        return new OpponentCreateSurvivorTokenCost(this);
    }

}
