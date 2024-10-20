package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DontMakeASound extends CardImpl {

    public DontMakeASound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell unless its controller pays {2}. If they do, surveil 2.
        this.getSpellAbility().addEffect(new DontMakeASoundEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DontMakeASound(final DontMakeASound card) {
        super(card);
    }

    @Override
    public DontMakeASound copy() {
        return new DontMakeASound(this);
    }
}

class DontMakeASoundEffect extends OneShotEffect {

    DontMakeASoundEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell unless its controller pays {2}. If they do, surveil 2";
    }

    private DontMakeASoundEffect(final DontMakeASoundEffect effect) {
        super(effect);
    }

    @Override
    public DontMakeASoundEffect copy() {
        return new DontMakeASoundEffect(this);
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
        Optional.ofNullable(game.getPlayer(source.getControllerId()))
                .ifPresent(p -> p.surveil(2, source, game));
        return true;
    }
}
