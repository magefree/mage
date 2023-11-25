package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Backfir3
 */
public final class ClawsOfGix extends CardImpl {

    public ClawsOfGix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{0}");

        //{1}, Sacrifice a permanent: You gain 1 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_PERMANENT_SHORT_TEXT));
        this.addAbility(ability);
    }

    private ClawsOfGix(final ClawsOfGix card) {
        super(card);
    }

    @Override
    public ClawsOfGix copy() {
        return new ClawsOfGix(this);
    }
}
