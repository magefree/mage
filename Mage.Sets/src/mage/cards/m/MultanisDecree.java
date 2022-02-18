
package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class MultanisDecree extends CardImpl {

    public MultanisDecree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Destroy all enchantments. You gain 2 life for each enchantment destroyed this way.
        this.getSpellAbility().addEffect(new MultanisDecreeDestroyEffect());
    }

    private MultanisDecree(final MultanisDecree card) {
        super(card);
    }

    @Override
    public MultanisDecree copy() {
        return new MultanisDecree(this);
    }
}

class MultanisDecreeDestroyEffect extends OneShotEffect {
    public MultanisDecreeDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all enchantments. You gain 2 life for each enchantment destroyed this way";
    }

    public MultanisDecreeDestroyEffect(final MultanisDecreeDestroyEffect effect) {
        super(effect);
    }

    @Override
    public MultanisDecreeDestroyEffect copy() {
        return new MultanisDecreeDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
		int enchantmentsDestoyed = 0;
        for (Permanent permanent: game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_ENCHANTMENT,  source.getControllerId(), source.getSourceId(), game)) {
            if (permanent.destroy(source, game, false)) {
                enchantmentsDestoyed++;
            }
        }
		if(enchantmentsDestoyed > 0 && controller != null) {
			controller.gainLife(enchantmentsDestoyed * 2, game, source);
		}
        return false;
    }
}
