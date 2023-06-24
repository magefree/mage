
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class AngelicDestiny extends CardImpl {

    public AngelicDestiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +4/+4, has flying and first strike, and is an Angel in addition to its other types.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4, 4, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", has flying");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and first strike");
        ability.addEffect(effect);
        effect = new AddCardSubtypeAttachedEffect(SubType.ANGEL, Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText(", and is an Angel in addition to its other types");
        ability.addEffect(effect);
        this.addAbility(ability);

        // When enchanted creature dies, return Angelic Destiny to its owner's hand.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToHandSourceEffect(false, true), "enchanted creature"));
    }

    private AngelicDestiny(final AngelicDestiny card) {
        super(card);
    }

    @Override
    public AngelicDestiny copy() {
        return new AngelicDestiny(this);
    }
}
