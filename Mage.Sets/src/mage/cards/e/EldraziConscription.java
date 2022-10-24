

package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class EldraziConscription extends CardImpl {

    public EldraziConscription (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature gets +10/+10 and has trample and annihilator 2        
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(10, 10, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(TrampleAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has trample");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(new AnnihilatorAbility(2), AttachmentType.AURA);
        effect.setText("and annihilator 2. <i>(Whenever it attacks, defending player sacrifices two permanents.)</i>");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public EldraziConscription (final EldraziConscription card) {
        super(card);
    }

    @Override
    public EldraziConscription copy() {
        return new EldraziConscription(this);
    }

}
