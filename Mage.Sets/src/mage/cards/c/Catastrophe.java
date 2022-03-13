
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Catastrophe extends CardImpl {

    public Catastrophe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Destroy all lands or all creatures. Creatures destroyed this way can't be regenerated.
        this.getSpellAbility().addEffect(new CatastropheEffect());
    }

    private Catastrophe(final Catastrophe card) {
        super(card);
    }

    @Override
    public Catastrophe copy() {
        return new Catastrophe(this);
    }
}

class CatastropheEffect extends OneShotEffect {

    public CatastropheEffect() {
        super(Outcome.Detriment);
        this.staticText = "Destroy all lands or all creatures. Creatures destroyed this way can't be regenerated";
    }

    public CatastropheEffect(final CatastropheEffect effect) {
        super(effect);
    }

    @Override
    public CatastropheEffect copy() {
        return new CatastropheEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseUse(outcome, "Destroy all lands? (otherwise all creatures are destroyed)", source, game)) {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterLandPermanent(), controller.getId(), source, game)) {
                    permanent.destroy(source, game, permanent.isCreature(game));
                }
            } else {
                for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), source, game)) {
                    permanent.destroy(source, game, true);
                }
            }
            return true;
        }
        return false;
    }
}
