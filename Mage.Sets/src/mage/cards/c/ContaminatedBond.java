
package mage.cards.c;

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
 * @author Loki
 */
public final class ContaminatedBond extends CardImpl {

    public ContaminatedBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature attacks or blocks, its controller loses 3 life.
        this.addAbility(new AttacksOrBlocksEnchantedTriggeredAbility(Zone.BATTLEFIELD, new LoseLifeControllerAttachedEffect(3)));
    }

    public ContaminatedBond(final ContaminatedBond card) {
        super(card);
    }

    @Override
    public ContaminatedBond copy() {
        return new ContaminatedBond(this);
    }
}
