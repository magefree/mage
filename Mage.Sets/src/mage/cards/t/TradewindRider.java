package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class TradewindRider extends CardImpl {

    public TradewindRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addCost(new TapTargetCost(2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES));
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);
    }

    private TradewindRider(final TradewindRider card) {
        super(card);
    }

    @Override
    public TradewindRider copy() {
        return new TradewindRider(this);
    }
}
