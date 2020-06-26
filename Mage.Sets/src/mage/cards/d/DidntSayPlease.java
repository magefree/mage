package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DidntSayPlease extends CardImpl {

    public DidntSayPlease(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. Its controller puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new DidntSayPleaseEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DidntSayPlease(final DidntSayPlease card) {
        super(card);
    }

    @Override
    public DidntSayPlease copy() {
        return new DidntSayPlease(this);
    }
}

class DidntSayPleaseEffect extends OneShotEffect {

    private static final Effect effect = new CounterTargetEffect();

    DidntSayPleaseEffect() {
        super(Outcome.Benefit);
        staticText = "Counter target spell. Its controller mills three cards.";
    }

    private DidntSayPleaseEffect(final DidntSayPleaseEffect effect) {
        super(effect);
    }

    @Override
    public DidntSayPleaseEffect copy() {
        return new DidntSayPleaseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getControllerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        player.millCards(3, source, game);
        return effect.apply(game, source);
    }
}
