
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.GraftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JotaPeRL
 */
public final class HeliumSquirter extends CardImpl {

    public HeliumSquirter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 3
        this.addAbility(new GraftAbility(this, 3));
        
        // {1}: Target creature with a +1/+1 counter on it gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(1));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_P1P1));
        this.addAbility(ability);        
    }

    private HeliumSquirter(final HeliumSquirter card) {
        super(card);
    }

    @Override
    public HeliumSquirter copy() {
        return new HeliumSquirter(this);
    }
}
