

package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CipherEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */


public final class HiddenStrings extends CardImpl {

    public HiddenStrings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{U}");


        // You may tap or untap target permanent, then you may tap or untap another target permanent
        this.getSpellAbility().addEffect(new HiddenStringsEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, new FilterPermanent(), false));

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    public HiddenStrings(final HiddenStrings card) {
        super(card);
    }

    @Override
    public HiddenStrings copy() {
        return new HiddenStrings(this);
    }

}

class HiddenStringsEffect extends OneShotEffect {

    public HiddenStringsEffect() {
        super(Outcome.Tap);
        this.staticText = "You may tap or untap target permanent, then you may tap or untap another target permanent";
    }

    public HiddenStringsEffect(final HiddenStringsEffect effect) {
        super(effect);
    }

    @Override
    public HiddenStringsEffect copy() {
        return new HiddenStringsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                for (UUID targetId : source.getTargets().get(0).getTargets()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if (permanent.isTapped()) {
                            if (player.chooseUse(Outcome.Untap, new StringBuilder("Untap ").append(permanent.getName()).append('?').toString(), source, game)) {
                                permanent.untap(game);
                            }
                        } else {
                            if (player.chooseUse(Outcome.Tap, new StringBuilder("Tap ").append(permanent.getName()).append('?').toString(), source, game)) {
                                permanent.tap(game);
                            }
                        }
                    }
                }
                return true;
            }
            return false;
    }
}
