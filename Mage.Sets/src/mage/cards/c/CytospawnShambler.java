
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.GraftAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class CytospawnShambler extends CardImpl {

    public CytospawnShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{G}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 6
        this.addAbility(new GraftAbility(this, 6));
        
        // {G}: Target creature with a +1/+1 counter on it gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_P1P1));
        this.addAbility(ability);
    }

    private CytospawnShambler(final CytospawnShambler card) {
        super(card);
    }

    @Override
    public CytospawnShambler copy() {
        return new CytospawnShambler(this);
    }
}
