
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BoundInSilence extends CardImpl {

    public BoundInSilence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.REBEL, SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Removal));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackBlockAttachedEffect(AttachmentType.AURA)));
    }

    private BoundInSilence(final BoundInSilence card) {
        super(card);
    }

    @Override
    public BoundInSilence copy() {
        return new BoundInSilence(this);
    }
}
