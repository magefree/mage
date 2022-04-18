package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author HanClinto
 */
public final class Backfire extends CardImpl {

    public Backfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted creature deals damage to you, Backfire deals that much damage to that creature's controller.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new DamageAttachedControllerEffect(SavedDamageValue.MUCH), "enchanted creature", false, true, false, TargetController.YOU));
    }

    private Backfire(final Backfire card) {
        super(card);
    }

    @Override
    public Backfire copy() {
        return new Backfire(this);
    }
}
