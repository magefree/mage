package mage.cards.c;

import java.util.UUID;

import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCopiedSourceEffect;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.TargetSpell;
import mage.util.functions.StackObjectCopyApplier;

/**
 *
 * @author muz
 */
public final class ChoreographedSparks extends CardImpl {

    private static final FilterInstantOrSorcerySpell filter =
        new FilterInstantOrSorcerySpell("instant or sorcery spell you control");
    private static final FilterCreatureSpell filter2 =
        new FilterCreatureSpell("creature spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter2.add(TargetController.YOU.getControllerPredicate());
    }

    public ChoreographedSparks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // This spell can't be copied.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCopiedSourceEffect())
            .setRuleAtTheTop(true));

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Copy target instant or sorcery spell you control. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new CopyTargetStackObjectEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

        // * Copy target creature spell you control. The copy gains haste and "At the beginning of the end step, sacrifice this token."
        Mode mode = new Mode(
            new CopyTargetStackObjectEffect(false, false, false, 1, ChoreographedSparksApplier.instance)
                .setText("Copy target creature spell you control. "
                    + "The copy gains haste and \"At the beginning of "
                    + "the end step, sacrifice this token.\"")
        );
        mode.addTarget(new TargetSpell(filter2));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private ChoreographedSparks(final ChoreographedSparks card) {
        super(card);
    }

    @Override
    public ChoreographedSparks copy() {
        return new ChoreographedSparks(this);
    }
}

enum ChoreographedSparksApplier implements StackObjectCopyApplier {
    instance;

    @Override
    public void modifySpell(StackObject copiedSpell, Game game) {
        Spell spell = (Spell) copiedSpell;
        spell.addAbilityForCopy(HasteAbility.getInstance());
        spell.addAbilityForCopy(new BeginningOfEndStepTriggeredAbility(
            TargetController.ANY,
            new SacrificeSourceEffect(),
            false)
        );
    }

    @Override
    public MageObjectReferencePredicate getNextNewTargetType() {
        return null;
    }
}
