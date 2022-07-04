package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jonubuu
 */
public final class GolgariGraveTroll extends CardImpl {

    public GolgariGraveTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.SKELETON);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Golgari Grave-Troll enters the battlefield with a +1/+1 counter on it for each creature card in your graveyard.
        this.addAbility(new EntersBattlefieldAbility(new GolgariGraveTrollEffect(), "with a +1/+1 counter on it for each creature card in your graveyard"));
        // {1}, Remove a +1/+1 counter from Golgari Grave-Troll: Regenerate Golgari Grave-Troll.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
        // Dredge 6
        this.addAbility(new DredgeAbility(6));
    }

    private GolgariGraveTroll(final GolgariGraveTroll card) {
        super(card);
    }

    @Override
    public GolgariGraveTroll copy() {
        return new GolgariGraveTroll(this);
    }
}

class GolgariGraveTrollEffect extends OneShotEffect {

    public GolgariGraveTrollEffect() {
        super(Outcome.BoostCreature);
    }

    public GolgariGraveTrollEffect(final GolgariGraveTrollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null && player != null) {
            int amount = player.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game);
            if (amount > 0) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public GolgariGraveTrollEffect copy() {
        return new GolgariGraveTrollEffect(this);
    }
}
