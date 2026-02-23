package mage.cards.n;

import java.util.UUID;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.abilities.keyword.FlashAbility;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class NoggleTheMind extends CardImpl {

    public NoggleTheMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature loses all abilities and is a colorless Noggle with base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
            new CreatureToken(1, 1, "", SubType.NOGGLE),
            "Enchanted creature loses all abilities and is a colorless Noggle with base power and toughness 1/1",
            Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.ALL, Outcome.Detriment
        )));
    }

    private NoggleTheMind(final NoggleTheMind card) {
        super(card);
    }

    @Override
    public NoggleTheMind copy() {
        return new NoggleTheMind(this);
    }
}
