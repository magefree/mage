package mage.cards.r;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.abilities.keyword.FlashAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class RetroMutation extends CardImpl {

    public RetroMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature is a Turtle with base power and toughness 0/1. It can't attack and loses all abilities.
        SimpleStaticAbility ability = new SimpleStaticAbility(
        new BecomesCreatureAttachedEffect(
            new CreatureToken(0, 1, "0/1 Turtle creature", SubType.TURTLE),
            "Enchanted creature is a Turtle with base power and toughness 0/1",
            Duration.WhileOnBattlefield
        ));
        ability.addEffect(new CantAttackAttachedEffect(AttachmentType.AURA).setText("It can't attack "));
        ability.addEffect(new LoseAllAbilitiesAttachedEffect(AttachmentType.AURA).setText("and loses all abilities"));
        this.addAbility(ability);
    }

    private RetroMutation(final RetroMutation card) {
        super(card);
    }

    @Override
    public RetroMutation copy() {
        return new RetroMutation(this);
    }
}
