
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class LivingTerrain extends CardImpl {

    public LivingTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted land is a 5/6 green Treefolk creature that's still a land.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAttachedEffect(
            new CreatureToken(5, 6, "5/6 green Treefolk creature", SubType.TREEFOLK)
                .withColor("G"),
            "Enchanted land is a 5/6 green Treefolk creature that's still a land",
            Duration.WhileOnBattlefield,
            BecomesCreatureAttachedEffect.LoseType.COLOR
        )));
    }

    private LivingTerrain(final LivingTerrain card) {
        super(card);
    }

    @Override
    public LivingTerrain copy() {
        return new LivingTerrain(this);
    }
}
