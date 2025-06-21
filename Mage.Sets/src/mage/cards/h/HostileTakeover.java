package mage.cards.h;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class HostileTakeover extends CardImpl {

    public HostileTakeover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}{R}");

        // Up to one target creature has base power and toughness 1/1 until end of turn. Up to one other target creature has base power and toughness 4/4 until end of turn. Then Hostile Takeover deals 3 damage to each creature.
        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(1, 1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1).setTargetTag(1).withChooseHint("1/1"));

        this.getSpellAbility().addEffect(new SetBasePowerToughnessTargetEffect(4, 4, Duration.EndOfTurn)
                .setTargetPointer(new SecondTargetPointer())
                .setText("up to one other target creature has base power and toughness 4/4 until end of turn"));
        this.getSpellAbility().addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2
        ).setTargetTag(2).withChooseHint("4/4"));
        this.getSpellAbility().addEffect(new DamageAllEffect(
                3, StaticFilters.FILTER_PERMANENT_CREATURE
        ).concatBy("Then"));
    }

    private HostileTakeover(final HostileTakeover card) {
        super(card);
    }

    @Override
    public HostileTakeover copy() {
        return new HostileTakeover(this);
    }
}
