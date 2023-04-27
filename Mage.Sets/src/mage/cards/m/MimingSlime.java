
package mage.cards.m;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.OozeToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MimingSlime extends CardImpl {

    public MimingSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Create an X/X green Ooze creature token, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new MimingSlimeEffect());
    }

    private MimingSlime(final MimingSlime card) {
        super(card);
    }

    @Override
    public MimingSlime copy() {
        return new MimingSlime(this);
    }
}

class MimingSlimeEffect extends OneShotEffect {

    public MimingSlimeEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Create an X/X green Ooze creature token, where X is the greatest power among creatures you control";
    }

    public MimingSlimeEffect(final MimingSlimeEffect effect) {
        super(effect);
    }

    @Override
    public MimingSlimeEffect copy() {
        return new MimingSlimeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, player.getId(), game);
            int amount = 0;
            for (Permanent creature : creatures) {
                int power = creature.getPower().getValue();
                if (amount < power) {
                    amount = power;
                }
            }
            OozeToken oozeToken = new OozeToken();
            oozeToken.setPower(amount);
            oozeToken.setToughness(amount);
            oozeToken.putOntoBattlefield(1, game, source, source.getControllerId());
            return true;
        }
        return false;
    }
}
