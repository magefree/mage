
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByTargetSourceEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.BloodthirstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class BurningTreeBloodscale extends CardImpl {

    public BurningTreeBloodscale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");
        this.subtype.add(SubType.VIASHINO, SubType.BERSERKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bloodthirst 1
        this.addAbility(new BloodthirstAbility(1));
        
        // {2}{R}: Target creature can't block Burning-Tree Bloodscale this turn.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CantBeBlockedByTargetSourceEffect(Duration.EndOfTurn), 
        		new ManaCostsImpl<>("{2}{R}"));
        ability1.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability1);
        
        // {2}{G}: Target creature blocks Burning-Tree Bloodscale this turn if able.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn), 
        		new ManaCostsImpl<>("{2}{G}"));
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private BurningTreeBloodscale(final BurningTreeBloodscale card) {
        super(card);
    }

    @Override
    public BurningTreeBloodscale copy() {
        return new BurningTreeBloodscale(this);
    }
}
