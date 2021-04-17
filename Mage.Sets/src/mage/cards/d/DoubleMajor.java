package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.TargetSpell;
import mage.util.functions.SpellCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoubleMajor extends CardImpl {

    public DoubleMajor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        this.getSpellAbility().addEffect(new DoubleMajorEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_CREATURE));
    }

    private DoubleMajor(final DoubleMajor card) {
        super(card);
    }

    @Override
    public DoubleMajor copy() {
        return new DoubleMajor(this);
    }
}

class DoubleMajorEffect extends OneShotEffect {

    DoubleMajorEffect() {
        super(Outcome.Copy);
        staticText = "copy target creature spell you control, except it isn't legendary if the spell is legendary";
    }

    private DoubleMajorEffect(final DoubleMajorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getFirstTarget());
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(),
                false, 1, DoubleMajorApplier.instance
        );
        return true;
    }

    @Override
    public DoubleMajorEffect copy() {
        return new DoubleMajorEffect(this);
    }
}

enum DoubleMajorApplier implements SpellCopyApplier {
    instance;

    @Override
    public void modifySpell(Spell spell, Game game) {
        spell.getSuperType().remove(SuperType.LEGENDARY);
    }

    @Override
    public MageObjectReferencePredicate getNextPredicate() {
        return null;
    }
}
