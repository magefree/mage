package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;

/**
 * @author LePwnerer
 */
public final class HostileHostel extends CardImpl {

    public HostileHostel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        this.secondSideCardClazz = mage.cards.c.CreepingInn.class;

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice a creature: Put a soul counter on Hostile Hostel. Then if there are three or more soul counters on it, remove those counters, transform it, then untap it. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new HostileHostelEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    private HostileHostel(final HostileHostel card) {
        super(card);
    }

    @Override
    public HostileHostel copy() {
        return new HostileHostel(this);
    }
}

class HostileHostelEffect extends OneShotEffect {

    HostileHostelEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a soul counter on {this}. " +
                "Then if there are three or more soul counters on it, remove those counters, transform it, then untap it.";
    }

    HostileHostelEffect(final mage.cards.h.HostileHostelEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.h.HostileHostelEffect copy() {
        return new mage.cards.h.HostileHostelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null && player != null) {
            permanent.addCounters(CounterType.SOUL.createInstance(), source.getControllerId(), source, game);
            int counters = permanent.getCounters(game).getCount(CounterType.SOUL);
            if (counters > 2) {
                permanent.removeCounters(CounterType.SOUL.getName(), counters, source, game);
                permanent.transform(source, game);
                permanent.untap(game);
            }
            return true;
        }
        return false;
    }
}
