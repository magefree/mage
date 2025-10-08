package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VillainousWrath extends CardImpl {

    public VillainousWrath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Target opponent loses life equal to the number of creatures they control. Then destroy all creatures.
        this.getSpellAbility().addEffect(new VillainousWrathEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES).concatBy("Then"));
    }

    private VillainousWrath(final VillainousWrath card) {
        super(card);
    }

    @Override
    public VillainousWrath copy() {
        return new VillainousWrath(this);
    }
}

class VillainousWrathEffect extends OneShotEffect {

    VillainousWrathEffect() {
        super(Outcome.Benefit);
        staticText = "target opponent loses life equal to the number of creatures they control";
    }

    private VillainousWrathEffect(final VillainousWrathEffect effect) {
        super(effect);
    }

    @Override
    public VillainousWrathEffect copy() {
        return new VillainousWrathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int amount = game.getBattlefield().count(StaticFilters.FILTER_CONTROLLED_CREATURE, player.getId(), source, game);
        return amount > 0 && player.loseLife(amount, game, source, false) > 0;
    }
}
