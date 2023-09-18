package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageAttachedTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author HanClinto
 */
public final class GuiltyConscience extends CardImpl {

    public GuiltyConscience(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.UnboostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals damage, Guilty Conscience deals that much damage to that creature.
        this.addAbility(new DealsDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, new DamageAttachedEffect(SavedDamageValue.MUCH).setText("that much damage to that creature"), false));
    }

    private GuiltyConscience(final GuiltyConscience card) {
        super(card);
    }

    @Override
    public GuiltyConscience copy() {
        return new GuiltyConscience(this);
    }
}
