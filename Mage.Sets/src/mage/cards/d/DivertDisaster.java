package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.LanderToken;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DivertDisaster extends CardImpl {

    public DivertDisaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2}. If they do, you create a Lander token.
        this.getSpellAbility().addEffect(new DivertDisasterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DivertDisaster(final DivertDisaster card) {
        super(card);
    }

    @Override
    public DivertDisaster copy() {
        return new DivertDisaster(this);
    }
}

class DivertDisasterEffect extends OneShotEffect {

    DivertDisasterEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell unless its controller pays {2}. If they do, you create a Lander token.";
    }

    private DivertDisasterEffect(final DivertDisasterEffect effect) {
        super(effect);
    }

    @Override
    public DivertDisasterEffect copy() {
        return new DivertDisasterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        Player player = game.getPlayer(spell.getControllerId());
        if (player == null) {
            return game.getStack().counter(spell.getId(), source, game);
        }
        Cost cost = new GenericManaCost(2);
        if (!cost.canPay(source, source, player.getId(), game)
                || !player.chooseUse(outcome, "Pay {2}?", source, game)
                || !cost.pay(source, game, source, player.getId(), false)) {
            return game.getStack().counter(spell.getId(), source, game);
        }
        new CreateTokenEffect(new LanderToken()).apply(game, source);
        return true;
    }
}
