package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;

/**
 * @author Cguy7777
 */
public final class Electrosiphon extends CardImpl {

    public Electrosiphon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}{R}");

        // Counter target spell. You get an amount of {E} equal to its mana value.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ElectrosiphonEffect());
    }

    private Electrosiphon(final Electrosiphon card) {
        super(card);
    }

    @Override
    public Electrosiphon copy() {
        return new Electrosiphon(this);
    }
}

class ElectrosiphonEffect extends OneShotEffect {

    ElectrosiphonEffect() {
        super(Outcome.Detriment);
        this.staticText = "Counter target spell. You get an amount of {E} <i>(energy counters)</i> equal to its mana value";
    }

    private ElectrosiphonEffect(final ElectrosiphonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            return false;
        }

        game.getStack().counter(spell.getId(), source, game);
        new GetEnergyCountersControllerEffect(spell.getManaValue()).apply(game, source);
        return true;
    }

    @Override
    public ElectrosiphonEffect copy() {
        return new ElectrosiphonEffect(this);
    }
}
