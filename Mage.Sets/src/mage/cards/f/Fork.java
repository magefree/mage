package mage.cards.f;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Fork extends CardImpl {

    public Fork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Copy target instant or sorcery spell, except that the copy is red. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new ForkEffect());
        this.getSpellAbility().addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_INSTANT_OR_SORCERY));
    }

    private Fork(final Fork card) {
        super(card);
    }

    @Override
    public Fork copy() {
        return new Fork(this);
    }
}

class ForkEffect extends OneShotEffect {

    ForkEffect() {
        super(Outcome.Copy);
        staticText = "copy target instant or sorcery spell, except that the copy is red. " +
                "You may choose new targets for the copy";
    }

    private ForkEffect(final ForkEffect effect) {
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
                true, 1, ForkApplier.instance
        );
        return true;
    }

    @Override
    public ForkEffect copy() {
        return new ForkEffect(this);
    }
}

enum ForkApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
        stackObject.getColor(game).setColor(ObjectColor.RED);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}
