
package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPermanent;

import java.util.UUID;

import static mage.filter.StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED;


/**
 *
 * @author LevelX2
 */
public final class MizziumSkin extends CardImpl {


    public MizziumSkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target creature you control gets +0/+1 and gains hexproof until end of turn.
        // Overload {1}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility.implementOverloadAbility(this, new ManaCostsImpl<>("{1}{U}"),
                new TargetPermanent(FILTER_PERMANENT_CREATURE_CONTROLLED), new BoostTargetEffect(0,1, Duration.EndOfTurn).setText("target creature you control gets +0/+1"),
                new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn).setText("and gains hexproof until end of turn"));
    }

    private MizziumSkin(final MizziumSkin card) {
        super(card);
    }

    @Override
    public MizziumSkin copy() {
        return new MizziumSkin(this);
    }
}
