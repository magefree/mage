package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author noxx
 */
public final class BonfireOfTheDamned extends CardImpl {

    public BonfireOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Bonfire of the Damned deals X damage to target player and each creature they control.
        this.getSpellAbility().addEffect(new BonfireOfTheDamnedEffect());
        this.getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());

        // Miracle {X}{R}
        this.addAbility(new MiracleAbility(this, new ManaCostsImpl<>("{X}{R}")));
    }

    private BonfireOfTheDamned(final BonfireOfTheDamned card) {
        super(card);
    }

    @Override
    public BonfireOfTheDamned copy() {
        return new BonfireOfTheDamned(this);
    }
}

class BonfireOfTheDamnedEffect extends OneShotEffect {

    BonfireOfTheDamnedEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to target player or planeswalker " +
                "and each creature that player or that planeswalker's controller controls";
    }

    private BonfireOfTheDamnedEffect(final BonfireOfTheDamnedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = source.getManaCostsToPay().getX();
        if (damage < 1) {
            return false;
        }
        game.damagePlayerOrPlaneswalker(source.getFirstTarget(), damage, source.getSourceId(), source, game, false, true);
        Player player = game.getPlayerOrPlaneswalkerController(source.getFirstTarget());
        if (player == null) {
            return true;
        }
        for (Permanent perm : game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game
        )) {
            perm.damage(damage, source.getSourceId(), source, game);
        }
        return true;
    }

    @Override
    public BonfireOfTheDamnedEffect copy() {
        return new BonfireOfTheDamnedEffect(this);
    }

}
