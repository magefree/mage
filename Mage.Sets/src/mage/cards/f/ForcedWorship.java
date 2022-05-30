

package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ForcedWorship extends CardImpl {

    public ForcedWorship (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);

        
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackAttachedEffect(AttachmentType.AURA)));
        // {2}{W}: Return Forced Worship to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{2}{W}")));
    }

    public ForcedWorship (final ForcedWorship card) {
        super(card);
    }

    @Override
    public ForcedWorship copy() {
        return new ForcedWorship(this);
    }

}
