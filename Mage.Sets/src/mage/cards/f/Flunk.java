package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
public final class Flunk extends CardImpl {

    public Flunk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets -X/-X until end of turn, where X is 7 minus the number of cards in that creature's controller's hand.
        this.getSpellAbility().addEffect(new FlunkEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Flunk(final Flunk card) {
        super(card);
    }

    @Override
    public Flunk copy() {
        return new Flunk(this);
    }
}

class FlunkEffect extends OneShotEffect {

    FlunkEffect() {
        super(Outcome.Benefit);
        staticText = "target creature gets -X/-X until end of turn, where X is 7 " +
                "minus the number of cards in that creature's controller's hand";
    }

    private FlunkEffect(final FlunkEffect effect) {
        super(effect);
    }

    @Override
    public FlunkEffect copy() {
        return new FlunkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getControllerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        int xValue = Math.max(7 - player.getHand().size(), 0);
        game.addEffect(new BoostTargetEffect(-xValue, -xValue), source);
        return true;
    }
}
