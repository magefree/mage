
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DarksteelMutation extends CardImpl {

    public DarksteelMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature is an Insect artifact creature with base power and toughness 0/1 and has indestructible, and it loses all other abilities, card types, and creature types.
        this.addAbility(new SimpleStaticAbility(
            new BecomesCreatureAttachedEffect(
                new CreatureToken(
                    0, 1,
                    "Insect artifact creature with base power and toughness 0/1",
                    SubType.INSECT
                ).withType(CardType.ARTIFACT).withAbility(IndestructibleAbility.getInstance()),
                "Enchanted creature is an Insect artifact creature with base power and toughness 0/1 and has indestructible, and it loses all other abilities, card types, and creature types.",
                Duration.WhileOnBattlefield,
                BecomesCreatureAttachedEffect.LoseType.ALL_BUT_COLOR
            )
        ));

    }

    private DarksteelMutation(final DarksteelMutation card) {
        super(card);
    }

    @Override
    public DarksteelMutation copy() {
        return new DarksteelMutation(this);
    }
}
