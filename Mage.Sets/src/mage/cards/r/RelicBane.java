package mage.cards.r;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class RelicBane extends CardImpl {

    public RelicBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant artifact
        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted artifact has "At the beginning of your upkeep, you lose 2 life."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new BeginningOfUpkeepTriggeredAbility(new LoseLifeSourceControllerEffect(2)),
                AttachmentType.AURA, Duration.WhileOnBattlefield, null, "artifact")));
     }

    private RelicBane(final RelicBane card) {
        super(card);
    }

    @Override
    public RelicBane copy() {
        return new RelicBane(this);
    }
}
