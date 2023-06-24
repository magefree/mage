
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 * @author noxx
 */
public final class AbundantGrowth extends CardImpl {

    public AbundantGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Abundant Growth enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));

        // Enchanted land has "{T}: Add one mana of any color."
        Ability gainedAbility = new AnyColorManaAbility(new TapSourceCost());
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("Enchanted land has \"{T}: Add one mana of any color.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private AbundantGrowth(final AbundantGrowth card) {
        super(card);
    }

    @Override
    public AbundantGrowth copy() {
        return new AbundantGrowth(this);
    }
}
