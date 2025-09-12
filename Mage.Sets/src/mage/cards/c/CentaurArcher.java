
package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author anonymous
 */
public final class CentaurArcher extends CardImpl {

    public CentaurArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // {tap}: Centaur Archer deals 1 damage to target creature with flying.
        Ability activatedAbility = new SimpleActivatedAbility(new DamageTargetEffect(1), new TapSourceCost());
        activatedAbility.addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
        this.addAbility(activatedAbility);
    }

    private CentaurArcher(final CentaurArcher card) {
        super(card);
    }

    @Override
    public CentaurArcher copy() {
        return new CentaurArcher(this);
    }
}
