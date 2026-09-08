package mage.cards.e;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.abilities.effects.common.replacement.AdditionalTriggerObjectReplacementEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ColorlessPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EchoesOfEternity extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a colorless spell");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    private static final FilterSpellOrPermanent filter2 = new FilterSpellOrPermanent("a colorless spell you control or another colorless permanent you control");

    static {
        filter2.getSpellFilter().add(TargetController.YOU.getControllerPredicate());
        filter2.getSpellFilter().add(ColorlessPredicate.instance);
        filter2.getPermanentFilter().add(TargetController.YOU.getControllerPredicate());
        filter2.getPermanentFilter().add(ColorlessPredicate.instance);
        filter2.getPermanentFilter().add(AnotherPredicate.instance);
    }

    public EchoesOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.KINDRED, CardType.ENCHANTMENT}, "{3}{C}{C}{C}");

        this.subtype.add(SubType.ELDRAZI);

        // If a triggered ability of a colorless spell you control or another colorless permanent you control triggers, that ability triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new AdditionalTriggerObjectReplacementEffect(filter2)));

        // Whenever you cast a colorless spell, copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(new CopyTargetStackObjectEffect(
                false, false, true
        ).withText("it"), filter, false, SetTargetPointer.SPELL));
    }

    private EchoesOfEternity(final EchoesOfEternity card) {
        super(card);
    }

    @Override
    public EchoesOfEternity copy() {
        return new EchoesOfEternity(this);
    }
}
