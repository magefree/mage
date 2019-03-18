
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.LoseCreatureTypeSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreatureInPlay;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class IroasGodOfVictory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control");
    private static final FilterControlledCreatureInPlay filterAttacking = new FilterControlledCreatureInPlay("attacking creatures you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filterAttacking.getCreatureFilter().add(AttackingPredicate.instance);
    }
    
    public IroasGodOfVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT,CardType.CREATURE},"{2}{R}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);

        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        
        // As long as your devotion to red and white is less than seven, Iroas isn't a creature.
        Effect effect = new LoseCreatureTypeSourceEffect(new DevotionCount(ColoredManaSymbol.R, ColoredManaSymbol.W), 7);
        effect.setText("As long as your devotion to red and white is less than seven, Iroas isn't a creature");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // Creatures you control have menace. (They can't be blocked except by two or more creatures.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(new MenaceAbility(), Duration.WhileOnBattlefield, filter)));
        
        // Prevent all damage that would be dealt to attacking creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, filterAttacking)));
        
    }

    public IroasGodOfVictory(final IroasGodOfVictory card) {
        super(card);
    }

    @Override
    public IroasGodOfVictory copy() {
        return new IroasGodOfVictory(this);
    }
}
