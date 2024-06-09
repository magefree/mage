package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class TriclopeanSight extends CardImpl {

    public TriclopeanSight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Neutral));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Triclopean Sight enters the battlefield, untap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapAttachedEffect()));

        // Enchanted creature gets +1/+1 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                1, 1, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(),
                AttachmentType.AURA,
                Duration.WhileOnBattlefield
        ).setText("and has vigilance"));
        this.addAbility(ability);
    }

    private TriclopeanSight(final TriclopeanSight card) {
        super(card);
    }

    @Override
    public TriclopeanSight copy() {
        return new TriclopeanSight(this);
    }
}
