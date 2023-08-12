
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrimalAmulet extends CardImpl {

    private static final FilterCard filter = new FilterCard("Instant and sorcery spells");

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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever you cast an instant or sorcery spell, put a charge counter on Primal Amulet. Then if there are four or more charge counters on it, you may remove those counters and transform it.
        this.addAbility(new TransformAbility());
        this.addAbility(new SpellCastControllerTriggeredAbility(new PrimalAmuletEffect(), new FilterInstantOrSorcerySpell(), false));
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
        this.staticText = "put a charge counter on {this}. "
                + "Then if there are four or more charge counters on it, "
                + "you may remove those counters and transform it";
    }

    PrimalAmuletEffect(final PrimalAmuletEffect effect) {
        super(effect);
    }

    @Override
    public PrimalAmuletEffect copy() {
        return new PrimalAmuletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && player != null) {
            permanent.addCounters(CounterType.CHARGE.createInstance(), source.getControllerId(), source, game);
            int counters = permanent.getCounters(game).getCount(CounterType.CHARGE);
            if (counters > 3 && player.chooseUse(Outcome.Benefit, "Transform this?", source, game)) {
                permanent.removeCounters(CounterType.CHARGE.getName(), counters, source, game);
                new TransformSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }
}
