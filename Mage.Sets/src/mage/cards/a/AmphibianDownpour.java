package mage.cards.a;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.StormAbility;
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
public final class AmphibianDownpour extends CardImpl {

    public AmphibianDownpour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Storm
        this.addAbility(new StormAbility());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature loses all abilities and is a blue Frog creature with base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(1, 1, "", SubType.FROG).withColor("U"),
                "Enchanted creature loses all abilities and is a blue Frog creature with base power and toughness 1/1",
                Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ALL, Outcome.Detriment
        )));
    }

    private AmphibianDownpour(final AmphibianDownpour card) {
        super(card);
    }

    @Override
    public AmphibianDownpour copy() {
        return new AmphibianDownpour(this);
    }
}
