package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Boing extends CardImpl {

    public Boing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target creature to its owner's hand, then roll a six-sided die. If the result is 3 or less, scry a number of cards equal to the result.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new BoingEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Boing(final Boing card) {
        super(card);
    }

    @Override
    public Boing copy() {
        return new Boing(this);
    }
}

class BoingEffect extends OneShotEffect {

    BoingEffect() {
        super(Outcome.Benefit);
        staticText = ", then roll a six-sided die. If the result is 3 or less, " +
                "scry a number of cards equal to the result";
    }

    private BoingEffect(final BoingEffect effect) {
        super(effect);
    }

    @Override
    public BoingEffect copy() {
        return new BoingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int result = player.rollDice(outcome, source, game, 6);
        if (result <= 3) {
            player.scry(result, source, game);
        }
        return true;
    }
}
