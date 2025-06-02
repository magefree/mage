package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TipTheScales extends CardImpl {

    public TipTheScales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Sacrifice a creature. When you do, all creatures get -X/-X until end of turn, where X is the sacrificed creature's toughness.
        this.getSpellAbility().addEffect(new TipTheScalesEffect());
    }

    private TipTheScales(final TipTheScales card) {
        super(card);
    }

    @Override
    public TipTheScales copy() {
        return new TipTheScales(this);
    }
}

class TipTheScalesEffect extends OneShotEffect {

    TipTheScalesEffect() {
        super(Outcome.Benefit);
        staticText = "sacrifice a creature. When you do, all creatures get " +
                "-X/-X until end of turn, where X is the sacrificed creature's toughness";
    }

    private TipTheScalesEffect(final TipTheScalesEffect effect) {
        super(effect);
    }

    @Override
    public TipTheScalesEffect copy() {
        return new TipTheScalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetSacrifice(StaticFilters.FILTER_CONTROLLED_CREATURE);
        if (!target.canChoose(source.getControllerId(), source, game)) {
            return false;
        }
        player.choose(Outcome.Sacrifice, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int value = permanent.getToughness().getValue();
        if (!permanent.sacrifice(source, game) || value < 1) {
            return false;
        }
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new BoostAllEffect(-value, -value, Duration.EndOfTurn), false
        ), source);
        return true;
    }
}
