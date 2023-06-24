package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeffwadsworth
 */
public final class VenomousFangs extends CardImpl {

    public VenomousFangs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals damage to a creature, destroy the other creature.
        this.addAbility(new DealsDamageToACreatureAttachedTriggeredAbility(new DestroyTargetEffect(), false, "enchanted creature", false, true));
        
    }

    private VenomousFangs(final VenomousFangs card) {
        super(card);
    }

    @Override
    public VenomousFangs copy() {
        return new VenomousFangs(this);
    }
}
