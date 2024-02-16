
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class Meadowboon extends CardImpl {

    public Meadowboon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Meadowboon leaves the battlefield, put a +1/+1 counter on each creature target player controls.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new MeadowboonEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        // Evoke {3}{W}
        this.addAbility(new EvokeAbility("{3}{W}"));
    }

    private Meadowboon(final Meadowboon card) {
        super(card);
    }

    @Override
    public Meadowboon copy() {
        return new Meadowboon(this);
    }
}

class MeadowboonEffect extends OneShotEffect {

    MeadowboonEffect() {
        super(Outcome.UnboostCreature);
        staticText = "put a +1/+1 counter on each creature target player controls";
    }

    private MeadowboonEffect(final MeadowboonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player target = game.getPlayer(source.getFirstTarget());
        if (target != null) {
            for (Permanent p : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, target.getId(), game)) {
                p.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public MeadowboonEffect copy() {
        return new MeadowboonEffect(this);
    }

}
