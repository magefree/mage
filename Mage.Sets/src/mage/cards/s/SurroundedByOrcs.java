package mage.cards.s;

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
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SurroundedByOrcs extends CardImpl {

    public SurroundedByOrcs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Amass Orcs 3, then target player mills X cards, where X is the amassed Army's power.
        this.getSpellAbility().addEffect(new SurroundedByOrcsEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private SurroundedByOrcs(final SurroundedByOrcs card) {
        super(card);
    }

    @Override
    public SurroundedByOrcs copy() {
        return new SurroundedByOrcs(this);
    }
}

class SurroundedByOrcsEffect extends OneShotEffect {

    SurroundedByOrcsEffect() {
        super(Outcome.Benefit);
        staticText = "amass Orcs 3, then target player mills X cards, where X is the amassed Army's power";
    }

    private SurroundedByOrcsEffect(final SurroundedByOrcsEffect effect) {
        super(effect);
    }

    @Override
    public SurroundedByOrcsEffect copy() {
        return new SurroundedByOrcsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = AmassEffect.doAmass(3, SubType.ORC, game, source);
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (permanent == null || player == null) {
            return false;
        }
        game.getState().processAction(game);
        player.millCards(permanent.getPower().getValue(), source, game);
        return true;
    }
}
