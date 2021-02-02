
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ReturnToDust extends CardImpl {

    public ReturnToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Exile target artifact or enchantment. If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment.
        this.getSpellAbility().addEffect(new ReturnToDustEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(1, 2, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT, false));
    }

    private ReturnToDust(final ReturnToDust card) {
        super(card);
    }

    @Override
    public ReturnToDust copy() {
        return new ReturnToDust(this);
    }
}

class ReturnToDustEffect extends OneShotEffect {

    ReturnToDustEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Exile target artifact or enchantment. If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment";
    }

    private ReturnToDustEffect(final ReturnToDustEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new ReturnToDustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int targetsCleared = 0;
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            if (targetsCleared == 0) {
                player.moveCards(permanent, Zone.EXILED, source, game);
                targetsCleared++;
            } else if (targetsCleared == 1) {
                if (game.isActivePlayer(source.getControllerId()) && game.isMainPhase()
                        && player.chooseUse(outcome, "Exile another permanent?", source, game)) {
                    player.moveCards(permanent, Zone.EXILED, source, game);
                    targetsCleared++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return true;
    }
}