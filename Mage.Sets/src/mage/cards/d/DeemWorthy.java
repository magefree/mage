

package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author Darkside-
 */
public final class DeemWorthy extends CardImpl {

    public DeemWorthy (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{R}");

        
        // Deem Worthy deals 7 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(7));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        //Cycling {3}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}{R}")));
        // When you cycle Deem Worthy, you may have it deal 2 damage to target creature.
        Ability ability = new CycleTriggeredAbility(new DamageTargetEffect(2),true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public DeemWorthy (final DeemWorthy card) {
        super(card);
    }

    @Override
    public DeemWorthy copy() {
        return new DeemWorthy(this);
    }
}
