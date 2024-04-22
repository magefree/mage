package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ConsumingAshes extends CardImpl {

    public ConsumingAshes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Exile target creature. If it had mana value 3 or less, surveil 2.
        this.getSpellAbility().addEffect(new ConsumingAshesEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ConsumingAshes(final ConsumingAshes card) {
        super(card);
    }

    @Override
    public ConsumingAshes copy() {
        return new ConsumingAshes(this);
    }
}

class ConsumingAshesEffect extends OneShotEffect {

    ConsumingAshesEffect() {
        super(Outcome.Exile);
        staticText = "Exile target creature. If it had mana value 3 or less, surveil 2.";
    }

    private ConsumingAshesEffect(final ConsumingAshesEffect effect) {
        super(effect);
    }

    @Override
    public ConsumingAshesEffect copy() {
        return new ConsumingAshesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (controller == null || permanent == null) {
            return false;
        }
        permanent.moveToExile(null, "", source, game);
        if (permanent.getManaValue() <= 3) {
            controller.surveil(2, source, game);
        }
        return true;
    }

}