package mage.cards.e;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardColorAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EyeOfNidhogg extends CardImpl {

    public EyeOfNidhogg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature is a black Dragon with base power and toughness 4/2, has flying and deathtouch, and is goaded.
        Ability ability = new SimpleStaticAbility(new SetCardColorAttachedEffect(
                ObjectColor.BLACK, Duration.WhileControlled, AttachmentType.AURA
        ).setText("enchanted creature is a black Dragon"));
        ability.addEffect(new AddCardSubtypeAttachedEffect(
                SubType.DRAGON, AttachmentType.AURA
        ).setText("with base power"));
        ability.addEffect(new SetBasePowerToughnessAttachedEffect(
                4, 2, AttachmentType.AURA
        ).setText("and toughness 4/2"));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText(", has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.AURA
        ).setText("and deathtouch"));
        ability.addEffect(new GoadAttachedEffect().concatBy(","));
        this.addAbility(ability);

        // When Eye of Nidhogg is put into a graveyard from the battlefield, return it to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()
                .setText("return it to its owner's hand")));
    }

    private EyeOfNidhogg(final EyeOfNidhogg card) {
        super(card);
    }

    @Override
    public EyeOfNidhogg copy() {
        return new EyeOfNidhogg(this);
    }
}
