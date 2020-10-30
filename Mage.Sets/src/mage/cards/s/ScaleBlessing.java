
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class ScaleBlessing extends CardImpl {

    public ScaleBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Bolster 1, then put a +1/+1 counter on each creature you control with a +1/+1 counter on it. <i.(To bolster 1, choose a creature with the least toughness among creatures you control and put +1/+1 counter on it.)</i>
        Effect effect = new BolsterEffect(1);
        effect.setText("Bolster 1");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new ScaleBlessingEffect());

    }

    public ScaleBlessing(final ScaleBlessing card) {
        super(card);
    }

    @Override
    public ScaleBlessing copy() {
        return new ScaleBlessing(this);
    }
}

class ScaleBlessingEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public ScaleBlessingEffect() {
        super(Outcome.Benefit);
        this.staticText = ", then put a +1/+1 counter on each creature you control with a +1/+1 counter on it. <i>(To bolster 1, choose a creature with the least toughness among creatures you control and put +1/+1 counter on it.)</i>";
    }

    public ScaleBlessingEffect(final ScaleBlessingEffect effect) {
        super(effect);
    }

    @Override
    public ScaleBlessingEffect copy() {
        return new ScaleBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(filter, controller.getId(), game)) {
                permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
                game.informPlayers(sourceObject.getName() + ": Put a +1/+1 counter on " + permanent.getLogName());
            }
        }
        return true;
    }
}
