package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class ElderMastery extends CardImpl {

    public ElderMastery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{B}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +3/+3 and has flying.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(
                3, 3, Duration.WhileOnBattlefield
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield
        ).setText("and has flying"));
        this.addAbility(ability);

        // Whenever enchanted creature deals damage to a player, that player discards two cards.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new DiscardTargetEffect(2), "enchanted creature",
                false, true, false
        ));
    }

    private ElderMastery(final ElderMastery card) {
        super(card);
    }

    @Override
    public ElderMastery copy() {
        return new ElderMastery(this);
    }
}
