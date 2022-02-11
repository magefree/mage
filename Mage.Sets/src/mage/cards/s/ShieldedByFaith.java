
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ShieldedByFaith extends CardImpl {

    public ShieldedByFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // Enchanted creature has indestructible.
        Effect effect = new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), 
                AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature has indestructible");
        effect.setOutcome(Outcome.Benefit);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // Whenever a creature enters the battlefield, you may attach Shielded by Faith to that creature.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new AttachEffect(Outcome.Benefit, "attach {this} to that creature"),
                StaticFilters.FILTER_PERMANENT_A_CREATURE, true, SetTargetPointer.PERMANENT, null, false));
    }

    private ShieldedByFaith(final ShieldedByFaith card) {
        super(card);
    }

    @Override
    public ShieldedByFaith copy() {
        return new ShieldedByFaith(this);
    }
}
