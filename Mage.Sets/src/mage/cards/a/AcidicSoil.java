package mage.cards.a;

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

import java.util.List;
import java.util.UUID;

/**
 * @author Backfir3
 */
public final class AcidicSoil extends CardImpl {

    public AcidicSoil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");


        //Acidic Soil deals damage to each player equal to the number of lands they control.
        this.getSpellAbility().addEffect(new AcidicSoilEffect());
    }

    private AcidicSoil(final AcidicSoil card) {
        super(card);
    }

    @Override
    public AcidicSoil copy() {
        return new AcidicSoil(this);
    }
}

class AcidicSoilEffect extends OneShotEffect {

    AcidicSoilEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to each player equal to the number of lands they control";
    }

    AcidicSoilEffect(final AcidicSoilEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), source, game);
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
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
    public AcidicSoilEffect copy() {
        return new AcidicSoilEffect(this);
    }
}
