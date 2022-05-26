package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EldritchPact extends CardImpl {

    public EldritchPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{B}");

        // Target player draws X cards and loses X life, where X is the number of cards in their graveyard.
        this.getSpellAbility().addEffect(new EldritchPactEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private EldritchPact(final EldritchPact card) {
        super(card);
    }

    @Override
    public EldritchPact copy() {
        return new EldritchPact(this);
    }
}

class EldritchPactEffect extends OneShotEffect {

    EldritchPactEffect() {
        super(Outcome.Benefit);
        staticText = "target player draws X cards and loses X life, where X is the number of cards in their graveyard";
    }

    private EldritchPactEffect(final EldritchPactEffect effect) {
        super(effect);
    }

    @Override
    public EldritchPactEffect copy() {
        return new EldritchPactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = player.getGraveyard().size();
        if (count < 1) {
            return false;
        }
        player.drawCards(count, source, game);
        player.loseLife(count, game, source, false);
        return true;
    }
}
