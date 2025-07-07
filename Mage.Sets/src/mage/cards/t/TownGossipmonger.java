package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class TownGossipmonger extends CardImpl {

    public TownGossipmonger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.secondSideCardClazz = mage.cards.i.IncitedRabble.class;

        // {T}, Tap an untapped creature you control: Transform Town Gossipmonger.
        this.addAbility(new TransformAbility());
        Ability ability = new SimpleActivatedAbility(new TransformSourceEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE));
        this.addAbility(ability);
    }

    private TownGossipmonger(final TownGossipmonger card) {
        super(card);
    }

    @Override
    public TownGossipmonger copy() {
        return new TownGossipmonger(this);
    }
}
