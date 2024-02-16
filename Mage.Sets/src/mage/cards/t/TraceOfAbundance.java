package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.mana.AddManaAnyColorAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class TraceOfAbundance extends CardImpl {

    public TraceOfAbundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R/W}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted land has shroud.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                ShroudAbility.getInstance(), AttachmentType.AURA,
                Duration.WhileOnBattlefield, "enchanted land has shroud"
        )));

        // Whenever enchanted land is tapped for mana, its controller adds one mana of any color.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new AddManaAnyColorAttachedControllerEffect()));
    }

    private TraceOfAbundance(final TraceOfAbundance card) {
        super(card);
    }

    @Override
    public TraceOfAbundance copy() {
        return new TraceOfAbundance(this);
    }
}
