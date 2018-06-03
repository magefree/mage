
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksEnchantedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeControllerAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class SinisterPossession extends CardImpl {

    public SinisterPossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseLife));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks or blocks, it's controller loses 2 life.
        this.addAbility(new AttacksOrBlocksEnchantedTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeControllerAttachedEffect(2)));
    }

    public SinisterPossession(final SinisterPossession card) {
        super(card);
    }

    @Override
    public SinisterPossession copy() {
        return new SinisterPossession(this);
    }
}
