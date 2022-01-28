package mage.cards.s;

import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SavageSmash extends CardImpl {

    public SavageSmash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{G}");

        // Target creature you control gets +2/+2 until end of turn. It fights target creature you don't control.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(
                new FightTargetsEffect().setText("It fights target creature you don't control." +
                        "<i>(Each deals damage equal to its power to the other.)</i>")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private SavageSmash(final SavageSmash card) {
        super(card);
    }

    @Override
    public SavageSmash copy() {
        return new SavageSmash(this);
    }
}
