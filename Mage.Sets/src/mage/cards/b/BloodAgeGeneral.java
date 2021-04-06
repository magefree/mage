package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodAgeGeneral extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterAttackingCreature("attacking Spirits");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public BloodAgeGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Attacking Spirits get +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn, filter, false
        ), new TapSourceCost()));
    }

    private BloodAgeGeneral(final BloodAgeGeneral card) {
        super(card);
    }

    @Override
    public BloodAgeGeneral copy() {
        return new BloodAgeGeneral(this);
    }
}
