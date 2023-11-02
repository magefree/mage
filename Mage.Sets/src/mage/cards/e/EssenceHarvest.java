package mage.cards.e;

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
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class EssenceHarvest extends CardImpl {

    public EssenceHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}");

        // Target player loses X life and you gain X life, where X is the greatest power among creatures you control.
        this.getSpellAbility().addEffect(new EssenceHarvestEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EssenceHarvest(final EssenceHarvest card) {
        super(card);
    }

    @Override
    public EssenceHarvest copy() {
        return new EssenceHarvest(this);
    }
}

class EssenceHarvestEffect extends OneShotEffect {

    public EssenceHarvestEffect() {
        super(Outcome.Damage);
        this.staticText = "Target player loses X life and you gain X life, where X is the greatest power among creatures you control";
    }

    private EssenceHarvestEffect(final EssenceHarvestEffect effect) {
        super(effect);
    }

    @Override
    public EssenceHarvestEffect copy() {
        return new EssenceHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null
                && targetPlayer != null) {
            List<Permanent> creatures = game.getBattlefield().getAllActivePermanents(
                    StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game);
            int amount = 0;
            for (Permanent creature : creatures) {
                int power = creature.getPower().getValue();
                if (amount < power) {
                    amount = power;
                }
            }

            if (amount > 0) {
                targetPlayer.loseLife(amount, game, source, false);
                controller.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}
