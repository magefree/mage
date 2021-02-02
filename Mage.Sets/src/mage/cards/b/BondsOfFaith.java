
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.EquippedHasSubtypeCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author nantuko
 */
public final class BondsOfFaith extends CardImpl {

    private static final String rule = "Enchanted creature gets +2/+2 as long as it's a Human";

    public BondsOfFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 as long as it's a Human. Otherwise, it can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(new BoostEquippedEffect(2, 2), new EquippedHasSubtypeCondition(SubType.HUMAN), rule)));
        Effect effect = new ConditionalRestrictionEffect(new CantAttackBlockAttachedEffect(AttachmentType.AURA), new InvertCondition(new EquippedHasSubtypeCondition(SubType.HUMAN)));
        effect.setText("Otherwise, it can't attack or block");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private BondsOfFaith(final BondsOfFaith card) {
        super(card);
    }

    @Override
    public BondsOfFaith copy() {
        return new BondsOfFaith(this);
    }
}
