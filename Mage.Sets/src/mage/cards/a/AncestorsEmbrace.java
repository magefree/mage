package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncestorsEmbrace extends CardImpl {

    public AncestorsEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.subtype.add(SubType.AURA);
        this.color.setWhite(true);
        this.nightCard = true;

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has lifelink.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                LifelinkAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield
        )));

        // If Ancestor's Embrace would be put into a graveyard from anywhere, exile it instead.
        this.addAbility(DisturbAbility.makeBackAbility());
    }

    private AncestorsEmbrace(final AncestorsEmbrace card) {
        super(card);
    }

    @Override
    public AncestorsEmbrace copy() {
        return new AncestorsEmbrace(this);
    }
}
