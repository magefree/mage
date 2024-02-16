package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiredUltimatum extends CardImpl {

    public InspiredUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}{U}{R}{R}{R}{W}{W}");

        // Target player gains 5 life. Inspired Ultimatum deals 5 damage to any target. You draw five cards.
        this.getSpellAbility().addEffect(new InspiredUltimatumEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private InspiredUltimatum(final InspiredUltimatum card) {
        super(card);
    }

    @Override
    public InspiredUltimatum copy() {
        return new InspiredUltimatum(this);
    }
}

class InspiredUltimatumEffect extends OneShotEffect {

    InspiredUltimatumEffect() {
        super(Outcome.Benefit);
        staticText = "Target player gains 5 life, {this} deals 5 damage to any target, then you draw five cards.";
    }

    private InspiredUltimatumEffect(final InspiredUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public InspiredUltimatumEffect copy() {
        return new InspiredUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (player != null) {
            player.gainLife(5, game, source);
        }
        game.damagePlayerOrPermanent(
                source.getTargets().get(1).getFirstTarget(), 5,
                source.getSourceId(), source, game, false, true
        );
        player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(5, source, game);
        }
        return true;
    }
}