package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author choiseul11
 */
public final class CelestialSword extends CardImpl {

    public CelestialSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // {3}, {tap}: Target creature you control gets +3/+3 until end of turn. Its controller sacrifices it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(3, 3, Duration.EndOfTurn), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("its controller sacrifices it")
                )
        ).setText("its controller sacrifices it at the beginning of the next end step"));
        this.addAbility(ability);
    }

    private CelestialSword(final CelestialSword card) {
        super(card);
    }

    @Override
    public CelestialSword copy() {
        return new CelestialSword(this);
    }
}
