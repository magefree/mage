
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class DustCorona extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with flying");
    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DustCorona(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature gets +2/+0 and can't be blocked by creatures with flying.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 0));
        Effect effect = new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, filter, AttachmentType.AURA);
        effect.setText("and can't be blocked by creatures with flying");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DustCorona(final DustCorona card) {
        super(card);
    }

    @Override
    public DustCorona copy() {
        return new DustCorona(this);
    }
}
