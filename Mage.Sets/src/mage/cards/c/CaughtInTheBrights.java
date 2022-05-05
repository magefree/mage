
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class CaughtInTheBrights extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vehicle you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.VEHICLE.getPredicate());
    }

    public CaughtInTheBrights(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockAttachedEffect(AttachmentType.AURA)));

        // When a Vehicle you control attacks, exile enchanted creature.
        this.addAbility(new AttacksAllTriggeredAbility(new ExileAttachedEffect(), false, filter, SetTargetPointer.NONE, false).setTriggerPhrase("When a Vehicle you control attacks, "));
    }

    private CaughtInTheBrights(final CaughtInTheBrights card) {
        super(card);
    }

    @Override
    public CaughtInTheBrights copy() {
        return new CaughtInTheBrights(this);
    }
}
