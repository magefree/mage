package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class Indestructibility extends CardImpl {

    public Indestructibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted permanent is indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(), AttachmentType.AURA,
                Duration.WhileOnBattlefield, null, "permanent"
        )));
    }

    private Indestructibility(final Indestructibility card) {
        super(card);
    }

    @Override
    public Indestructibility copy() {
        return new Indestructibility(this);
    }
}
