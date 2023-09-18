package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkaabWrangler extends CardImpl {

    public SkaabWrangler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Tap three untapped creatures you control: Tap target creature.
        Ability ability = new SimpleActivatedAbility(
                new TapTargetEffect(),
                new TapTargetCost(new TargetControlledPermanent(
                        3, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
                ))
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SkaabWrangler(final SkaabWrangler card) {
        super(card);
    }

    @Override
    public SkaabWrangler copy() {
        return new SkaabWrangler(this);
    }
}
