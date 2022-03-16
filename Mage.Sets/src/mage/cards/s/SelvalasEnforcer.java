
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ParleyCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class SelvalasEnforcer extends CardImpl {

    public SelvalasEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Parley - When Selvala's Enforcer enters the battlefield, each player reveals the top card of their library.
        // For each nonland card revealed this way, put a +1/+1 counter on Selvala's Enforcer. Then each player draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new SelvalasEnforcerEffect(), false);
        ability.addEffect(new DrawCardAllEffect(1).setText("Then each player draws a card"));
        ability.setAbilityWord(AbilityWord.PARLEY);
        this.addAbility(ability);
    }

    private SelvalasEnforcer(final SelvalasEnforcer card) {
        super(card);
    }

    @Override
    public SelvalasEnforcer copy() {
        return new SelvalasEnforcer(this);
    }
}

class SelvalasEnforcerEffect extends OneShotEffect {

    public SelvalasEnforcerEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player reveals the top card of their library. For each nonland card revealed this way, put a +1/+1 counter on {this}";
    }

    public SelvalasEnforcerEffect(final SelvalasEnforcerEffect effect) {
        super(effect);
    }

    @Override
    public SelvalasEnforcerEffect copy() {
        return new SelvalasEnforcerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int parley = ParleyCount.getInstance().calculate(game, source, this);
            if (parley > 0) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    sourcePermanent.addCounters(CounterType.P1P1.createInstance(parley), source.getControllerId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
