
package mage.cards.p;

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
 * @author nantuko
 */
public final class Paraselene extends CardImpl {

    public Paraselene(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");


        // Destroy all enchantments. You gain 1 life for each enchantment destroyed this way.
        this.getSpellAbility().addEffect(new ParaseleneEffect());
    }

    private Paraselene(final Paraselene card) {
        super(card);
    }

    @Override
    public Paraselene copy() {
        return new Paraselene(this);
    }
}

class ParaseleneEffect extends OneShotEffect {

    public ParaseleneEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all enchantments. You gain 1 life for each enchantment destroyed this way";
    }

    public ParaseleneEffect(ParaseleneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_ENCHANTMENT, source.getControllerId(), source, game)) {
            if (permanent.destroy(source, game, false)) {
                count++;
            }
        }
        if (count > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(count, game, source);
            }
        }
        return true;
    }

    @Override
    public ParaseleneEffect copy() {
        return new ParaseleneEffect(this);
    }
}
