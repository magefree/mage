
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author wetterlicht
 */
public final class PsychicOverload extends CardImpl {

    public PsychicOverload(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));
        
        // When Psychic Overload enters the battlefield, tap enchanted permanent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect("permanent")));
        // Enchanted permanent doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepEnchantedEffect("permanent")));
        
        // Enchanted permanent has "Discard two artifact cards: Untap this permanent."
        Ability gainedAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new DiscardTargetCost(new TargetCardInHand(2, new FilterArtifactCard("two artifact cards"))));       
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield, "Enchanted permanent has \"Discard two artifact cards: Untap this permanent.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,  effect));
    }

    private PsychicOverload(final PsychicOverload card) {
        super(card);
    }

    @Override
    public PsychicOverload copy() {
        return new PsychicOverload(this);
    }
}
