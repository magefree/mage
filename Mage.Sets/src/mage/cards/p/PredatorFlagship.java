
package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PredatorFlagship extends CardImpl {

    public PredatorFlagship(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.supertype.add(SuperType.LEGENDARY);

        // {2}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {5}, {T}: Destroy target creature with flying.
        ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(ability);
    }

    private PredatorFlagship(final PredatorFlagship card) {
        super(card);
    }

    @Override
    public PredatorFlagship copy() {
        return new PredatorFlagship(this);
    }
}
