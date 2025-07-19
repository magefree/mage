package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Unravel extends CardImpl {

    public Unravel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. If the amount of mana spent to cast that spell was less than its mana value, you draw a card.
        this.getSpellAbility().addEffect(new UnravelEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Unravel(final Unravel card) {
        super(card);
    }

    @Override
    public Unravel copy() {
        return new Unravel(this);
    }
}

class UnravelEffect extends OneShotEffect {

    UnravelEffect() {
        super(Outcome.Benefit);
        staticText = "counter target spell. If the amount of mana spent to cast that spell was less than its mana value, you draw a card";
    }

    private UnravelEffect(final UnravelEffect effect) {
        super(effect);
    }

    @Override
    public UnravelEffect copy() {
        return new UnravelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }
        boolean flag = spell
                .getStackAbility()
                .getManaCostsToPay()
                .getUsedManaToPay()
                .count() < spell.getManaValue();
        game.getStack().counter(spell.getId(), source, game);
        if (!flag) {
            return true;
        }
        Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .ifPresent(player -> player.drawCards(1, source, game));
        return true;
    }
}
