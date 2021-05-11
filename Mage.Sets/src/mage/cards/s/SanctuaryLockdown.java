package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SanctuaryLockdown extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent(SubType.HUMAN, "Humans");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.HUMAN, "untapped Humans you control");

    static {
        filter2.add(TappedPredicate.UNTAPPED);
    }

    public SanctuaryLockdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Humans you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)
        ));

        // {2}, Tap two untapped Humans you control: Tap target creature an opponent controls.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new GenericManaCost(2));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(2, filter2)));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private SanctuaryLockdown(final SanctuaryLockdown card) {
        super(card);
    }

    @Override
    public SanctuaryLockdown copy() {
        return new SanctuaryLockdown(this);
    }
}
