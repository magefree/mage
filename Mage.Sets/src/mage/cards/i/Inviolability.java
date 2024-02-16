package mage.cards.i;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.PreventDamageToAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class Inviolability extends CardImpl {

    public Inviolability(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget));

        // Prevent all damage that would be dealt to enchanted creature.
        this.addAbility(new SimpleStaticAbility(new PreventDamageToAttachedEffect(
                Duration.WhileOnBattlefield, AttachmentType.AURA, false
        )));
    }

    private Inviolability(final Inviolability card) {
        super(card);
    }

    @Override
    public Inviolability copy() {
        return new Inviolability(this);
    }
}
