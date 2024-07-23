package mage.cards.d;

import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetLandPermanent;
import mage.target.targetadjustment.ConditionalTargetAdjuster;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DwarvenLandslide extends CardImpl {

    public DwarvenLandslide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Kicker-{2}{R}, Sacrifice a land.
        Costs<Cost> kickerCosts = new CostsImpl<>();
        kickerCosts.add(new ManaCostsImpl<>("{2}{R}"));
        kickerCosts.add(new SacrificeTargetCost(StaticFilters.FILTER_LAND));
        this.addAbility(new KickerAbility(kickerCosts));

        // Destroy target land. If Dwarven Landslide was kicked, destroy another target land.
        getSpellAbility().addEffect(new DestroyTargetEffect("Destroy target land. If this spell was kicked, destroy another target land"));
        getSpellAbility().addTarget(new TargetLandPermanent());
        getSpellAbility().setTargetAdjuster(new ConditionalTargetAdjuster(KickedCondition.ONCE,
                new TargetLandPermanent(2)));
    }

    private DwarvenLandslide(final DwarvenLandslide card) {
        super(card);
    }

    @Override
    public DwarvenLandslide copy() {
        return new DwarvenLandslide(this);
    }
}
