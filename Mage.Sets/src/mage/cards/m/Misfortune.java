package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Misfortune extends CardImpl {

    public Misfortune(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}{G}");

        // An opponent chooses one —
        this.getSpellAbility().getModes().setModeChooser(TargetController.OPPONENT);

        // • You put a +1/+1 counter on each creature you control and gain 4 life.
        this.getSpellAbility().addEffect(new AddCountersAllEffect(
                CounterType.P1P1.createInstance(), StaticFilters.FILTER_CONTROLLED_CREATURE
        ).setText("you put a +1/+1 counter on each creature you control"));
        this.getSpellAbility().addEffect(new GainLifeEffect(4).setText("and gain 4 life"));

        // • You put a -1/-1 counter on each creature that player controls and Misfortune deals 4 damage to that player.
        this.getSpellAbility().addMode(new Mode(new MisfortuneEffect()));
    }

    private Misfortune(final Misfortune card) {
        super(card);
    }

    @Override
    public Misfortune copy() {
        return new Misfortune(this);
    }
}

class MisfortuneEffect extends OneShotEffect {

    MisfortuneEffect() {
        super(Outcome.Benefit);
        staticText = "you put a -1/-1 counter on each creature that player controls " +
                "and {this} deals 4 damage to that player";
    }

    private MisfortuneEffect(final MisfortuneEffect effect) {
        super(effect);
    }

    @Override
    public MisfortuneEffect copy() {
        return new MisfortuneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer((UUID) getValue("choosingPlayer"));
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game
        )) {
            permanent.addCounters(CounterType.M1M1.createInstance(), source.getControllerId(), source, game);
        }
        player.damage(4, source, game);
        return true;
    }
}
