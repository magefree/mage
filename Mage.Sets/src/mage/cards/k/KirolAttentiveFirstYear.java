package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CopyTargetStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.target.TargetStackObject;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KirolAttentiveFirstYear extends CardImpl {

    public KirolAttentiveFirstYear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tap two untapped creatures you control: Copy target triggered ability you control. You may choose new targets for the copy. Activate only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(
                new CopyTargetStackObjectEffect(), new TapTargetCost(2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES)
        );
        ability.addTarget(new TargetStackObject(StaticFilters.FILTER_CONTROLLED_TRIGGERED_ABILITY));
        this.addAbility(ability);
    }

    private KirolAttentiveFirstYear(final KirolAttentiveFirstYear card) {
        super(card);
    }

    @Override
    public KirolAttentiveFirstYear copy() {
        return new KirolAttentiveFirstYear(this);
    }
}
