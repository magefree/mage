
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SomberwaldAlpha extends CardImpl {
    
    static final private FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public SomberwaldAlpha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever a creature you control becomes blocked, it gets +1/+1 until end of turn.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("it gets +1/+1 until end of turn");
        this.addAbility(new BecomesBlockedAllTriggeredAbility(effect, false, filter, true));
        
        // {1}{G}: Target creature you control gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), 
                new ManaCostsImpl<>("{1}{G}"));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);  
    }

    private SomberwaldAlpha(final SomberwaldAlpha card) {
        super(card);
    }

    @Override
    public SomberwaldAlpha copy() {
        return new SomberwaldAlpha(this);
    }
}
