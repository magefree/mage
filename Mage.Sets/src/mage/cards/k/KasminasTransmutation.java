package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
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
public final class KasminasTransmutation extends CardImpl {

    public KasminasTransmutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature loses all abilities and has base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(1, 1), "Enchanted creature loses all abilities " +
                "and has base power and toughness 1/1", Duration.WhileOnBattlefield,
                BecomesCreatureAttachedEffect.LoseType.ABILITIES
        )));
    }

    private KasminasTransmutation(final KasminasTransmutation card) {
        super(card);
    }

    @Override
    public KasminasTransmutation copy() {
        return new KasminasTransmutation(this);
    }
}
