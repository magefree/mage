
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.BestowAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class LeafcrownDryad extends CardImpl {

    public LeafcrownDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.NYMPH);
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bestow {3}{G}  (If you cast this card for its bestow cost, it's an Aura spell with enchant creature. It becomes a creature again if it's not attached to a creature.)
        this.addAbility(new BestowAbility(this, "{3}{G}"));
        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Enchanted creature gets +2/+2 and has reach.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2,2));
        Effect effect = new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has reach");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private LeafcrownDryad(final LeafcrownDryad card) {
        super(card);
    }

    @Override
    public LeafcrownDryad copy() {
        return new LeafcrownDryad(this);
    }
}
