package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Ichthyomorphosis extends CardImpl {

    public Ichthyomorphosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature loses all abilities and is a blue Fish with base power and toughness 0/1.
        Effect effect = new BecomesCreatureAttachedEffect(
                new CreatureToken(0, 1, "", SubType.FISH).withColor("U"),
                "Enchanted creature loses all abilities and is a blue Fish with base power and toughness 0/1",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ALL
        );
        effect.setOutcome(Outcome.Detriment);
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private Ichthyomorphosis(final Ichthyomorphosis card) {
        super(card);
    }

    @Override
    public Ichthyomorphosis copy() {
        return new Ichthyomorphosis(this);
    }
}
