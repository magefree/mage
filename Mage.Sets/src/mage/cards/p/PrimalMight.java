package mage.cards.p;

import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author mikalinn777
 */


public final class PrimalMight extends CardImpl {

    public PrimalMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}");

        // Target creature you control gets +X/+X until end of turn. Then it fights up to one target creature you don’t control.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(ManacostVariableValue.REGULAR, ManacostVariableValue.REGULAR, Duration.EndOfTurn));
        //
        this.getSpellAbility().addEffect(new FightTargetsEffect()
                .concatBy("Then")
                .setText("it fights up to one target creature you don't control"));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));

    }

    private PrimalMight(final PrimalMight card) {
        super(card);
    }

    @Override
    public PrimalMight copy() {
        return new PrimalMight(this);
    }

}
