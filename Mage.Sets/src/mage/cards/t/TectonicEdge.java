package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetNonBasicLandPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class TectonicEdge extends CardImpl {

    private static final Condition condition = new OpponentControlsPermanentCondition(
            new FilterLandPermanent("an opponent controls four or more lands"), ComparisonType.MORE_THAN, 3
    );

    public TectonicEdge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // Tap: Add 1.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}, Sacrifice Tectonic Edge: Destroy target nonbasic land. Activate only if an opponent controls four or more lands.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect(), new GenericManaCost(1), condition
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetNonBasicLandPermanent());
        this.addAbility(ability);
    }

    private TectonicEdge(final TectonicEdge card) {
        super(card);
    }

    @Override
    public TectonicEdge copy() {
        return new TectonicEdge(this);
    }
}
