
package mage.cards.w;

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
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class WretchedBanquet extends CardImpl {

    public WretchedBanquet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Destroy target creature if it has the least power or is tied for least power among creatures on the battlefield.
        this.getSpellAbility().addEffect(new WretchedBanquetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WretchedBanquet(final WretchedBanquet card) {
        super(card);
    }

    @Override
    public WretchedBanquet copy() {
        return new WretchedBanquet(this);
    }
}

class WretchedBanquetEffect extends OneShotEffect {

    public WretchedBanquetEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature if it has the least power or is tied for least power among creatures on the battlefield";
    }

    public WretchedBanquetEffect(final WretchedBanquetEffect effect) {
        super(effect);
    }

    @Override
    public WretchedBanquetEffect copy() {
        return new WretchedBanquetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature == null) {
            return false;
        }
        List<Permanent> creatures = game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), source, game);

        int minPower = targetCreature.getPower().getValue() + 1;
        for (Permanent creature : creatures) {
            if (minPower > creature.getPower().getValue()) {
                minPower = creature.getPower().getValue();
            }
        }

        if (targetCreature.getPower().getValue() <= minPower) {
            targetCreature.destroy(source, game, false);
            return true;
        }
        return false;
    }
}
