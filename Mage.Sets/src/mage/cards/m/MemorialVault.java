package mage.cards.m;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MemorialVault extends CardImpl {

    public MemorialVault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}");

        // {T}, Sacrifice another artifact: Exile the top X cards of your library, where X is one plus the mana value of the sacrificed artifact. You may play those cards this turn.
        Ability ability = new SimpleActivatedAbility(new MemorialVaultEffect(), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT));
        this.addAbility(ability);
    }

    private MemorialVault(final MemorialVault card) {
        super(card);
    }

    @Override
    public MemorialVault copy() {
        return new MemorialVault(this);
    }
}

class MemorialVaultEffect extends OneShotEffect {

    MemorialVaultEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top X cards of your library, where X is one plus " +
                "the mana value of the sacrificed artifact. You may play those cards this turn";
    }

    private MemorialVaultEffect(final MemorialVaultEffect effect) {
        super(effect);
    }

    @Override
    public MemorialVaultEffect copy() {
        return new MemorialVaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int xValue = 1 + CardUtil
                .castStream(source.getCosts(), SacrificeTargetCost.class)
                .map(SacrificeTargetCost::getPermanents)
                .flatMap(Collection::stream)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return new ExileTopXMayPlayUntilEffect(xValue, Duration.EndOfTurn).apply(game, source);
    }
}
