
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SoratamiCloudChariot extends CardImpl {

    public SoratamiCloudChariot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {2}: Target creature you control gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(2));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);    
        
        // {2}: Prevent all combat damage that would be dealt to
        Effect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt to");
        // and dealt by target creature you control this turn.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}"));
        effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("and dealt by target creature you control this turn.");
        ability.addEffect(effect);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);        
    }

    private SoratamiCloudChariot(final SoratamiCloudChariot card) {
        super(card);
    }

    @Override
    public SoratamiCloudChariot copy() {
        return new SoratamiCloudChariot(this);
    }
}
