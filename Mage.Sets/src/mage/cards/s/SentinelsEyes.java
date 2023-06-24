package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SentinelsEyes extends CardImpl {

    public SentinelsEyes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has vigilance.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.AURA
        ).setText("and has vigilance"));
        this.addAbility(ability);

        // Escape—{W}, Exile two other cards from your graveyard.
        this.addAbility(new EscapeAbility(this, "{W}", 2));
    }

    private SentinelsEyes(final SentinelsEyes card) {
        super(card);
    }

    @Override
    public SentinelsEyes copy() {
        return new SentinelsEyes(this);
    }
}
