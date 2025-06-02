package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.AttachTargetToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class BrassSquire extends CardImpl {

    public BrassSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.MYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Attach target Equipment you control to target creature you control.
        Ability ability = new SimpleActivatedAbility(new AttachTargetToTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private BrassSquire(final BrassSquire card) {
        super(card);
    }

    @Override
    public BrassSquire copy() {
        return new BrassSquire(this);
    }
}
