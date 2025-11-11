package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CreepingCrystalCoating extends CardImpl {

    public CreepingCrystalCoating(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +0/+3 and has "Whenever this creature attacks, create a Food token."
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(0, 3));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new CreateTokenEffect(new FoodToken())), AttachmentType.AURA
        ).setText("and has \"Whenever this creature attacks, create a Food token.\""));
        this.addAbility(ability);
    }

    private CreepingCrystalCoating(final CreepingCrystalCoating card) {
        super(card);
    }

    @Override
    public CreepingCrystalCoating copy() {
        return new CreepingCrystalCoating(this);
    }
}
