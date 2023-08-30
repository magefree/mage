package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class EnterTheGodEternals extends CardImpl {

    public EnterTheGodEternals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}{B}");

        // Enter the God-Eternals deals 4 damage to target creature and you gain life equal to the damage dealt this way. Target player puts the top four cards of their library into their graveyard. Amass 4.
        this.getSpellAbility().addEffect(new EnterTheGodEternalsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EnterTheGodEternals(final EnterTheGodEternals card) {
        super(card);
    }

    @Override
    public EnterTheGodEternals copy() {
        return new EnterTheGodEternals(this);
    }
}

class EnterTheGodEternalsEffect extends OneShotEffect {

    EnterTheGodEternalsEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 4 damage to target creature and you gain life equal to the damage dealt this way. "
                + "Target player mills four cards. Amass 4.";
    }

    private EnterTheGodEternalsEffect(final EnterTheGodEternalsEffect effect) {
        super(effect);
    }

    @Override
    public EnterTheGodEternalsEffect copy() {
        return new EnterTheGodEternalsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (Target target : source.getTargets()) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    controller.gainLife(permanent.damage(4, source.getSourceId(), source, game), game, source);
                    continue;
                }
                Player player = game.getPlayer(targetId);
                if (player != null) {
                    player.millCards(4, source, game);
                }
            }
        }
        return new AmassEffect(4, SubType.ZOMBIE).apply(game, source);
    }
}
