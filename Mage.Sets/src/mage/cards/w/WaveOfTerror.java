
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class WaveOfTerror extends CardImpl {

    public WaveOfTerror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // At the beginning of your draw step, destroy each creature with converted mana cost equal to the number of age counters on Wave of Terror. They can't be regenerated.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new WaveOfTerrorEffect(), TargetController.YOU, false));
    }

    private WaveOfTerror(final WaveOfTerror card) {
        super(card);
    }

    @Override
    public WaveOfTerror copy() {
        return new WaveOfTerror(this);
    }
}

class WaveOfTerrorEffect extends OneShotEffect {

    WaveOfTerrorEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy each creature with mana value equal to the number of age counters on {this}. They can't be regenerated.";
    }

    WaveOfTerrorEffect(final WaveOfTerrorEffect effect) {
        super(effect);
    }

    @Override
    public WaveOfTerrorEffect copy() {
        return new WaveOfTerrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new ManaValuePredicate(
                ComparisonType.EQUAL_TO,
                permanent.getCounters(game).getCount(CounterType.AGE)
        ));
        return new DestroyAllEffect(filter, true).apply(game, source);
    }
}
