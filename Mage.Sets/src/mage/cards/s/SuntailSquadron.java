package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ConjureCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuntailSquadron extends CardImpl {

    public SuntailSquadron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Conjure a card named Suntail Hawk into your hand. If you have fewer than 7 cards in hand, repeat this process.
        this.getSpellAbility().addEffect(new SuntailSquadronEffect());
    }

    private SuntailSquadron(final SuntailSquadron card) {
        super(card);
    }

    @Override
    public SuntailSquadron copy() {
        return new SuntailSquadron(this);
    }
}

class SuntailSquadronEffect extends OneShotEffect {

    SuntailSquadronEffect() {
        super(Outcome.Benefit);
        staticText = "conjure a card named Suntail Hawk into your hand. " +
                "If you have fewer than 7 cards in hand, repeat this process";
    }

    private SuntailSquadronEffect(final SuntailSquadronEffect effect) {
        super(effect);
    }

    @Override
    public SuntailSquadronEffect copy() {
        return new SuntailSquadronEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Effect effect = new ConjureCardEffect("Suntail Hawk");
        do {
            effect.apply(game, source);
        } while (player.getHand().size() < 7);
        return true;
    }
}
