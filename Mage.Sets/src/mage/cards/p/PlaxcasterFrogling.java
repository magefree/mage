
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.GraftAbility;
import mage.abilities.keyword.ShroudAbility;
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
public final class PlaxcasterFrogling extends CardImpl {

    public PlaxcasterFrogling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 3
        this.addAbility(new GraftAbility(this, 3));
        
        // {2}: Target creature with a +1/+1 counter on it gains shroud until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(ShroudAbility.getInstance(), Duration.EndOfTurn), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_P1P1));
        this.addAbility(ability);        
    }

    private PlaxcasterFrogling(final PlaxcasterFrogling card) {
        super(card);
    }

    @Override
    public PlaxcasterFrogling copy() {
        return new PlaxcasterFrogling(this);
    }
}
