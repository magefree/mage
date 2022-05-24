package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.GraftAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author JotaPeRL
 */
public final class AquastrandSpider extends CardImpl {

    public AquastrandSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Graft 2
        this.addAbility(new GraftAbility(this, 2));

        // {G}: Target creature with a +1/+1 counter on it gains reach until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new GainAbilityTargetEffect(ReachAbility.getInstance(),
                        Duration.EndOfTurn), new ManaCostsImpl<>("{G}"));
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_P1P1));
        this.addAbility(ability.addCustomOutcome(Outcome.Benefit));        
    }
    
    private AquastrandSpider(final AquastrandSpider card) {
        super(card);
    }
    
    @Override
    public AquastrandSpider copy() {
        return new AquastrandSpider(this);
    }
}
