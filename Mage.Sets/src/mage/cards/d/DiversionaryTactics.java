package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class DiversionaryTactics extends CardImpl {

    public DiversionaryTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(),
                new TapTargetCost(
                        new TargetControlledPermanent(2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES)
                )
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DiversionaryTactics(final DiversionaryTactics card) {
        super(card);
    }

    @Override
    public DiversionaryTactics copy() {
        return new DiversionaryTactics(this);
    }
}
