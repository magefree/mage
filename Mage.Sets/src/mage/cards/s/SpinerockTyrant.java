package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WitherAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.targetpointer.FixedTarget;
import mage.util.functions.StackObjectCopyApplier;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpinerockTyrant extends CardImpl {

    private static final FilterSpell filter = new FilterInstantOrSorcerySpell("an instant or sorcery spell with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public SpinerockTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Wither
        this.addAbility(WitherAbility.getInstance());

        // Whenever you cast an instant or sorcery spell with a single target, you may copy it. If you do, those spells gain wither. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(new SpinerockTyrantCopyEffect(), filter, true));
    }

    private SpinerockTyrant(final SpinerockTyrant card) {
        super(card);
    }

    @Override
    public SpinerockTyrant copy() {
        return new SpinerockTyrant(this);
    }
}

class SpinerockTyrantCopyEffect extends OneShotEffect {

    enum SpinerockTyrantApplier implements StackObjectCopyApplier {
        instance;

        @Override
        public void modifySpell(StackObject stackObject, Game game) {
            ((Spell) stackObject).addAbilityForCopy(WitherAbility.getInstance());
        }

        @Override
        public MageObjectReferencePredicate getNextNewTargetType() {
            return null;
        }
    }

    SpinerockTyrantCopyEffect() {
        super(Outcome.Benefit);
        staticText = "you may copy it. If you do, those spells gain wither. You may choose new targets for the copy";
    }

    private SpinerockTyrantCopyEffect(final SpinerockTyrantCopyEffect effect) {
        super(effect);
    }

    @Override
    public SpinerockTyrantCopyEffect copy() {
        return new SpinerockTyrantCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        spell.createCopyOnStack(
                game, source, source.getControllerId(), true,
                1, SpinerockTyrantApplier.instance
        );
        game.addEffect(new SpinerockTyrantAbilityEffect(spell, game), source);
        return true;
    }
}

class SpinerockTyrantAbilityEffect extends ContinuousEffectImpl {

    SpinerockTyrantAbilityEffect(Spell spell, Game game) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.setTargetPointer(new FixedTarget(spell, game));
    }

    private SpinerockTyrantAbilityEffect(final SpinerockTyrantAbilityEffect effect) {
        super(effect);
    }

    @Override
    public SpinerockTyrantAbilityEffect copy() {
        return new SpinerockTyrantAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(getTargetPointer().getFirst(game, source));
        if (spell == null) {
            discard();
            return false;
        }
        Card card = spell.getCard();
        if (card == null) {
            return false;
        }
        game.getState().addOtherAbility(card, WitherAbility.getInstance());
        return true;
    }
}
