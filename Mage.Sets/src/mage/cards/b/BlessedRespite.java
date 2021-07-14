package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author weirddan455
 */
public final class BlessedRespite extends CardImpl {

    public BlessedRespite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target player shuffles their graveyard into their library. Prevent all combat damage that would be dealt this turn.
        this.getSpellAbility().addEffect(new BlessedRespiteEffect());
        this.getSpellAbility().addEffect(new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private BlessedRespite(final BlessedRespite card) {
        super(card);
    }

    @Override
    public BlessedRespite copy() {
        return new BlessedRespite(this);
    }
}

class BlessedRespiteEffect extends OneShotEffect {

    public BlessedRespiteEffect() {
        super(Outcome.Detriment);
        this.staticText = "Target player shuffles their graveyard into their library";
    }

    private BlessedRespiteEffect(final BlessedRespiteEffect effect) {
        super(effect);
    }

    @Override
    public BlessedRespiteEffect copy() {
        return new BlessedRespiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.moveCards(targetPlayer.getGraveyard(), Zone.LIBRARY, source, game);
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}
