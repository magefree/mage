package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.StackObjectCopyApplier;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class DonalHeraldOfWings extends CardImpl {

    private static final FilterSpell filterSpell = new FilterSpell("a nonlegendary creature spell with flying");
    static {
        filterSpell.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
        filterSpell.add(CardType.CREATURE.getPredicate());
        filterSpell.add(TargetController.YOU.getControllerPredicate());
    }

    public DonalHeraldOfWings(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);

        this.addSubType(SubType.HUMAN);
        this.addSubType(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a nonlegendary creature spell with flying, you may copy it,
        // except the copy is a 1/1 Spirit in addition to its other types.
        // Do this only once each turn. (The copy becomes a token.)
        this.addAbility(new SpellCastControllerTriggeredAbility(new DonalHeraldOfWingsEffect(), filterSpell, true, true));
    }

    private DonalHeraldOfWings(final DonalHeraldOfWings card) { super(card); }

    @Override
    public DonalHeraldOfWings copy() { return new DonalHeraldOfWings(this); }
}

class DonalHeraldOfWingsEffect extends OneShotEffect {

    DonalHeraldOfWingsEffect() {
        super(Outcome.Copy);
        staticText = "you may copy it, except the copy is a 1/1 Spirit in addition to its other types.";
    }

    private DonalHeraldOfWingsEffect(final DonalHeraldOfWingsEffect effect) { super(effect); }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false; }

        // TODO Make sure it only gets done once per turn

        // Get the card that was cast
        if (this.getTargetPointer() == null) { return false; }
        Spell originalSpell = game.getStack().getSpell(((FixedTarget) this.getTargetPointer()).getTarget());

        // Create a token copy
        // TODO: Copy made is a token, but there's no indication of it on the card
        originalSpell.createCopyOnStack(game, source, controller.getId(), false, 1, DonalHeraldOfWingsApplier.instance);

        return true;
    }

    @Override
    public Effect copy() { return new DonalHeraldOfWingsEffect(this); }
}

enum DonalHeraldOfWingsApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        copiedSpell.addSubType(SubType.SPIRIT);
        // TODO: These two calls don't work
        copiedSpell.getPower().setValue(1);
        copiedSpell.getToughness().setValue(1);
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType(int copyNumber) { return null; }
}
