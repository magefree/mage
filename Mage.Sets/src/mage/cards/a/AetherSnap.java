package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.Counter;
import mage.counters.Counters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AetherSnap extends CardImpl {

    public AetherSnap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Remove all counters from all permanents and exile all tokens.
        this.getSpellAbility().addEffect(new AetherSnapEffect());
    }

    private AetherSnap(final AetherSnap card) {
        super(card);
    }

    @Override
    public AetherSnap copy() {
        return new AetherSnap(this);
    }
}

class AetherSnapEffect extends OneShotEffect {

    public AetherSnapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Remove all counters from all permanents and exile all tokens";
    }

    private AetherSnapEffect(final AetherSnapEffect effect) {
        super(effect);
    }

    @Override
    public AetherSnapEffect copy() {
        return new AetherSnapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards tokens = new CardsImpl();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getSourceId(), game)) {
            if (permanent instanceof PermanentToken) {
                tokens.add(permanent);
            }
            if (permanent.getCounters(game).isEmpty()) {
                continue;
            }
            Counters counters = permanent.getCounters(game).copy();
            for (Counter counter : counters.values()) {
                permanent.removeCounters(counter, source, game);
            }
        }
        controller.moveCards(tokens, Zone.EXILED, source, game);
        return true;
    }
}
