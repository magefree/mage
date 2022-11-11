
package mage.cards.v;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandAttachedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.custom.ElementalCreatureToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class VastwoodZendikon extends CardImpl {

    public VastwoodZendikon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        // Enchanted land is a 6/4 green Elemental creature. It's still a land.
        // When enchanted land dies, return that card to its owner's hand.
        
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new ElementalCreatureToken(6, 4, "6/4 green Elemental creature", new ObjectColor("G")),
                "Enchanted land is a 6/4 green Elemental creature. It's still a land", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR));
        this.addAbility(ability2);
        
        Ability ability3 = new DiesAttachedTriggeredAbility(new ReturnToHandAttachedEffect(), "enchanted land", false);
        this.addAbility(ability3);
    }

    private VastwoodZendikon(final VastwoodZendikon card) {
        super(card);
    }

    @Override
    public VastwoodZendikon copy() {
        return new VastwoodZendikon(this);
    }
}
