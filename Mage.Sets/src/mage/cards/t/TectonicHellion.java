package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.targetpointer.FixedTarget;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TectonicHellion extends CardImpl {

    public TectonicHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");

        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Tectonic Hellion attacks, each player who controls the most lands sacrifices two lands.
        this.addAbility(new AttacksTriggeredAbility(new TectonicHellionEffect(), false));
    }

    private TectonicHellion(final TectonicHellion card) {
        super(card);
    }

    @Override
    public TectonicHellion copy() {
        return new TectonicHellion(this);
    }
}

class TectonicHellionEffect extends OneShotEffect {

    TectonicHellionEffect() {
        super(Outcome.Benefit);
        staticText = "each player who controls the most lands sacrifices two lands";
    }

    private TectonicHellionEffect(final TectonicHellionEffect effect) {
        super(effect);
    }

    @Override
    public TectonicHellionEffect copy() {
        return new TectonicHellionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> landMap = new HashMap<>();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .forEach(uuid -> landMap.put(uuid, game.getBattlefield().getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, uuid, source, game
                ).size()));
        int max = landMap
                .values()
                .stream()
                .max(Integer::compare)
                .get();
        Effect effect = new SacrificeEffect(StaticFilters.FILTER_LANDS, 2, "");
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .filter(uuid -> landMap.getOrDefault(uuid, 0) == max)
                .forEachOrdered(uuid -> {
                    effect.setTargetPointer(new FixedTarget(uuid, game));
                    effect.apply(game, source);
                });
        return true;
    }
}