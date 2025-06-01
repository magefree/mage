package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author nigelzor
 */
public final class DispersalShield extends CardImpl {

    public DispersalShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell if its converted mana cost is less than or equal to the highest converted mana cost among permanents you control.
        this.getSpellAbility().addEffect(new DispersalShieldEffect());
        this.getSpellAbility().addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.getHint());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private DispersalShield(final DispersalShield card) {
        super(card);
    }

    @Override
    public DispersalShield copy() {
        return new DispersalShield(this);
    }
}

class DispersalShieldEffect extends OneShotEffect {

    DispersalShieldEffect() {
        super(Outcome.Detriment);
        staticText = "Counter target spell if its mana value is less than or equal to the greatest mana value among permanents you control";
    }

    private DispersalShieldEffect(final DispersalShieldEffect effect) {
        super(effect);
    }

    @Override
    public DispersalShieldEffect copy() {
        return new DispersalShieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_PERMANENTS.calculate(game, source, this);
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null && spell.getManaValue() <= amount) {
            return game.getStack().counter(source.getFirstTarget(), source, game);
        }
        return false;
    }
}
