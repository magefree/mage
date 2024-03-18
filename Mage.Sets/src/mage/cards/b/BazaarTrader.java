package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.TargetPlayerGainControlTargetPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class BazaarTrader extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact, creature, or land you control");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()));
    }

    public BazaarTrader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target player gains control of target artifact, creature, or land you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new TargetPlayerGainControlTargetPermanentEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private BazaarTrader(final BazaarTrader card) {
        super(card);
    }

    @Override
    public BazaarTrader copy() {
        return new BazaarTrader(this);
    }
}
