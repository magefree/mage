package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class Hellfire extends CardImpl {

    public Hellfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}{B}");

        // Destroy all nonblack creatures. Hellfire deals X plus 3 damage to you, where X is the number of creatures that died this way.
        this.getSpellAbility().addEffect(new HellfireEffect());
    }

    private Hellfire(final Hellfire card) {
        super(card);
    }

    @Override
    public Hellfire copy() {
        return new Hellfire(this);
    }
}

class HellfireEffect extends OneShotEffect {

    public HellfireEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy all nonblack creatures. {this} deals X plus 3 damage to you, where X is the number of creatures that died this way";
    }

    public HellfireEffect(final HellfireEffect effect) {
        super(effect);
    }

    @Override
    public HellfireEffect copy() {
        return new HellfireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int destroyedCreature = 0;
            for (Permanent creature : game.getState().getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES_NON_BLACK, controller.getId(), game)) {
                if (creature.destroy(source, game, false)
                        && game.getState().getZone(creature.getId()) == Zone.GRAVEYARD) { // If a commander is replaced to command zone, the creature does not die) {
                    destroyedCreature++;
                }
            }
            if (destroyedCreature > 0) {
                new DamageControllerEffect(destroyedCreature + 3).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
