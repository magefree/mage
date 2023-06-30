package mage.game.command.emblems;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.stack.Spell;

/**
 * @author TheElk801
 */
public final class JayaFieryNegotiatorEmblem extends Emblem {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("a red instant or sorcery spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    // −8: You get an emblem with "Whenever you cast a red instant or sorcery spell, copy it twice. You may choose new targets for the copies."
    public JayaFieryNegotiatorEmblem() {
        super("Emblem Jaya");
        this.getAbilities().add(new SpellCastControllerTriggeredAbility(
                new JayaFieryNegotiatorEmblemEffect(), filter, false
        ));
    }

    private JayaFieryNegotiatorEmblem(final JayaFieryNegotiatorEmblem card) {
        super(card);
    }

    @Override
    public JayaFieryNegotiatorEmblem copy() {
        return new JayaFieryNegotiatorEmblem(this);
    }
}

class JayaFieryNegotiatorEmblemEffect extends OneShotEffect {

    JayaFieryNegotiatorEmblemEffect() {
        super(Outcome.Benefit);
        staticText = "copy it twice. You may choose new targets for the copies";
    }

    private JayaFieryNegotiatorEmblemEffect(final JayaFieryNegotiatorEmblemEffect effect) {
        super(effect);
    }

    @Override
    public JayaFieryNegotiatorEmblemEffect copy() {
        return new JayaFieryNegotiatorEmblemEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(game, source, source.getControllerId(), true, 2);
        return true;
    }
}
