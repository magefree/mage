
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;

/**
 *
 * @author LevelX2
 */
public final class Dragonrage extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("attacking creature you control");
    
    static {
        filter.add(AttackingPredicate.instance);
    }
    
    
    public Dragonrage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        // Add {R} for each attacking creature you control. 
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.RedMana(1),
                new PermanentsOnBattlefieldCount(filter)));
        
        // Until end of turn, attacking creatures you control gain "{R}: This creature gets +1/+0 until end of turn."
        Ability abilityToGain = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0,Duration.EndOfTurn), new ManaCostsImpl<>("{R}"));
        Effect effect = new GainAbilityAllEffect(abilityToGain, Duration.EndOfTurn, filter);
        effect.setText("Until end of turn, attacking creatures you control gain \"{R}: This creature gets +1/+0 until end of turn.\"");
        this.getSpellAbility().addEffect(effect);        
        
    }

    private Dragonrage(final Dragonrage card) {
        super(card);
    }

    @Override
    public Dragonrage copy() {
        return new Dragonrage(this);
    }
}
