
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AssassinsStrike extends CardImpl {

    public AssassinsStrike(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{B}{B}");


        // Destroy target creature. Its controller discards a card.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new AssassinsStrikeEffect());
    }

    private AssassinsStrike(final AssassinsStrike card) {
        super(card);
    }

    @Override
    public AssassinsStrike copy() {
        return new AssassinsStrike(this);
    }
}

class AssassinsStrikeEffect extends OneShotEffect {

    public AssassinsStrikeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Its controller discards a card";
    }

    private AssassinsStrikeEffect(final AssassinsStrikeEffect effect) {
        super(effect);
    }

    @Override
    public AssassinsStrikeEffect copy() {
        return new AssassinsStrikeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(source.getFirstTarget(), Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.discard(1, false, false, source, game);
                return true;
            }
        }
        return false;
    }
}