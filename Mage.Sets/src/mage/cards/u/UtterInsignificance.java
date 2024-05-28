package mage.cards.u;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
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
public final class UtterInsignificance extends CardImpl {

    public UtterInsignificance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature loses all abilities and has base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
                new CreatureToken(1, 1), "Enchanted creature loses all abilities " +
                "and has base power and toughness 1/1", Duration.WhileOnBattlefield,
                BecomesCreatureAttachedEffect.LoseType.ABILITIES
        )));

        // {2}{C}: Exile enchanted creature.
        this.addAbility(new SimpleActivatedAbility(new ExileAttachedEffect(), new ManaCostsImpl<>("{2}{C}")));
    }

    private UtterInsignificance(final UtterInsignificance card) {
        super(card);
    }

    @Override
    public UtterInsignificance copy() {
        return new UtterInsignificance(this);
    }
}
