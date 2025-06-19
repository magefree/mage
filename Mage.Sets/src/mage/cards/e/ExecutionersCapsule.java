package mage.cards.e;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK;

/**
 *
 * @author North
 */
public final class ExecutionersCapsule extends CardImpl {

    public ExecutionersCapsule(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{B}");

        SimpleActivatedAbility ability = new SimpleActivatedAbility(new DestroyTargetEffect(), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.addAbility(ability);
    }

    private ExecutionersCapsule(final ExecutionersCapsule card) {
        super(card);
    }

    @Override
    public ExecutionersCapsule copy() {
        return new ExecutionersCapsule(this);
    }
}
