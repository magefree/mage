
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EwokToken;
import mage.players.Player;

/**
 *
 * @author Styxo
 */
public final class TheBattleOfEndor extends CardImpl {

    public TheBattleOfEndor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{X}{G}{G}{G}");

        // Create X 1/1 green Ewok creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new EwokToken(), ManacostVariableValue.REGULAR));

        // Put X +1/+1 counters on each creature you control.
        this.getSpellAbility().addEffect(new TheBattleOfEndorEffect());

        // Creatures you control gain trample and haste until end of turn.
        Effect effect = new GainAbilityAllEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent());
        effect.setText("Creatures you control gain trample");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent());
        effect.setText("and haste until end of turn");
        this.getSpellAbility().addEffect(effect);

    }

    private TheBattleOfEndor(final TheBattleOfEndor card) {
        super(card);
    }

    @Override
    public TheBattleOfEndor copy() {
        return new TheBattleOfEndor(this);
    }
}

class TheBattleOfEndorEffect extends OneShotEffect {

    TheBattleOfEndorEffect() {
        super(Outcome.Benefit);
        staticText = "Put X +1/+1 counters on each creature you control";
    }

    TheBattleOfEndorEffect(TheBattleOfEndorEffect effect) {
        super(effect);
    }

    @Override
    public TheBattleOfEndorEffect copy() {
        return new TheBattleOfEndorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source, game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(source.getManaCostsToPay().getX()), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

}
