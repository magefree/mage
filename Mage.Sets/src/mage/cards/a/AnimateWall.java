
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class AnimateWall extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wall");
    
    static {
        filter.add(SubType.WALL.getPredicate());
    }
    
    public AnimateWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);


        // Enchant Wall
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted Wall can attack as though it didn't have defender.
        Ability canAttackAbility = new SimpleStaticAbility(Zone.BATTLEFIELD, new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield));
        Effect enchantEffect = new GainAbilityAttachedEffect(canAttackAbility, AttachmentType.AURA, Duration.WhileOnBattlefield);
        enchantEffect.setText("Enchanted Wall can attack as though it didn't have defender");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, enchantEffect));
    }

    private AnimateWall(final AnimateWall card) {
        super(card);
    }

    @Override
    public AnimateWall copy() {
        return new AnimateWall(this);
    }
}
