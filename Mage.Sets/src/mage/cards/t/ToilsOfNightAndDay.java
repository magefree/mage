
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ToilsOfNightAndDay extends CardImpl {

    public ToilsOfNightAndDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{U}");
        this.subtype.add(SubType.ARCANE);

        // You may tap or untap target permanent, then you may tap or untap another target permanent.
        this.getSpellAbility().addEffect(new ToilsOfNightAndDayEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, new FilterPermanent(), false));
    }

    private ToilsOfNightAndDay(final ToilsOfNightAndDay card) {
        super(card);
    }

    @Override
    public ToilsOfNightAndDay copy() {
        return new ToilsOfNightAndDay(this);
    }


    private static class ToilsOfNightAndDayEffect extends OneShotEffect {

        public ToilsOfNightAndDayEffect() {
            super(Outcome.Tap);
            this.staticText = "You may tap or untap target permanent, then you may tap or untap another target permanent";
        }

        private ToilsOfNightAndDayEffect(final ToilsOfNightAndDayEffect effect) {
            super(effect);
        }

        @Override
        public ToilsOfNightAndDayEffect copy() {
            return new ToilsOfNightAndDayEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                for (UUID targetId : source.getTargets().get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if (player.chooseUse(Outcome.Tap, "Tap " + permanent.getName() + '?', source, game)) {
                            permanent.tap(source, game);
                        } else if (player.chooseUse(Outcome.Untap, "Untap " + permanent.getName() + '?', source, game)) {
                            permanent.untap(game);
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
}