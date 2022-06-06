package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.XTargetsAdjuster;

/**
 *
 * @author fireshoes
 */
public final class MishrasHelix extends CardImpl {

    public MishrasHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{5}");

        // {X}, {tap}: Tap X target lands.
        Effect effect = new TapTargetEffect("tap X target lands");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_LANDS));
        ability.setTargetAdjuster(XTargetsAdjuster.instance);
        this.addAbility(ability);
    }

    private MishrasHelix(final MishrasHelix card) {
        super(card);
    }

    @Override
    public MishrasHelix copy() {
        return new MishrasHelix(this);
    }
}
