
package mage.cards.g;

import java.util.UUID;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class GuardianZendikon extends CardImpl {

    public GuardianZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        // Enchanted land is a 2/6 white Wall creature with defender. It's still a land.
        // When enchanted land dies, return that card to its owner's hand.
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        this.addAbility(new EnchantAbility(auraTarget));

        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
            new CreatureToken(2, 6, "2/6 white Wall creature with defender", SubType.WALL).withColor("W")
                .withAbility(DefenderAbility.getInstance()),
            "Enchanted land is a 2/6 white Wall creature with defender. It's still a land",
            Duration.WhileOnBattlefield,
            BecomesCreatureAttachedEffect.LoseType.COLOR
        )));

        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false));
    }

    private GuardianZendikon(final GuardianZendikon card) {
        super(card);
    }

    @Override
    public GuardianZendikon copy() {
        return new GuardianZendikon(this);
    }
}
