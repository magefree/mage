
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author noxx
 */
public final class BonfireOfTheDamned extends CardImpl {

    public BonfireOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Bonfire of the Damned deals X damage to target player and each creature he or she controls.
        this.getSpellAbility().addEffect(new BonfireOfTheDamnedEffect());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

        // Miracle {X}{R}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl("{X}{R}")));
    }

    public BonfireOfTheDamned(final BonfireOfTheDamned card) {
        super(card);
    }

    @Override
    public BonfireOfTheDamned copy() {
        return new BonfireOfTheDamned(this);
    }
}

class BonfireOfTheDamnedEffect extends OneShotEffect {

    private static FilterPermanent filter = new FilterCreaturePermanent();

    public BonfireOfTheDamnedEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target player or planeswalker and each creature that player or that planeswalker's controller controls";
    }

    public BonfireOfTheDamnedEffect(final BonfireOfTheDamnedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player != null) {
            int damage = source.getManaCostsToPay().getX();
            if (damage > 0) {
                player.damage(damage, source.getSourceId(), game, false, true);
                for (Permanent perm : game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                    perm.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public BonfireOfTheDamnedEffect copy() {
        return new BonfireOfTheDamnedEffect(this);
    }

}
