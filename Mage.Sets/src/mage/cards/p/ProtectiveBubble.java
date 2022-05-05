package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ProtectiveBubble extends CardImpl {

    public ProtectiveBubble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature can't be blocked and has shroud.
        Ability ability = new SimpleStaticAbility(new CantBeBlockedAttachedEffect(AttachmentType.AURA));
        ability.addEffect(new GainAbilityAttachedEffect(
                ShroudAbility.getInstance(), AttachmentType.AURA
        ).setText("and has shroud"));
        this.addAbility(ability);
    }

    private ProtectiveBubble(final ProtectiveBubble card) {
        super(card);
    }

    @Override
    public ProtectiveBubble copy() {
        return new ProtectiveBubble(this);
    }
}
