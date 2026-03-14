package mage.cards.w;

import java.util.UUID;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class WindZendikon extends CardImpl {

    public WindZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        // Enchanted land is a 2/2 blue Elemental creature with flying. It's still a land.
        // When enchanted land dies, return that card to its owner's hand.

        TargetLandPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BecomeCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
            new CreatureToken(
                2, 2, "2/2 blue Elemental creature with flying", SubType.ELEMENTAL
            ).withColor("U").withAbility(FlyingAbility.getInstance()),
            "Enchanted land is a 2/2 blue Elemental creature with flying. It's still a land",
            Duration.WhileOnBattlefield,
            BecomesCreatureAttachedEffect.LoseType.COLOR
        )));

        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land"));
    }

    private WindZendikon(final WindZendikon card) {
        super(card);
    }

    @Override
    public WindZendikon copy() {
        return new WindZendikon(this);
    }
}
