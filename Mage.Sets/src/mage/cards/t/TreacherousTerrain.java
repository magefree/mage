package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class TreacherousTerrain extends CardImpl {

    public TreacherousTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{R}{G}");

        // Treacherous Terrain deals damage to each opponent requal to the number of lands that player controls.
        this.getSpellAbility().addEffect(new TreacherousTerrainEffect());

        // Basic landcycling {2}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl("{2}")));
    }

    private TreacherousTerrain(final TreacherousTerrain card) {
        super(card);
    }

    @Override
    public TreacherousTerrain copy() {
        return new TreacherousTerrain(this);
    }
}

class TreacherousTerrainEffect extends OneShotEffect {

    public TreacherousTerrainEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to each opponent equal to the number of lands that player controls";
    }

    public TreacherousTerrainEffect(final TreacherousTerrainEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source, game);
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int amount = 0;
                for (Permanent permanent : permanents) {
                    if (permanent.isControlledBy(playerId)) {
                        amount++;
                    }
                }
                if (amount > 0) {
                    player.damage(amount, source.getSourceId(), source, game);
                }
            }
        }
        return true;
    }

    @Override
    public TreacherousTerrainEffect copy() {
        return new TreacherousTerrainEffect(this);
    }
}
