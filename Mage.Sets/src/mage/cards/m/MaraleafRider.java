package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaraleafRider extends CardImpl {

    public MaraleafRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Sacrifice a Food: Target creature blocks Maraleaf Rider this turn if able.
        Ability ability = new SimpleActivatedAbility(
                new MustBeBlockedByTargetSourceEffect(), new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_FOOD))
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MaraleafRider(final MaraleafRider card) {
        super(card);
    }

    @Override
    public MaraleafRider copy() {
        return new MaraleafRider(this);
    }
}
