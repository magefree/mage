package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class ContestedWarZone extends CardImpl {

    public ContestedWarZone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Whenever a creature deals combat damage to you, that creature's controller gains control of Contested War Zone.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(Zone.BATTLEFIELD, StaticFilters.FILTER_PERMANENT_CREATURE,
                new TargetPlayerGainControlSourceEffect("that creature's controller"),
                false, true, SetTargetPointer.PLAYER));

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {T}: Attacking creatures get +1/+0 until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostAllEffect(1, 0, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ContestedWarZone(final ContestedWarZone card) {
        super(card);
    }

    @Override
    public ContestedWarZone copy() {
        return new ContestedWarZone(this);
    }

}
