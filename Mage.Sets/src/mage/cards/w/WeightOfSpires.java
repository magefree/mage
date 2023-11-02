
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class WeightOfSpires extends CardImpl {

    public WeightOfSpires(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Weight of Spires deals damage to target creature equal to the number of nonbasic lands that creature's controller controls.
        this.getSpellAbility().addEffect(new WeightOfSpiresEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WeightOfSpires(final WeightOfSpires card) {
        super(card);
    }

    @Override
    public WeightOfSpires copy() {
        return new WeightOfSpires(this);
    }
}

class WeightOfSpiresEffect extends OneShotEffect {

    WeightOfSpiresEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this} deals damage to target creature equal to the number of nonbasic lands that creature's controller controls";
    }

    private WeightOfSpiresEffect(final WeightOfSpiresEffect effect) {
        super(effect);
    }

    @Override
    public WeightOfSpiresEffect copy() {
        return new WeightOfSpiresEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        Player player = game.getPlayer(creature.getControllerId());
        if (player == null) {
            return false;
        }
        int damage = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_LANDS_NONBASIC, player.getId(), game).size();
        return new DamageTargetEffect(damage).apply(game, source);
    }
}
