package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class SacredRites extends CardImpl {

    public SacredRites(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.
        this.getSpellAbility().addEffect(new SacredRitesEffect());
    }

    private SacredRites(final SacredRites card) {
        super(card);
    }

    @Override
    public SacredRites copy() {
        return new SacredRites(this);
    }
}

class SacredRitesEffect extends OneShotEffect {

    SacredRitesEffect() {
        super(Outcome.AIDontUseIt);
        this.staticText = "Discard any number of cards. Creatures you control get +0/+1 until end of turn for each card discarded this way.";
    }

    private SacredRitesEffect(final SacredRitesEffect effect) {
        super(effect);
    }

    @Override
    public SacredRitesEffect copy() {
        return new SacredRitesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int numDiscarded = controller.discard(0, Integer.MAX_VALUE, false, source, game).size();
        if (numDiscarded > 0) {
            game.addEffect(new BoostControlledEffect(0, numDiscarded, Duration.EndOfTurn), source);
        }
        return true;
    }
}
