package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.costs.costadjusters.CommanderManaValueAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GreatestCommanderManaValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VisionsOfDominance extends CardImpl {

    public VisionsOfDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Put a +1/+1 counter on target creature, then double the number of +1/+1 counters on it.
        this.getSpellAbility().addEffect(new VisionsOfDominanceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {8}{G}{G}. This spell costs {X} less to cast this way, where X is the greatest mana value of a commander you own on the battlefield or in the command zone.
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{8}{G}{G}"))
                .setAbilityName("This spell costs {X} less to cast this way, where X is the greatest mana value " +
                        "of a commander you own on the battlefield or in the command zone.")
                .setCostAdjuster(CommanderManaValueAdjuster.instance)
                .addHint(GreatestCommanderManaValue.getHint()));
    }

    private VisionsOfDominance(final VisionsOfDominance card) {
        super(card);
    }

    @Override
    public VisionsOfDominance copy() {
        return new VisionsOfDominance(this);
    }
}

class VisionsOfDominanceEffect extends OneShotEffect {

    VisionsOfDominanceEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on target creature, then double the number of +1/+1 counters on it";
    }

    private VisionsOfDominanceEffect(final VisionsOfDominanceEffect effect) {
        super(effect);
    }

    @Override
    public VisionsOfDominanceEffect copy() {
        return new VisionsOfDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        int counterCount = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (counterCount > 0) {
            permanent.addCounters(CounterType.P1P1.createInstance(counterCount), source, game);
        }
        return true;
    }
}
