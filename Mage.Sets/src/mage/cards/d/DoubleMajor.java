package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;
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
public final class DoubleMajor extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("creature spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public DoubleMajor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}{U}");

        // Copy target creature spell you control, except it isn't legendary if the spell is legendary.
        this.getSpellAbility().addEffect(new DoubleMajorEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
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

enum DoubleMajorApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject stackObject, Game game) {
        stackObject.getSuperType().remove(SuperType.LEGENDARY);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}
