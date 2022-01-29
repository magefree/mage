package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VivienArkbowRanger extends CardImpl {

    public VivienArkbowRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VIVIEN);
        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(4));

        // +1: Distribute two +1/+1 counters among up to two target creatures. They gain trample until end of turn.
        Ability ability = new LoyaltyAbility(new DistributeCountersEffect(
                CounterType.P1P1, 2, false, "up to two target creatures"), 1);
        ability.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                "They gain trample until end of turn"
        ));
        TargetCreaturePermanentAmount target = new TargetCreaturePermanentAmount(2);
        target.setMinNumberOfTargets(0);
        target.setMaxNumberOfTargets(2);
        ability.addTarget(target);

        // ability.setTargetAdjuster(VivienArkbowRangerAdjuster.instance);
        this.addAbility(ability);

        // −3: Target creature you control deals damage equal to its power to target creature or planeswalker.
        ability = new LoyaltyAbility(new DamageWithPowerFromOneToAnotherTargetEffect(), -3);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addTarget(new TargetCreatureOrPlaneswalker());
        this.addAbility(ability);

        // −5: You may reveal a creature card you own from outside the game and put it into your hand.
        this.addAbility(new LoyaltyAbility(new WishEffect(StaticFilters.FILTER_CARD_CREATURE), -5)
                .addHint(OpenSideboardHint.instance));
    }

    private VivienArkbowRanger(final VivienArkbowRanger card) {
        super(card);
    }

    @Override
    public VivienArkbowRanger copy() {
        return new VivienArkbowRanger(this);
    }

    enum VivienArkbowRangerAdjuster implements TargetAdjuster {
        instance;

        @Override
        public void adjustTargets(Ability ability, Game game) {
            // if targets are available, switch over to a working target method
            if (game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, game).size() > 0) {
                ability.getTargets().clear();
                ability.addTarget(new TargetCreaturePermanentAmount(2));
            }
        }
    }
}
