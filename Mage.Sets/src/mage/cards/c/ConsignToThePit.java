package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConsignToThePit extends CardImpl {

    public ConsignToThePit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{B}");

        // Destroy target creature. Consign to the Pit deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new ConsignToThePitEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ConsignToThePit(final ConsignToThePit card) {
        super(card);
    }

    @Override
    public ConsignToThePit copy() {
        return new ConsignToThePit(this);
    }
}

class ConsignToThePitEffect extends OneShotEffect {

    ConsignToThePitEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target creature. {this} deals 2 damage to that creature's controller.";
    }

    private ConsignToThePitEffect(final ConsignToThePitEffect effect) {
        super(effect);
    }

    @Override
    public ConsignToThePitEffect copy() {
        return new ConsignToThePitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        permanent.destroy(source, game, false);
        player.damage(2, source.getSourceId(), source, game);
        return true;
    }
}