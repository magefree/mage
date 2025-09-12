package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimalAmulet extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant and sorcery spells");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public PrimalAmulet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.secondSideCardClazz = mage.cards.p.PrimalWellspring.class;

        // Instant and sorcery spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell, put a charge counter on Primal Amulet. Then if there are four or more charge counters on it, you may remove those counters and transform it.
        this.addAbility(new TransformAbility());
        Ability ability = new SpellCastControllerTriggeredAbility(
                new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addEffect(new PrimalAmuletEffect());
        this.addAbility(ability);
    }

    private PrimalAmulet(final PrimalAmulet card) {
        super(card);
    }

    @Override
    public PrimalAmulet copy() {
        return new PrimalAmulet(this);
    }
}

class PrimalAmuletEffect extends OneShotEffect {

    PrimalAmuletEffect() {
        super(Outcome.Benefit);
        this.staticText = "Then if there are four or more charge counters on it, " +
                "you may remove those counters and transform it";
    }

    private PrimalAmuletEffect(final PrimalAmuletEffect effect) {
        super(effect);
    }

    @Override
    public PrimalAmuletEffect copy() {
        return new PrimalAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null
                || player == null
                || permanent.getCounters(game).getCount(CounterType.CHARGE) <= 3
                || !player.chooseUse(Outcome.Benefit, "Remove all charge counters from this and transform it?", source, game)) {
            return false;
        }
        permanent.removeAllCounters(CounterType.CHARGE.getName(), source, game);
        permanent.transform(source, game);
        return true;
    }
}
