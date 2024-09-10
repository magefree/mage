
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.OverloadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author LevelX2
 */
public final class MizziumSkin extends CardImpl {


    public MizziumSkin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Target creature you control gets +0/+1 and gains hexproof until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED));
        this.getSpellAbility().addEffect(new BoostTargetEffect(0,1, Duration.EndOfTurn).setText("target creature you control gets +0/+1"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(HexproofAbility.getInstance(), Duration.EndOfTurn).setText("and gains hexproof until end of turn"));

        // Overload {1}{U} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        OverloadAbility ability = new OverloadAbility(this, new BoostAllEffect(0,1, Duration.EndOfTurn,StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED,false), new ManaCostsImpl<>("{1}{U}"));
        ability.addEffect(new GainAbilityAllEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED));
        this.addAbility(ability);
    }

    private MizziumSkin(final MizziumSkin card) {
        super(card);
    }

    @Override
    public MizziumSkin copy() {
        return new MizziumSkin(this);
    }
}