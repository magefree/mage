package mage.cards.l;

import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Derpthemeus
 */
public final class Launch extends CardImpl {

    public Launch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetCreaturePermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield)));

        // When Launch is put into a graveyard from the battlefield, return Launch to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));

    }

    private Launch(final Launch card) {
        super(card);
    }

    @Override
    public Launch copy() {
        return new Launch(this);
    }
}
