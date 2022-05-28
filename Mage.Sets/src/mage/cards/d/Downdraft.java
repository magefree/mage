
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class Downdraft extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public Downdraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        // {G}: Target creature loses flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new LoseAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), 
                new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // Sacrifice Downdraft: Downdraft deals 2 damage to each creature with flying.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageAllEffect(2, filter), new SacrificeSourceCost()));
    }

    private Downdraft(final Downdraft card) {
        super(card);
    }

    @Override
    public Downdraft copy() {
        return new Downdraft(this);
    }
}
