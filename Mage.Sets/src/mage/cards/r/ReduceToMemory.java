package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Spirit32Token;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReduceToMemory extends CardImpl {

    public ReduceToMemory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        this.subtype.add(SubType.LESSON);

        // Exile target nonland permanent. Its controller creates a 3/2 red and white spirit creature token.
        this.getSpellAbility().addEffect(new ReduceToMemoryEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private ReduceToMemory(final ReduceToMemory card) {
        super(card);
    }

    @Override
    public ReduceToMemory copy() {
        return new ReduceToMemory(this);
    }
}

class ReduceToMemoryEffect extends OneShotEffect {

    ReduceToMemoryEffect() {
        super(Outcome.Benefit);
        staticText = "exile target nonland permanent. Its controller creates a 3/2 red and white Spirit creature token";
    }

    private ReduceToMemoryEffect(final ReduceToMemoryEffect effect) {
        super(effect);
    }

    @Override
    public ReduceToMemoryEffect copy() {
        return new ReduceToMemoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        player.moveCards(permanent, Zone.EXILED, source, game);
        new Spirit32Token().putOntoBattlefield(1, game, source, player.getId());
        return true;
    }
}
